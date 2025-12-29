package stark.dataworks.examples.llm.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import stark.dataworks.boot.llm.chat.ChatSessionFactory;
import stark.dataworks.boot.llm.chat.DefaultChatSession;
import stark.dataworks.boot.llm.chat.IChatSession;
import stark.dataworks.examples.llm.dto.ChatRequest;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/session")
public class DefaultSessionController
{
    public static final String SYSTEM_PROMPT = "You are a helpful assistant.";

    private final HashMap<String, IChatSession> chatSessions;

    @Autowired
    private ChatSessionFactory chatSessionFactory;

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    public DefaultSessionController()
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

        if (!chatSessions.containsKey(sessionId))
            chatSessions.put(sessionId, chatSessionFactory.openSession(SYSTEM_PROMPT, 1));

        IChatSession chatSession = chatSessions.get(sessionId);
        log.info("Chat session ID: {}", sessionId);

        return chatSession.chat(request.getUserInput()).getContent();
    }
}
