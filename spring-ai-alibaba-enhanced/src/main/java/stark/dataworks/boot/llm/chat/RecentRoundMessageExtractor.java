package stark.dataworks.boot.llm.chat;

import stark.dataworks.boot.llm.Role;

import java.util.ArrayList;
import java.util.List;

public class RecentRoundMessageExtractor
{
    private RecentRoundMessageExtractor()
    {}

    public static List<ChatMessage> extract(List<ChatMessage> messages, String systemPrompt, int recentRounds)
    {
        List<ChatMessage> context = new ArrayList<>();

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
}
