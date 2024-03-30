package webSocketMessages.serverMessages;

public class NotificationMessages extends ServerMessage{
    private final String message;
    public NotificationMessages(ServerMessageType type, String message) {
        super(type);
        this.message = message;
    }

    @Override
    public ServerMessageType getServerMessageType() {
        return super.getServerMessageType();
    }

    public String getMessage() {
        return message;
    }
}
