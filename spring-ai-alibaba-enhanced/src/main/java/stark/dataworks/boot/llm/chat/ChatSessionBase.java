package stark.dataworks.boot.llm.chat;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public abstract class ChatSessionBase implements IChatSession
{
    private final String sessionId;
    private final IChatContextManager contextManager;
    private final IChatCompletionExecutor chatCompletionExecutor;

    public ChatSessionBase(String sessionId, IChatContextManager contextManager, IChatCompletionExecutor chatCompletionExecutor)
    {
        this.sessionId = sessionId;
        this.contextManager = contextManager;
        this.chatCompletionExecutor = chatCompletionExecutor;
    }

    @Override
    public String getSessionId()
    {
        return sessionId;
    }

    private Mono<Void> appendAssistantMessage(String content)
    {
        return Mono.<Void>fromRunnable(() -> contextManager.appendAssistantMessage(content))
            .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<String> chatStream(String userInput)
    {
        contextManager.appendUserMessage(userInput);
        Flux<String> answerFlux = chatCompletionExecutor.stream(contextManager.buildContext(), userInput);

        ConnectableFlux<String> shared = answerFlux.publish();

        // 后台保存完整回答（非阻塞、带错误日志）
        shared
            .collectList()
            .map(chunks -> String.join("", chunks))
            .flatMap(this::appendAssistantMessage)
            .doOnError(e -> log.warn("Failed to persist assistant response:", e))
            .onErrorComplete() // 防止未处理异常
            .subscribe(); // 启动后台任务

        return shared.autoConnect();
    }

    @Override
    public String chat(String userInput)
    {
        String answer = chatCompletionExecutor.complete(contextManager.buildContext(), userInput);
        contextManager.appendUserMessage(userInput);
        contextManager.appendAssistantMessage(answer);
        return answer;
    }
}
