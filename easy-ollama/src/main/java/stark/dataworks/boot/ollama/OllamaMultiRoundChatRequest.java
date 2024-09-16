package stark.dataworks.boot.ollama;

import lombok.Data;

import java.util.List;

@Data
public class OllamaMultiRoundChatRequest
{
    private String model;
    private List<OllamaMultiRoundChatMessage> messages;
    private boolean stream;
}
