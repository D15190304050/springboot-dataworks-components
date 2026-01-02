package stark.dataworks.boot.llm.chat;

public class ChatSessionFactory
{
    private final IChatCompletionExecutor defaultChatCompletionExecutor;

    public ChatSessionFactory(IChatCompletionExecutor chatCompletionExecutor)
    {
        defaultChatCompletionExecutor = chatCompletionExecutor;
    }

    public IChatSession createDefaultSession(IChatCompletionExecutor chatCompletionExecutor, String systemPrompt, int recentRounds)
    {
        IChatContextManager contextManager = new InMemoryChatContextManager(systemPrompt, recentRounds);
        return new InMemoryChatSession(contextManager, chatCompletionExecutor);
    }

    public IChatSession createDefaultSession(String systemPrompt, int recentRounds)
    {
        return createDefaultSession(defaultChatCompletionExecutor, systemPrompt, recentRounds);
    }

    public IChatSession createRedisSession(IChatCompletionExecutor chatCompletionExecutor, String systemPrompt, int recentRounds)
    {
        return null;
    }

    public IChatSession createRedisSession(String systemPrompt, int recentRounds)
    {
        return createRedisSession(defaultChatCompletionExecutor, systemPrompt, recentRounds);
    }

    public IChatSession loadSessionFromRedis(IChatCompletionExecutor chatCompletionExecutor, String sessionId, int recentRounds)
    {


        return null;
    }

    public IChatSession loadSessionFromRedis(String sessionId, int recentRounds)
    {
        return loadSessionFromRedis(defaultChatCompletionExecutor, sessionId, recentRounds);
    }
}
