package stark.dataworks.boot.llm.chat;

import reactor.core.publisher.Flux;

import java.util.UUID;

public class RedisChatSession implements IChatSession
{
    private final String sessionId;
    private final RedisChatContextManager contextManager;

    public RedisChatSession(RedisChatContextManager contextManager)
    {
        this(UUID.randomUUID().toString(), contextManager);
    }

    public RedisChatSession(String sessionId, RedisChatContextManager contextManager)
    {
        this.sessionId = sessionId;
        this.contextManager = contextManager;
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
        contextManager.clearCaches();
    }
}
