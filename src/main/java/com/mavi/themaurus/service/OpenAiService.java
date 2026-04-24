package com.mavi.themaurus.service;

import com.openai.client.OpenAIClient;
import com.openai.models.Reasoning;
import com.openai.models.ReasoningEffort;
import com.openai.models.responses.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class OpenAiService {

    @Value("${WEB_SEARCH}")
    private boolean USE_WEB_SEARCH;

    private final String BASE_URL = "https://api.openai.com/v1";

    private final OpenAIClient client;

    public OpenAiService(OpenAIClient client) {
        this.client = client;
    }

    private final String SYSTEM_PROMPT = """
            Provide a list of 3 movies that share a theme/aestehic with the given input movie title.
            
            If made available, you must use web-search to verify the IMDB ID for each suggested movie.
            Response must be in the following JSON format, and should contain NOTHING but the raw json:
            [
                {
                    "title": "movie 1 title",
                    "imdb_id": "tt637362"
                },
                {
                    "title": "movie 2 title",
                    "imdb_id": "tt826844"
                }
            ]
            """;

    public String getResponse(String query) {

        System.out.println("use web search: " + USE_WEB_SEARCH);

        ResponseCreateParams.Builder paramBuilder = ResponseCreateParams.builder()
                .instructions(SYSTEM_PROMPT)
                .input(query)
                .model("gpt-5.4")
                .reasoning(Reasoning.builder().effort(ReasoningEffort.LOW).build());

        if (USE_WEB_SEARCH) {
            WebSearchTool searchTool = WebSearchTool.builder().type(WebSearchTool.Type.WEB_SEARCH).build();
            paramBuilder.addTool(searchTool)
                    .toolChoice(ToolChoiceOptions.REQUIRED)
                    .maxToolCalls(3);
        }

        ResponseCreateParams params = paramBuilder.build();

        Response response = client.responses().create(params);

        String result = response.output().stream()
                .flatMap(item -> item.message().stream())
                .flatMap(message -> message.content().stream())
                .flatMap(content -> content.outputText().stream())
                .map(ResponseOutputText::text)
                .collect(Collectors.joining());

        if (result.isBlank()) {
            throw new IllegalStateException("Model returned no text output");
        }

        return result;
    }
}
