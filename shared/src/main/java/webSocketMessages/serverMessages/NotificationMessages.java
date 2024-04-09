package webSocketMessages.serverMessages;

public class NotificationMessages extends ServerMessage{
    private final String message;
    public NotificationMessages(String message) {
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
