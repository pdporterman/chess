package webScoketMessages;

import webScoketMessages.serverMessages.ServerMessage;

public class ErrorMessage extends ServerMessage {

    String errorMessage;
    public ErrorMessage(ServerMessageType type, String errorMessage) {
        super(type);
        this.errorMessage = errorMessage;
    }

    @Override
    public ServerMessageType getServerMessageType() {
        return super.getServerMessageType();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
