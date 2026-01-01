package stark.dataworks.boot.llm.chat;

import reactor.core.publisher.Flux;

import java.util.UUID;

public class RedisChatSession implements IChatSession
{
    private final String sessionId;

    public RedisChatSession()
    {
        this.sessionId = UUID.randomUUID().toString();
    }

    public RedisChatSession(String sessionId)
    {
        this.sessionId = sessionId;
    }

    @Override
    public String getSessionId()
    {
        return sessionId;
    }

    @Override
    public ChatResponse chat(String userInput)
    {
        return null;
    }

    @Override
    public Flux<String> chatStream(String userInput)
    {
        return null;
    }

    @Override
    public void close()
    {

    }
}
