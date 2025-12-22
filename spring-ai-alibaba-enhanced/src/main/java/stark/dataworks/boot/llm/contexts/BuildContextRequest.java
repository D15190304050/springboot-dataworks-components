package stark.dataworks.boot.llm.contexts;

import java.util.Map;

public record BuildContextRequest(
    String userQuery,
    int maxInputTokens,
    int reservedOutputTokens,
    Map<String, Object> hints // 比如：强制使用某些 assetId、或禁用文件等
)
{
}