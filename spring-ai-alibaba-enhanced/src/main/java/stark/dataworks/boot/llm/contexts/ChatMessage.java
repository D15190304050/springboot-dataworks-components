package stark.dataworks.boot.llm.contexts;

import java.util.Map;

public record ChatMessage(String role, String content, Long tsMillis, Map<String, Object> meta) {}
