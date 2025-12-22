package stark.dataworks.boot.llm.contexts;

public record SummarizeRequest(int targetTokens, String reason) {}