package stark.dataworks.boot.llm.contexts;

public record SummarizeResult(boolean summarized, String summaryId, int tokensReduced) {}