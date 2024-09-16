package stark.dataworks.boot.ollama.examples;

import stark.dataworks.basic.data.json.JsonSerializer;
import stark.dataworks.boot.ollama.OllamaConnection;
import stark.dataworks.boot.ollama.OllamaOneTimeChatRequest;
import stark.dataworks.boot.ollama.OllamaOneTimeChatResponse;

public class OneTimeChat
{
    public static void main(String[] args)
    {
        OllamaConnection connection = new OllamaConnection("http://localhost", 11434);

        OllamaOneTimeChatRequest request = new OllamaOneTimeChatRequest();
        request.setModel("qwen2");
        request.setPrompt("如何做土豆牛腩？");
        request.setStream(false);

        OllamaOneTimeChatResponse chatResponse = connection.runOneTimeChat(request);

        System.out.println(JsonSerializer.serialize(chatResponse));
    }
}
