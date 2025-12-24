package stark.dataworks.boot.llm.chat;

import java.util.UUID;

public class ChatSessionFactory
{
    private final IChatCompletionExecutor chatExecutor;

    public ChatSessionFactory(IChatCompletionExecutor chatExecutor)
    {
        this.chatExecutor = chatExecutor;
    }

    public IChatSession openSession(
        String systemPrompt,
        int recentRounds
    )
    {
        UUID sessionId = UUID.randomUUID();

        IChatContextManager contextManager =
            new InMemoryChatContextManager(systemPrompt, recentRounds);

        return new DefaultChatSession(
            sessionId,
            contextManager,
            chatExecutor
        );
    }
}
