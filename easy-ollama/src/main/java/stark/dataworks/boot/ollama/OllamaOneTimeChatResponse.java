package stark.dataworks.boot.ollama;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OllamaOneTimeChatResponse
{
    private String model;

    @JsonProperty("created_at")
    private Date createdAt;

    private String response;

    private boolean done;

    @JsonProperty("done_reason")
    private String doneReason;

    private List<Long> context;

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
