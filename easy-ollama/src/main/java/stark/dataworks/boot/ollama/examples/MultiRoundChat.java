package stark.dataworks.boot.ollama.examples;

import stark.dataworks.basic.data.json.JsonSerializer;
import stark.dataworks.boot.ollama.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MultiRoundChat
{
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String getCurrentTime()
    {
        return DATE_FORMAT.format(new Date());
    }

    public static void main(String[] args)
    {
        OllamaConnection connection = new OllamaConnection("http://localhost", 11434);

        OllamaMultiRoundChatSession session = connection.createMultiRoundChatSession("qwen2");

        OllamaMultiRoundChatMessage message1 = new OllamaMultiRoundChatMessage();
        message1.setRole(OllamaChatRoles.USER);
        message1.setContent("请教一下如何做土豆牛腩");
        System.out.println(getCurrentTime() + ": Message = " + JsonSerializer.serialize(message1));

        OllamaMultiRoundChatResponse chatResponse1 = session.runChat(message1);
        System.out.println(getCurrentTime() + ": Response = " + JsonSerializer.serialize(chatResponse1));

        OllamaMultiRoundChatMessage message2 = new OllamaMultiRoundChatMessage();
        message2.setRole(OllamaChatRoles.USER);
        message2.setContent("好厉害");
        System.out.println(getCurrentTime() + ": Message = " + JsonSerializer.serialize(message2));

        OllamaMultiRoundChatResponse chatResponse2 = session.runChat(message2);
        System.out.println(getCurrentTime() + ": Response = " + JsonSerializer.serialize(chatResponse2));
    }
}
