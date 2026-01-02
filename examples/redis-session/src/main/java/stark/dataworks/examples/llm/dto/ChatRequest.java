package stark.dataworks.examples.llm.dto;

import lombok.Data;

@Data
public class ChatRequest
{
    private String sessionId;
    private String userInput;
}
