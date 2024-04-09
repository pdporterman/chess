package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import webSocketMessages.serverMessages.NotificationMessages;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, ArrayList<Session>> connections = new ConcurrentHashMap<>();

    public void add(int gameId, Session session) {
        ArrayList<Session> list = connections.get(gameId);
        if (list == null){
            connections.put(gameId, new ArrayList<>());
            list = connections.get(gameId);
        }
        list.add(session);
    }

    public void remove(String authtoken) {
        connections.remove(authtoken);
    }

    public void broadcast(int gameId, ServerMessage notification) throws IOException {
        var removeList = new ArrayList<>();
        var cons = connections.get(gameId);
        for (var sesh : cons) {
            if (sesh.isOpen()) {
//                if (!c.authtoken.equals(authtoken)) {
//                    c.send(new Gson().toJson(notification));
//                }
            } else {
                removeList.add(sesh);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c);
        }
    }

}
