package webSocket;

import webSocketMessages.serverMessages.NotificationMessages;
import ui.EscapeSequences;

public class NotificationHandler {
    public void notify(NotificationMessages notification) {
        System.out.println(notification.getMessage());
    }

}
