package stark.dataworks.examples.llm.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public DefaultSessionController()
    {
        chatSessions = new HashMap<>();
    }

    // Actually, we need a method to initialize a chat session before sending a chat request.

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request)
    {
        IChatSession chatSession = chatSessions.getOrDefault(request.getSessionId(), chatSessionFactory.openSession(SYSTEM_PROMPT, 1));
        chatSessions.put(request.getSessionId(), chatSession);

        log.info("Chat session ID: {}", request.getSessionId());

        return chatSession.chat(request.getUserInput()).getContent();
    }
}
