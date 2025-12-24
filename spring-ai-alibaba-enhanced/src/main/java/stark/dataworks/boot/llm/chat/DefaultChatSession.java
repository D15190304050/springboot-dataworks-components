package stark.dataworks.boot.llm.chat;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

class DefaultChatSession implements IChatSession
{

    private final UUID sessionId;
    private final IChatContextManager contextManager;
    private final IChatCompletionExecutor chatCompletionExecutor;

    DefaultChatSession(
        UUID sessionId,
        IChatContextManager contextManager,
        IChatCompletionExecutor chatCompletionExecutor
    )
    {
        this.sessionId = sessionId;
        this.contextManager = contextManager;
        this.chatCompletionExecutor = chatCompletionExecutor;
    }

    @Override
    public UUID getSessionId()
    {
        return sessionId;
    }

    @Override
    public synchronized ChatResponse chat(String userInput)
    {
        contextManager.appendUserMessage(userInput);

        List<ChatMessage> context = contextManager.buildContext();

        String answer = chatCompletionExecutor.complete(context);

        contextManager.appendAssistantMessage(answer);

        return new ChatResponse(answer);
    }

    @Override
    public Flux<String> chatStream(String userInput) {
        contextManager.appendUserMessage(userInput);

        List<ChatMessage> context = contextManager.buildContext();

        return chatCompletionExecutor
            .stream(context)
            .doOnComplete(() -> {
                // 这里要把完整 assistant 内容写回 context
                // 可以用 StringBuilder 在上层聚合
            });
    }


    @Override
    public void close()
    {
        // 第一版：no-op
    }
}
