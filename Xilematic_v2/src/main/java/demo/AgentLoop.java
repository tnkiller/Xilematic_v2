package demo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import service.MovieService;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import model.Movie;

import com.fasterxml.jackson.databind.SerializationFeature;

public class AgentLoop {

    private static final int MAX_ITERATIONS = 10;
    private final List<Message> memory = new ArrayList<>();
    private final List<Message> agentRules = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()) // hỗ trợ LocalDate, LocalDateTime,...
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // định dạng ISO 8601
    private final MovieService movieService = new MovieService();
    private final FuntionToCall funtionToCall = new FuntionToCall(movieService.getAllMovies());
    public AgentLoop() {
        // Initialize agent rules
        agentRules.add(new Message("system",
                """
                Bạn là một tác nhân AI có thể thực hiện các nhiệm vụ bằng cách sử dụng các công cụ sẵn có.

Các công cụ có sẵn:

- getDetailMovieByName(filmName: String) -> List<Movie>: Tra cứu chi tiết phim theo tên.
- showFilmHot() -> List<Movie>: Hiển thị danh sách các phim đang hot.
- getBookingLink(filmName: String) -> String: Cung cấp link đặt vé cho một phim cụ thể.
- findMoviesByDirector(directorName: String) -> List<Movie>: Tìm các phim theo tên đạo diễn.
- findMoviesByGenre(genre: String) -> List<Movie>: Tìm các phim theo thể loại (hành động, kinh dị, hài, v.v.).
- findByShowtime(showtime: String) -> List<Movie>: Tìm phim theo ngày giờ chiếu.
- terminate(message: String): Kết thúc vòng lặp tác nhân và in ra tóm tắt cho người dùng.
- error(message: String): Trả về nếu yêu cầu không khớp với bất kỳ công cụ nào ở trên.

Mỗi phản hồi BẮT BUỘC phải có một hành động.  
Phản hồi theo định dạng sau:

```action
{
    "toolName": "insert toolName",
    "args": {
        ... điền các tham số cần thiết vào đây ...
    }
}
 """));
    }

    public String run(String userRequest, LLM llm) throws IOException, SQLException {
    int iterations = 0;
    memory.add(new Message("user", userRequest));
    boolean breakLoop = false;
    String finalResult = "";

    while (iterations < MAX_ITERATIONS && !breakLoop) {
        // Step 1: Construct prompt
        List<Message> prompt = new ArrayList<>(agentRules);
        prompt.addAll(memory);

        // Step 2: Generate response from LLM
        System.out.println("Agent thinking...");
        String response = llm.generateResponse(prompt);
        System.out.println("Agent response: " + response);

        // Step 3: Parse response to determine action
        Action action = parseAction(response);
        ActionResult result = null;

        // Step 4: Execute the appropriate action
        switch (action.getToolName()) {
            case "showFilmHot":
                result = new ActionResult(funtionToCall.showFilmHot(), null);
                break;

            case "getDetailMovieByName":
                String filmName1 = (String) action.getArgs().get("filmName");
                result = new ActionResult(funtionToCall.getDetailMovieByName(filmName1), null);
                break;

            case "getBookingLink":
                String filmName2 = (String) action.getArgs().get("filmName");
                result = new ActionResult(funtionToCall.getBookingLink(filmName2), null);
                break;

            case "findMoviesByDirector":
                String directorName = (String) action.getArgs().get("directorName");
                result = new ActionResult(funtionToCall.findMoviesByDirector(directorName), null);
                break;

            case "findMoviesByGenre":
                String genre = (String) action.getArgs().get("genre");
                result = new ActionResult(funtionToCall.findMoviesByGenre(genre), null);
                break;

           case "findByShowtime":
    LocalDateTime desiredTime = extractTimeFromUserText(userRequest);
    if (desiredTime != null) {
        try {
            List<Movie> recommendations = funtionToCall.recommendMoviesByTime(desiredTime);
            System.out.println("Recommendations count: " + recommendations.size());
            result = new ActionResult(recommendations, null);
        } catch (Exception e) {
            System.err.println("Error finding movies by showtime: " + e.getMessage());
            result = new ActionResult(null, "Lỗi khi tìm phim: " + e.getMessage());
        }
    } else {
        result = new ActionResult(null, "Không thể xác định thời gian từ câu nói.");
    }
    break;


            case "terminate":
                breakLoop = true;
                finalResult = (String) action.getArgs().getOrDefault("message", "terminate.");
                System.out.println(finalResult);
                continue;

            case "error":
                breakLoop = true;
                finalResult= (String) action.getArgs().get("message");
                System.out.println(finalResult);
                result = new ActionResult(null, finalResult);
                break;

            default:
                result = new ActionResult(null, "Unknown action: " + action.getToolName());
                break;
        }

//        // Bước in kết quả và xử lý finalResult
//        if (result != null) {
//            System.out.println("Action result: " + result.toMap());
//
//            if (result.getResult() != null) {
//                finalResult = result.getResult().toString();
//            } else if (result.getError() != null) {
//                finalResult = "Error: " + result.getError();
//            } else {
//                finalResult = "No result";
//            }
//        }

        // Step 5: Update memory
        memory.add(new Message("assistant", response));
        safelyAddResultToMemory(result);

        iterations++;
    }

    return finalResult;
}

    private void safelyAddResultToMemory(ActionResult result) throws IOException {
        if (result != null) {
            memory.add(new Message("user", objectMapper.writeValueAsString(result.toMap())));
        } else {
        	
            Map<String, Object> dummy = new HashMap<>();
            dummy.put("result", null);
            memory.add(new Message("user", objectMapper.writeValueAsString(dummy)));
        }
    }

    private Action parseAction(String response) {
        try {
            String actionBlock = extractMarkdownBlock(response, "action");
            Action action = objectMapper.readValue(actionBlock, Action.class);
            if (action.getToolName() != null && action.getArgs() != null) {
                return action;
            } else {
                return errorAction("You must respond with a JSON tool invocation.");
            }
        } catch (Exception e) {
            return errorAction("Invalid JSON response. You must respond with a JSON tool invocation.");
        }
    }

    private String extractMarkdownBlock(String text, String label) {
        String startTag = "```" + label;
        String endTag = "```";
        int start = text.indexOf(startTag);
        int end = text.indexOf(endTag, startTag.length());
        if (start != -1 && end != -1) {
            return text.substring(start + startTag.length(), end).trim();
        }
        throw new IllegalArgumentException("No markdown block found for label: " + label);
    }

    private Action errorAction(String message) {
        Map<String, Object> args = new HashMap<>();
        args.put("message", message);
        return new Action("error", args);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            LLM llm = new LLM();
            AgentLoop agent = new AgentLoop();
            System.out.println("Tôi là trợ lý ảo cho web Cinema bạn cần giúp đỡ gì ? ");
            while (true) {
                String userFunctionRequest = scanner.nextLine();
                if (userFunctionRequest.equalsIgnoreCase("exit")) {
                    break;
                }

                agent.run(userFunctionRequest, llm);
            }

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    public LocalDateTime extractTimeFromUserText(String input) {
        input = input.toLowerCase();
        LocalDate date = LocalDate.now();
        int hour = 19; // mặc định buổi tối

        // Tìm ngày như "30/4" hoặc "30-4"
        Pattern pattern = Pattern.compile("(\\d{1,2})[/-](\\d{1,2})");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            int day = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int year = LocalDate.now().getYear(); // mặc định là năm hiện tại
            try {
                date = LocalDate.of(year, month, day);
            } catch (Exception e) {
                // ngày không hợp lệ
            }
        } else if (input.contains("ngày mai")) {
            date = date.plusDays(1);
        }

        // Tìm giờ như "7h", "19 giờ"
        for (int i = 1; i <= 24; i++) {
            if (input.contains(i + "h") || input.contains(i + " giờ")) {
                hour = i;
                break;
            }
        }

        // Một số từ gợi ý mặc định giờ
        if (input.contains("sáng")) hour = 9;
        if (input.contains("chiều")) hour = 15;
        if (input.contains("tối")) hour = 19;

        return LocalDateTime.of(date, LocalTime.of(hour, 0));
    }
    public List<Message> getMemory() {
    return new ArrayList<>(memory); 
    
}



}