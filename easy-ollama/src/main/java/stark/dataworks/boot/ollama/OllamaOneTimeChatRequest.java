package stark.dataworks.boot.ollama;

import lombok.Data;

@Data
public class OllamaOneTimeChatRequest
{
    private String model;
    private String prompt;
    private boolean stream;
}
