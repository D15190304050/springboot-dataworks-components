package stark.dataworks.boot.llm.chat;

import stark.dataworks.boot.llm.Role;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryChatContextManager implements IChatContextManager
{

    private final String systemPrompt;
    private final int recentRounds;

    /**
     * USER / ASSISTANT 交替
     */
    private final LinkedList<ChatMessage> messages = new LinkedList<>();

    public InMemoryChatContextManager(String systemPrompt, int recentRounds)
    {
        this.systemPrompt = systemPrompt;
        this.recentRounds = recentRounds;
    }

    @Override
    public synchronized List<ChatMessage> buildContext()
    {
        return RecentRoundMessageExtractor.extract(messages, systemPrompt, recentRounds);
    }

    @Override
    public synchronized void appendUserMessage(String content)
    {
        messages.add(new ChatMessage(Role.USER, content));
    }

    @Override
    public synchronized void appendAssistantMessage(String content)
    {
        messages.add(new ChatMessage(Role.ASSISTANT, content));
    }
}
