package stark.dataworks.boot.llm.contexts.assests;

import java.util.Map;

public record FileAsset(
        String filename,
        String contentType,     // "application/pdf" ...
        long sizeBytes,
        String objectKey,       // 指向对象存储/本地存储的引用
        Map<String, String> tags
) {}