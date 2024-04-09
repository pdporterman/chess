package webSocket;

import webSocketMessages.serverMessages.NotificationMessages;
import ui.EscapeSequences;

public interface NotificationHandler {
    public void notify(NotificationMessages notification);

}
