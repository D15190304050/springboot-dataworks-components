package stark.dataworks.boot.llm.chat;

import reactor.core.publisher.Flux;

import java.util.UUID;

public interface IChatSession
{
    String getSessionId();
    ChatResponse chat(String userInput);
    Flux<String> chatStream(String userInput);
    void close();
}
