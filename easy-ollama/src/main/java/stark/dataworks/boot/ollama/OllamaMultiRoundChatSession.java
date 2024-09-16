package stark.dataworks.boot.ollama;

import lombok.Data;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class OllamaMultiRoundChatSession
{
    private final String sessionId;
    private final List<OllamaMultiRoundChatMessage> messages;
    private final String httpUrlForMultiRoundChat;
    private final RestTemplate restTemplate;
    private final String model;

    public OllamaMultiRoundChatSession(String httpUrlForMultiRoundChat, String model)
    {
        this.model = model;
        this.sessionId = "MultiRoundSession-" + UUID.randomUUID();
        this.messages = new ArrayList<>();
        this.httpUrlForMultiRoundChat = httpUrlForMultiRoundChat;
        this.restTemplate = new RestTemplate();
    }

    public OllamaMultiRoundChatResponse runChat(OllamaMultiRoundChatMessage message)
    {
        messages.add(message);

        OllamaMultiRoundChatRequest request = new OllamaMultiRoundChatRequest();
        request.setMessages(messages);
        request.setModel(model);
        request.setStream(false);

        OllamaMultiRoundChatResponse chatResponse = restTemplate.postForObject(httpUrlForMultiRoundChat, request, OllamaMultiRoundChatResponse.class);
        messages.add(chatResponse.getMessage());
        return chatResponse;
    }
}
