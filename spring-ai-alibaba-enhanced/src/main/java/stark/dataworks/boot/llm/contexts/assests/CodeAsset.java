package stark.dataworks.boot.llm.contexts.assests;

import java.util.Map;

public record CodeAsset(
    String language,       // "java" "python" ...
    String path,           // "src/main/java/.."
    String content,        // raw code
    Map<String, String> tags
)
{
}