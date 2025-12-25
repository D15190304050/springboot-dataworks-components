package stark.dataworks.boot.llm.chat;

import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

class DefaultChatSession implements IChatSession
{
    private final UUID sessionId;
    private final IChatContextManager contextManager;
    private final IChatCompletionExecutor chatCompletionExecutor;

    DefaultChatSession(UUID sessionId, IChatContextManager contextManager, IChatCompletionExecutor chatCompletionExecutor)
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
        List<ChatMessage> context = contextManager.buildContext();
        String answer = chatCompletionExecutor.complete(context, userInput);

        // Note: We need to append user message after calling complete(),
        // otherwise the chat client will get 2 identity user input message, which is not what we want.
        contextManager.appendUserMessage(userInput);
        contextManager.appendAssistantMessage(answer);

        return new ChatResponse(answer);
    }

    @Override
    public Flux<String> chatStream(String userInput)
    {
        List<ChatMessage> context = contextManager.buildContext();

        return chatCompletionExecutor
            .stream(context, userInput)
            .doOnComplete(() ->
            {
                // 这里要把完整 assistant 内容写回 context
                // 可以用 StringBuilder 在上层聚合
                contextManager.appendUserMessage(userInput);
            });
    }


    @Override
    public void close()
    {
        // 第一版：no-op
    }
}
