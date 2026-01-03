package stark.dataworks.boot.llm.chat;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class ChatSessionFactory
{
    private final IChatCompletionExecutor defaultChatCompletionExecutor;
    private final RedisTemplate<String, String> stringRedisTemplate;
    private final Function<String, List<ChatMessage>> fnLoadChatHistory;

    public ChatSessionFactory(IChatCompletionExecutor chatCompletionExecutor, RedisTemplate<String, String> stringRedisTemplate, Function<String, List<ChatMessage>> fnLoadChatHistory)
    {
        defaultChatCompletionExecutor = chatCompletionExecutor;
        this.stringRedisTemplate = stringRedisTemplate;
        this.fnLoadChatHistory = fnLoadChatHistory;
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

    public IChatSession createRedisSession(IChatCompletionExecutor chatCompletionExecutor, RedisTemplate<String, String> stringRedisTemplate, String systemPrompt, int recentRounds)
    {
        String sessionId = UUID.randomUUID().toString();

        return null;
    }

    public IChatSession createRedisSession(RedisTemplate<String, String> stringRedisTemplate, String systemPrompt, int recentRounds)
    {
        return createRedisSession(defaultChatCompletionExecutor, stringRedisTemplate, systemPrompt, recentRounds);
    }

    public IChatSession loadSessionFromRedis(IChatCompletionExecutor chatCompletionExecutor, String sessionId, int recentRounds, RedisTemplate<String, String> stringRedisTemplate)
    {
        if (!StringUtils.hasText(sessionId))
            throw new IllegalArgumentException("Session ID is required.");

        RedisChatContextManager contextManager = new RedisChatContextManager(recentRounds, sessionId, stringRedisTemplate, null);
        return new RedisChatSession(chatCompletionExecutor, sessionId, contextManager);
    }

    public IChatSession loadSessionFromRedis(String sessionId, int recentRounds, RedisTemplate<String, String> stringRedisTemplate)
    {
        return loadSessionFromRedis(defaultChatCompletionExecutor, sessionId, recentRounds, stringRedisTemplate);
    }
}
