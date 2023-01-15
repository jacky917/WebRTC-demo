package com.demo.webrtc.service.impl;

import com.demo.webrtc.domain.entity.Connection;
import com.demo.webrtc.service.RoomService;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomServiceImpl implements RoomService {

    // 用戶池
    private final Map<Long, Connection> connections = new ConcurrentHashMap<>();

    @Override
    public Integer onlineUsersCount() {
        return connections.size();
    }

    @Override
    public Map<Long, Connection> getCurrentOnlineUsers() {
        return connections;
    }

    @Override
    public void addOnlineUser(Connection connection) {
        connections.put(connection.getUser().getId(),connection);
    }

    @Override
    public Connection getOneUser(Long id) {
        return connections.get(id);
    }

    @Override
    public void removeOneUser(Long id) {
        connections.remove(id);
    }

}
