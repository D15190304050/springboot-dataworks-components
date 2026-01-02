package stark.dataworks.examples.llm.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import stark.dataworks.boot.llm.chat.AlibabaChatCompletionExecutor;
import stark.dataworks.boot.llm.chat.ChatSessionFactory;

@Configuration
public class RedisSessionConfiguration
{
    @Bean
    public ChatSessionFactory chatSessionFactory()
    {
        DashScopeApi dashScopeApi = DashScopeApi.builder()
            .apiKey("sk-17f1a044d3f44832bbde89e4afa875e8")
            .build();

        ChatModel chatModel = DashScopeChatModel.builder()
            .dashScopeApi(dashScopeApi)
            .defaultOptions(DashScopeChatOptions.builder()
                // Note: model must be set when use options build.
//                .withModel(DashScopeChatModel.DEFAULT_MODEL_NAME)
                .model("deepseek-v3.2")
                .temperature(0.5)
                .maxToken(1000)
                .build())
            .build();

        ChatClient chatClient = ChatClient
            .builder(chatModel)
            .build();

        AlibabaChatCompletionExecutor completionExecutor = new AlibabaChatCompletionExecutor(chatClient);
        ChatSessionFactory chatSessionFactory = new ChatSessionFactory(completionExecutor);

        return chatSessionFactory;
    }
}
