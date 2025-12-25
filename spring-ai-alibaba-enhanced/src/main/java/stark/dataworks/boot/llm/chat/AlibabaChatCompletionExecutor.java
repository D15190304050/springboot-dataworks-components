package stark.dataworks.boot.llm.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class AlibabaChatCompletionExecutor implements IChatCompletionExecutor
{
    private final ChatClient chatClient;

    public AlibabaChatCompletionExecutor(ChatClient chatClient)
    {
        this.chatClient = chatClient;
    }

    @Override
    public String complete(List<ChatMessage> messages)
    {
        Prompt prompt = buildPrompt(messages);

        return chatClient
            .prompt(prompt)
            .call()
            .content();
    }

    @Override
    public Flux<String> stream(List<ChatMessage> messages)
    {
        Prompt prompt = buildPrompt(messages);

        return chatClient
            .prompt(prompt)
            .stream()
            .content();
    }

    private Prompt buildPrompt(List<ChatMessage> messages)
    {
        List<Message> springMessages = new ArrayList<>();

        for (ChatMessage message : messages)
        {
            switch (message.getRole())
            {
                case SYSTEM -> springMessages.add(new SystemMessage(message.getContent()));
                case USER -> springMessages.add(new UserMessage(message.getContent()));
                case ASSISTANT -> springMessages.add(new AssistantMessage(message.getContent()));
                default -> throw new IllegalArgumentException(
                    "Unsupported role: " + message.getRole());
            }
        }

        return new Prompt(springMessages);
    }
}
