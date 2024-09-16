package stark.dataworks.boot.ollama;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Data
public class OllamaConnection
{
    private final String httpUrlPrefix;
    private final String httpUrlForOneTimeChat;
    private final String httpUrlForMultiRoundChat;
    private final RestTemplate restTemplate;

    public OllamaConnection(String ollamaHost, int ollamaPort)
    {
        if (!StringUtils.hasText(ollamaHost))
            throw new IllegalArgumentException("Host name cannot be null or empty");

        if (ollamaPort < 1 || ollamaPort > 65535)
            throw new IllegalArgumentException("Port number must be between 1 and 65535");

        if (!ollamaHost.startsWith("http"))
            throw new IllegalArgumentException("Host name must start with 'http'");

        this.httpUrlPrefix = ollamaHost + ":" + ollamaPort;
        this.httpUrlForOneTimeChat = this.httpUrlPrefix + OllamaHttpApiUrls.ONE_TIME_CHAT;
        this.httpUrlForMultiRoundChat = this.httpUrlPrefix + OllamaHttpApiUrls.MULTI_ROUND_CHAT;
        this.restTemplate = new RestTemplate();
    }

    public OllamaOneTimeChatResponse runOneTimeChat(OllamaOneTimeChatRequest request)
    {
        // TODO: Upgrade to JDK 21 & SpringBoot 3.x.

        ResponseEntity<OllamaOneTimeChatResponse> responseEntity = restTemplate.postForEntity(httpUrlForOneTimeChat, request, OllamaOneTimeChatResponse.class);
        return responseEntity.getBody();
    }

    public OllamaMultiRoundChatSession createMultiRoundChatSession(String model)
    {
        return new OllamaMultiRoundChatSession(this.httpUrlForMultiRoundChat, model);
    }
}
