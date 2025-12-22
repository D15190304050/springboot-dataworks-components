package stark.dataworks.boot.llm.contexts;

import java.util.Map;

public record CompletionRecord(String model, String assistantText, Map<String, Object> usage) {}
