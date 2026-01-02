package stark.dataworks.boot.llm.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stark.dataworks.boot.llm.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage
{
    private Role role;
    private String content;
}
