package stark.dataworks.boot.llm.sessions;

import java.util.Map;

public record SessionCreateRequest(
        String userId,
        Map<String, Object> meta
) {
    public SessionCreateRequest(String userId) {
        this(userId, Map.of());
    }
}
