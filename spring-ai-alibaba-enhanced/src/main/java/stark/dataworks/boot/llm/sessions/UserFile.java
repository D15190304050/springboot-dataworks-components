package stark.dataworks.boot.llm.sessions;

import java.io.InputStream;
import java.util.Map;

public record UserFile(
        String filename,
        String contentType,
        long sizeBytes,
        InputStream content,
        Map<String, String> meta
) {}
