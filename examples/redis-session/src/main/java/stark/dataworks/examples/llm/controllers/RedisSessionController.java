package stark.dataworks.examples.llm.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stark.dataworks.boot.llm.chat.ChatSessionFactory;
import stark.dataworks.boot.llm.chat.IChatSession;
import stark.dataworks.examples.llm.dto.ChatRequest;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/session")
public class RedisSessionController
{
    public static final String SYSTEM_PROMPT = "You are a helpful assistant.";

    private final HashMap<String, IChatSession> chatSessions;

    @Autowired
    private ChatSessionFactory chatSessionFactory;

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    public RedisSessionController()
    {
        chatSessions = new HashMap<>();
    }

    // Actually, we need a method to initialize a chat session before sending a chat request.

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request)
    {
        String sessionId = request.getSessionId();
        if (!StringUtils.hasText(sessionId))
            throw new IllegalArgumentException("Session ID is required.");

        stringRedisTemplate.opsForValue().get(sessionId);

        chatSessionFactory.loadSessionFromRedis(sessionId, 1);

        IChatSession chatSession = chatSessions.get(sessionId);
        log.info("Chat session ID: {}", sessionId);

        return chatSession.chat(request.getUserInput());
    }
}
