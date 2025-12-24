package stark.dataworks.boot.llm.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import stark.dataworks.boot.llm.Role;

@Data
@AllArgsConstructor
public class ChatMessage
{
    private Role role;
    private String content;
}
