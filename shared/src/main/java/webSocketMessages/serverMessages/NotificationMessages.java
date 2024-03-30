package webSocketMessages.serverMessages;

public class NotificationMessages extends ServerMessage{
    private final String message;
    public NotificationMessages(ServerMessageType type, String message) {
        super(type);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
