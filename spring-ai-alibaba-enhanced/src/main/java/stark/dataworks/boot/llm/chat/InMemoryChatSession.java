package stark.dataworks.boot.llm.chat;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

public class InMemoryChatSession extends ChatSessionBase
{
    private final IChatContextManager contextManager;
    private final IChatCompletionExecutor chatCompletionExecutor;

    public InMemoryChatSession(IChatContextManager contextManager, IChatCompletionExecutor chatCompletionExecutor)
    {
        super(UUID.randomUUID().toString(), contextManager, chatCompletionExecutor);
        this.contextManager = contextManager;
        this.chatCompletionExecutor = chatCompletionExecutor;
    }

    @Override
    public void close()
    {
        // 第一版：no-op
    }
}
