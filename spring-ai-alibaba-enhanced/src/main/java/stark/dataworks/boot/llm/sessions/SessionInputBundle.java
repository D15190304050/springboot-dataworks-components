package stark.dataworks.boot.llm.sessions;

import java.util.List;
import java.util.Map;

public record SessionInputBundle(
    String text,                 // 用户原始文本（可包含代码块）
    List<UserImage> images,
    List<UserFile> files,
    Map<String, Object> meta
)
{
}
