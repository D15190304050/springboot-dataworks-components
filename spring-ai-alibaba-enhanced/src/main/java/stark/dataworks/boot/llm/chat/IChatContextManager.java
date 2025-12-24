package stark.dataworks.boot.llm.chat;

import java.util.List;

public interface IChatContextManager
{
    /**
     * 构造当前可发送给 LLM 的上下文
     */
    List<ChatMessage> buildContext();

    /**
     * 记录用户输入
     */
    void appendUserMessage(String content);

    /**
     * 记录 LLM 回复
     */
    void appendAssistantMessage(String content);
}
