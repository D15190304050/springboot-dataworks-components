package stark.dataworks.boot.llm.sessions;

public interface ILlmSession
{
    String sessionId();

    // ========== 用户输入 ==========
    SessionInputResult userText(String text);

    SessionInputResult userImage(UserImage image);

    SessionInputResult userFile(UserFile file);

    // ========== 一步完成对话 ==========
    LlmResponse ask(String userText);

    LlmResponse ask(SessionInputBundle input);

    // ========== 会话状态 ==========
    SessionSnapshot snapshot();

    void clear();
}
