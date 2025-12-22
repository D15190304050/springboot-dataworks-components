package stark.dataworks.boot.llm.sessions;

public record ClearOptions(
        boolean clearMessages,
        boolean clearAssets
) {
    public static ClearOptions all() {
        return new ClearOptions(true, true);
    }

    public static ClearOptions messagesOnly() {
        return new ClearOptions(true, false);
    }
}
