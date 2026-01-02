package stark.dataworks.boot.llm.chat;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class RedisChatSession extends ChatSessionBase
{
    private final IChatCompletionExecutor chatCompletionExecutor;

    private final RedisChatContextManager contextManager;

    public RedisChatSession(IChatCompletionExecutor chatCompletionExecutor, RedisChatContextManager contextManager)
    {
        this(chatCompletionExecutor, UUID.randomUUID().toString(), contextManager);
    }

    public RedisChatSession(IChatCompletionExecutor chatCompletionExecutor, String sessionId, RedisChatContextManager contextManager)
    {
        super(sessionId, contextManager, chatCompletionExecutor);
        this.chatCompletionExecutor = chatCompletionExecutor;
        this.contextManager = contextManager;
    }

    @Override
    public void close()
    {
        contextManager.clearCaches();
    }
}
