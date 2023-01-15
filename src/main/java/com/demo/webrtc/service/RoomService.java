package com.demo.webrtc.service;

import com.demo.webrtc.domain.entity.Connection;

import java.util.Map;

public interface RoomService {

    /**
     * 返回當前在線的用戶數
     * @return 用戶數
     */
    Integer onlineUsersCount();

    /**
     * 返回當前在線的用戶
     * @return List<Connection> 所有Connection
     */
    Map<Long, Connection> getCurrentOnlineUsers();

    /**
     * 用戶上線
     * @param connection 用戶連接對象
     */
    void addOnlineUser(Connection connection);

    /**
     * 獲取一個連接
     * @param id 連接id
     * @return 連接對象
     */
    Connection getOneUser(Long id);

    /**
     * 刪除一個連接
     * @param id 連接id
     */
    void removeOneUser(Long id);

}
