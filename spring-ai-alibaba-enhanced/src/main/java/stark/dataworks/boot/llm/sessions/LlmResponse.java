package stark.dataworks.boot.llm.sessions;

import java.util.Map;

public record LlmResponse(
        String text,
        Map<String, Object> usage,
        Map<String, Object> meta
) {
    public static LlmResponse of(String text) {
        return new LlmResponse(text, Map.of(), Map.of());
    }
}
