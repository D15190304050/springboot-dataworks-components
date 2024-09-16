package stark.dataworks.boot.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OllamaMultiRoundChatResponse
{
    private String model;

    @JsonProperty("created_at")
    private Date createdAt;

    private OllamaMultiRoundChatMessage message;

    @JsonProperty("done_reason")
    private String doneReason;

    private boolean done;

    @JsonProperty("total_duration")
    private long totalDurationNs;

    @JsonProperty("load_duration")
    private long loadDurationNs;

    @JsonProperty("prompt_eval_count")
    private long promptEvalCount;

    @JsonProperty("prompt_eval_duration")
    private long promptEvalDurationNs;

    @JsonProperty("eval_count")
    private long evalCount;

    @JsonProperty("eval_duration")
    private long evalDurationNs;
}
