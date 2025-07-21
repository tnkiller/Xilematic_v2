package demo;



import java.util.List;
import java.util.stream.Collectors;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionAssistantMessageParam;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionSystemMessageParam;
import com.openai.models.chat.completions.ChatCompletionUserMessageParam;

import model.Movie;
import service.MovieService;

public class LLM {

    /**
     * Generates an LLM response based on the provided messages.
     *
     * Why do it this way instead of using OpenAI directly?
     * ------------------------------------------------------
     * This method implements a critical abstraction layer that decouples the application's
     * business logic from any specific LLM provider. This architectural decision offers
     * several significant advantages:
     *
     * 1. Provider Independence: By using our own Message abstraction, we can easily switch
     *    between different LLM providers (OpenAI, Anthropic, Google, etc.) without changing
     *    any code in the rest of the application. Only this method needs modification.
     *
     * 2. API Evolution Protection: LLM provider APIs frequently change. This abstraction
     *    insulates the rest of the codebase from these changes. If OpenAI deprecates an
     *    API or changes its data structures, we only need to update this single method.
     *
     * 3. Testing and Mocking: This approach makes testing significantly easier. We can
     *    mock this method with predictable responses for unit tests without needing to
     *    stub complex provider-specific APIs.
     *
     * 4. Cost Control: We can easily implement fallback strategies, rate limiting, or
     *    routing logic to different models/providers based on cost or performance needs
     *    without affecting the consumer code.
     *
     * 5. Observability: This centralized method provides a single point for adding logging,
     *    metrics, error handling, and monitoring for all LLM interactions.
     *
     * 6. Future-Proofing: As new LLM providers emerge or as we develop internal models,
     *    we can integrate them seamlessly by just adapting this method.
     *
     * Example future extension:
     * ```java
     * if (useAnthropic) {
     *     return AnthropicAdapter.generateResponse(messages);
     * } else if (useInternalModel) {
     *     return InternalModelAdapter.generateResponse(messages);
     * } else {
     *     // Current OpenAI implementation
     * }
     * ```
     *
     * @param messages List of Message objects containing role and content.
     * @return The generated response as a String.
     */
    public String generateResponse(List<Message> messages) {
        // Initialize OpenAI client using environment variables
    	OpenAIClient client = OpenAIOkHttpClient.builder()
        .apiKey("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") // API key của bạn
        .build();

        // Transform custom Message objects to OpenAI's ChatCompletionMessageParam objects
        ChatCompletionCreateParams.Builder paramsBuilder = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4_1)
                .maxTokens(1024);

        // Add messages individually to the builder
        for (Message message : messages) {
            if (message.getRole().equals("system")) {
                ChatCompletionSystemMessageParam systemMsg = ChatCompletionSystemMessageParam.builder()
                        .content(message.getContent())
                        .build();
                paramsBuilder.addMessage(systemMsg);
            } else if (message.getRole().equals("user")) {
                ChatCompletionUserMessageParam userMsg = ChatCompletionUserMessageParam.builder()
                        .content(message.getContent())
                        .build();
                paramsBuilder.addMessage(userMsg);
            } else {
                // For assistant or other roles, use ChatCompletionAssistantMessageParam
                ChatCompletionAssistantMessageParam assistantMsg = ChatCompletionAssistantMessageParam.builder()
                        .content(message.getContent())
                        .build();
                paramsBuilder.addMessage(assistantMsg);
            }
        }

        // Get completion response
        ChatCompletion completion = client.chat().completions().create(paramsBuilder.build());

        // Return content from first choice
        return completion.choices().get(0).message().content().get();
    }
   
    }

