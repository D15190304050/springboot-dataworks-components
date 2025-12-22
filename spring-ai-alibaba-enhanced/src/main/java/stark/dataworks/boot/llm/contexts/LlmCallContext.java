package stark.dataworks.boot.llm.contexts;

import java.util.List;

public record LlmCallContext(
    List<PromptPart> parts,     // system/context/conversation/tooling
    List<String> usedAssetIds,  // 可观测性
    int estimatedTokens
)
{
}