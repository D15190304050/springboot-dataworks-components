package stark.dataworks.boot.llm.chat;

import reactor.core.publisher.Flux;

import java.util.UUID;

public class RedisChatSession implements IChatSession
{
    private final IChatCompletionExecutor chatCompletionExecutor;
    private final String sessionId;
    private final RedisChatContextManager contextManager;

    public RedisChatSession(IChatCompletionExecutor chatCompletionExecutor, RedisChatContextManager contextManager)
    {
        this(chatCompletionExecutor, UUID.randomUUID().toString(), contextManager);
    }

    public RedisChatSession(IChatCompletionExecutor chatCompletionExecutor, String sessionId, RedisChatContextManager contextManager)
    {
        this.chatCompletionExecutor = chatCompletionExecutor;
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
        String answer = chatCompletionExecutor.complete(contextManager.buildContext(), userInput);
        return new ChatResponse(answer);
    }

    @Override
    public Flux<String> chatStream(String userInput)
    {
        return chatCompletionExecutor.stream(contextManager.buildContext(), userInput);
    }

    @Override
    public void close()
    {
        contextManager.clearCaches();
    }
}
