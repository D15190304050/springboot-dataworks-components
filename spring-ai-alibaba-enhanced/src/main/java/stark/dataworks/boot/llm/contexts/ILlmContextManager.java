package stark.dataworks.boot.llm.contexts;

import stark.dataworks.boot.llm.contexts.assests.AssetRef;
import stark.dataworks.boot.llm.contexts.assests.CodeAsset;
import stark.dataworks.boot.llm.contexts.assests.FileAsset;
import stark.dataworks.boot.llm.sessions.ClearOptions;
import stark.dataworks.boot.llm.sessions.SessionCreateRequest;

public interface ILlmContextManager
{
    // ========== 1) Session ==========
    String createSession(SessionCreateRequest req);

    void touchSession(String sessionId);

    void clearSession(String sessionId, ClearOptions options);

    // ========== 2) Append chat ==========
    void appendUserMessage(String sessionId, ChatMessage msg);

    void appendAssistantMessage(String sessionId, ChatMessage msg);

    // ========== 3) Attach assets ==========
    AssetRef attachCode(String sessionId, CodeAsset code);

    AssetRef attachFile(String sessionId, FileAsset file);

    // ========== 4) Build context for a call ==========
    LlmCallContext buildContext(String sessionId, BuildContextRequest req);

    // ========== 5) Post-processing ==========
    void recordCompletion(String sessionId, CompletionRecord record);

    SummarizeResult summarizeIfNeeded(String sessionId, SummarizeRequest req);

    DebugView getDebugView(String sessionId);
}

