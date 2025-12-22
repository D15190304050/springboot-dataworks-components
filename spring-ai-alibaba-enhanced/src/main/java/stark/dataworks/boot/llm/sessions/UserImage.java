package stark.dataworks.boot.llm.sessions;

import java.util.Map;

public record UserImage(
    String imageId,
    String contentType,           // image/png
    byte[] bytes,                 // 或 InputStream
    Map<String, String> meta
)
{
}
