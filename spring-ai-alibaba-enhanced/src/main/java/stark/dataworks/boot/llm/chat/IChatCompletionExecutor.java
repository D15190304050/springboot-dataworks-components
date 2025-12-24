package stark.dataworks.boot.llm.chat;

import reactor.core.publisher.Flux;

import java.util.List;

public interface IChatCompletionExecutor
{
    /**
     * 同步完成一次 Chat Completion
     */
    String complete(List<ChatMessage> messages);

    /**
     * 流式完成 Chat Completion
     */
    Flux<String> stream(List<ChatMessage> messages);
}
