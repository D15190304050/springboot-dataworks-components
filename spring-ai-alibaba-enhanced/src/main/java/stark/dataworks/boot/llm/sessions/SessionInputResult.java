package stark.dataworks.boot.llm.sessions;

import stark.dataworks.boot.llm.contexts.assests.AssetRef;

import java.util.List;
import java.util.Map;

public record SessionInputResult(
        String sessionId,
        List<String> appendedMessageIds,
        List<AssetRef> attachedAssets,
        Map<String, Object> meta
) {
    public static SessionInputResult empty(String sessionId) {
        return new SessionInputResult(
                sessionId,
                List.of(),
                List.of(),
                Map.of()
        );
    }
}
