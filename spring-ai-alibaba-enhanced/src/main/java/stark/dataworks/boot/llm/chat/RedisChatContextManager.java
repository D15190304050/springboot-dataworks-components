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
    public static final String SYSTEM_PROMPT_KEY = "systemPrompt:";

    private final int recentRounds;
    private final String sessionId;
    private final RedisTemplate<String, String> stringRedisTemplate;

    private String userMessageToAppend;

    public RedisChatContextManager(String systemPrompt, int recentRounds, String sessionId, RedisTemplate<String, String> stringRedisTemplate)
    {
        this.recentRounds = recentRounds;
        this.sessionId = sessionId;
        this.stringRedisTemplate = stringRedisTemplate;

        stringRedisTemplate.opsForValue().set(SYSTEM_PROMPT_KEY + sessionId, systemPrompt);
    }

    @Override
    public synchronized List<ChatMessage> buildContext()
    {
        String systemPrompt = stringRedisTemplate.opsForValue().get(SYSTEM_PROMPT_KEY + sessionId);
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

        // Send null system prompt, because we only need to store the user messages into redis.
        List<ChatMessage> refreshedMessage = RecentRoundMessageExtractor.extract(messages, null, recentRounds);
        stringRedisTemplate.opsForValue().set(CHAT_HISTORY_KEY_PREFIX + sessionId, JsonSerializer.serialize(refreshedMessage));
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
