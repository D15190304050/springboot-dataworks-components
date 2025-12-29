package stark.dataworks.boot.llm.chat;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.dataworks.boot.llm.Role;

import java.util.ArrayList;
import java.util.List;

public class RedisChatContextManager implements IChatContextManager
{
    public static final String CHAT_HISTORY_KEY_PREFIX = "chatHistory:";

    private final String systemPrompt;
    private final int recentRounds;
    private final String sessionId;
    private final RedisTemplate<String, String> stringRedisTemplate;

    private String userMessageToAppend;

    public RedisChatContextManager(String systemPrompt, int recentRounds, String sessionId, RedisTemplate<String, String> stringRedisTemplate)
    {
        this.systemPrompt = systemPrompt;
        this.recentRounds = recentRounds;
        this.sessionId = sessionId;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public synchronized List<ChatMessage> buildContext()
    {
        List<ChatMessage> messages = getChatHistory();
        return RecentRoundMessageExtractor.extract(messages, systemPrompt, recentRounds);
    }

    @Override
    public void appendUserMessage(String content)
    {
        userMessageToAppend = content;
    }

    @Override
    public void appendAssistantMessage(String content)
    {
        if (userMessageToAppend == null)
            throw new IllegalStateException("User message is not appended.");

        List<ChatMessage> messages = getChatHistory();
        messages.add(new ChatMessage(Role.USER, userMessageToAppend));
        messages.add(new ChatMessage(Role.ASSISTANT, content));
    }

    private List<ChatMessage> getChatHistory()
    {
        String messagesJson = stringRedisTemplate.opsForValue().get(CHAT_HISTORY_KEY_PREFIX + sessionId);

        List<ChatMessage> messages;
        if (StringUtils.hasText(messagesJson))
            messages = JsonSerializer.deserializeList(messagesJson, ChatMessage.class);
        else
            messages = new ArrayList<>();

        return messages;
    }
}
