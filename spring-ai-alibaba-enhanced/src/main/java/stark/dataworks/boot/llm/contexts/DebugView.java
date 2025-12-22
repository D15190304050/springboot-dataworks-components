package stark.dataworks.boot.llm.contexts;

import java.util.List;
import java.util.Map;

public record DebugView(
        List<String> hotKeys,
        List<String> summaryKeys,
        List<String> assets,
        Map<String, Object> lastSelectorDecision
) {}