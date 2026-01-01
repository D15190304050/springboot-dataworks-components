package stark.dataworks.boot.llm.chat;

import java.util.UUID;

public class ChatSessionFactory
{
    private final IChatCompletionExecutor chatExecutor;

    public ChatSessionFactory(IChatCompletionExecutor chatExecutor)
    {
        this.chatExecutor = chatExecutor;
    }

    public IChatSession openSession(String systemPrompt, int recentRounds)
    {
        IChatContextManager contextManager =
            new InMemoryChatContextManager(systemPrompt, recentRounds);

        return new DefaultChatSession(
            contextManager,
            chatExecutor
        );
    }

    public IChatSession createRedisSession()
    {
        return null;
    }

    public IChatSession loadSessionFromRedis(String sessionId)
    {
        return null;
    }
}
