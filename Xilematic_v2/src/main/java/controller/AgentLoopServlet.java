package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import demo.AgentLoop;
import demo.LLM; // Giả định lớp LLM của bạn nằm trong package demo
import demo.Message; // Giả định lớp Message của bạn nằm trong package demo
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/chat")
public class AgentLoopServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()); // Đảm bảo ObjectMapper được cấu hình

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        AgentLoop agent = (AgentLoop) session.getAttribute("agentLoop");
        LLM llm = (LLM) session.getAttribute("llm");

        if (agent == null) {
            agent = new AgentLoop();
            session.setAttribute("agentLoop", agent);
        }
        if (llm == null) {
            llm = new LLM();
            session.setAttribute("llm", llm);
        }

        // Lấy tin nhắn người dùng từ request
        // CẦN ĐỌC JSON BODY, KHÔNG DÙNG request.getParameter
        // String userMessage = request.getParameter("message"); // Lỗi ở đây
        JsonNode jsonNode = objectMapper.readTree(request.getReader());
        String userMessage = jsonNode.has("message") ? jsonNode.get("message").asText() : null;


        if (userMessage == null || userMessage.trim().isEmpty()) {
            response.getWriter().write(objectMapper.writeValueAsString(Map.of("response", "Tin nhắn không được rỗng.")));
            return;
        }

        String botResponse;
        try {
            botResponse = agent.run(userMessage, llm);
            botResponse = processUrlInMessage(botResponse);
            System.out.println("chat bot response : "+botResponse);
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Thêm status lỗi
            response.getWriter().write(objectMapper.writeValueAsString(Map.of("error", "Đã có lỗi xảy ra với cơ sở dữ liệu. Vui lòng thử lại sau.")));
            return;
        } catch (Exception e) {
            System.err.println("Error processing chat: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Thêm status lỗi
            response.getWriter().write(objectMapper.writeValueAsString(Map.of("error", "Xin lỗi, đã có lỗi xảy ra trong quá trình xử lý yêu cầu của bạn.")));
            return;
        }

        response.getWriter().write(objectMapper.writeValueAsString(Map.of("response", botResponse)));
    }

    // Phương thức để lấy lịch sử chat khi người dùng mở lại hộp thoại
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        AgentLoop agent = (AgentLoop) session.getAttribute("agentLoop");

        List<Message> chatHistory = new ArrayList<>();
        if (agent != null) {
            try {
                // Đảm bảo AgentLoop có phương thức getMemory() trả về List<Message>
                chatHistory = agent.getMemory();
            } catch (Exception e) {
                System.err.println("Error retrieving chat history from AgentLoop: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write(objectMapper.writeValueAsString(Map.of("error", "Lỗi khi tải lịch sử chat từ server.")));
                return;
            }
        } else {
            // Nếu agent chưa được khởi tạo (phiên mới), có thể trả về một danh sách rỗng
            // hoặc khởi tạo agent nếu bạn muốn duy trì lịch sử ngay cả khi trang tải lại.
            // Tuy nhiên, việc khởi tạo lại agent ở GET có thể reset memory, tùy thuộc vào logic của bạn.
            // Để đơn giản, nếu agent null, ta trả về lịch sử rỗng.
            System.out.println("AgentLoop not found in session for GET request, returning empty history.");
        }

        response.getWriter().write(objectMapper.writeValueAsString(chatHistory));
    }
    private String processUrlInMessage(String message) {
    // Regex để tìm URL
    String urlRegex = "(https?://[^\\s]+)";
    Pattern pattern = Pattern.compile(urlRegex);
    Matcher matcher = pattern.matcher(message);
    
    // Nếu tìm thấy URL
    if (matcher.find()) {
        String url = matcher.group(1);
        System.out.println("Detected URL: " + url);
        
        // Tạo HTML link cho URL
        String linkedMessage = message.replace(url, 
            "<a href=\"" + url + "\" target=\"_blank\" style=color:red>" + "Đặt vé " + "</a>");
        
        System.out.println("Processed message with URL: " + linkedMessage);
        return linkedMessage;
    }
    
    // Nếu không có URL, trả về tin nhắn gốc
    return message;
}
}