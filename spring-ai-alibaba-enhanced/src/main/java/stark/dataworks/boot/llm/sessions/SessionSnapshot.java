package stark.dataworks.boot.llm.sessions;

import java.util.List;
import java.util.Map;

public record SessionSnapshot(
        String sessionId,
        int messageCount,
        int codeAssetCount,
        int fileAssetCount,
        List<String> recentMessages,
        Map<String, Object> meta
) {}
