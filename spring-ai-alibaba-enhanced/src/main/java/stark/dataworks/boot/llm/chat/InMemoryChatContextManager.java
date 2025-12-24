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
        List<ChatMessage> context = new ArrayList<>();

        // 1. system
        if (systemPrompt != null && !systemPrompt.isBlank())
            context.add(new ChatMessage(Role.SYSTEM, systemPrompt));

        int size = messages.size();

        // 2. 首轮（最多 2 条）
        if (size > 0)
            context.add(messages.get(0));
        if (size > 1)
            context.add(messages.get(1));

        // 3. 最近 N 轮
        int max = recentRounds * 2;
        int start = Math.max(2, size - max);

        for (int i = start; i < size; i++)
            context.add(messages.get(i));

        return context;
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
