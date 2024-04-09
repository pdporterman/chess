package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

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

    public void remove(int gameId, Session session) {
        var cons = connections.get(gameId);
        cons.remove(session);
    }

    public void broadcast(int gameId, Session session, ServerMessage notification) throws IOException {
        var removeList = new ArrayList<>();
        var cons = connections.get(gameId);
        for (var sesh : cons) {
            if (sesh.isOpen()) {
                if (!sesh.equals(session)) {
                    session.getRemote().sendString(new Gson().toJson(notification));
                }
            } else {
                cons.remove(sesh);
            }
        }
    }

}
