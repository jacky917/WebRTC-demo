package com.demo.webrtc.service.impl;

import com.demo.webrtc.domain.entity.Connection;
import com.demo.webrtc.domain.entity.WrUser;
import com.demo.webrtc.service.ConnectionService;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    // 用戶池
    private final Map<String, Connection> connections = new ConcurrentHashMap<>();


    @Override
    public Connection getConnection(Session session) {
        Object obj = session.getUserProperties().get("user");
        if (obj != null) {
            WrUser user = (WrUser) obj;
            return connections.get(user.getAccount());
        }
        return null;
    }

    @Override
    public Connection getConnection(String userName) {
        return connections.get(userName);
    }

    @Override
    public Integer onlineUsersCount() {
        return connections.size();
    }

    @Override
    public Map<String, Connection> getAllConnections() {
        return connections;
    }

    @Override
    public Set<String> getOnlineUsersName() {
        return connections.keySet();
    }

    @Override
    public Connection addUser(Session session) {

        Object obj = session.getUserProperties().get("user");
        if (obj != null) {
            Connection connection = new Connection();
            WrUser user = (WrUser) obj;
            connection.setUser(user);
            connection.setIp((String) session.getUserProperties().get("clientIp"));
            connection.setSession(session);
            connections.put(connection.getUser().getAccount(),connection);
            // 添加成功
            return connection;
        }
        // 添加失敗
        return null;
    }

    @Override
    public Connection getOneUser(String name) {
        return connections.get(name);
    }

    @Override
    public void removeUser(String name) {
        connections.remove(name);
    }

}
