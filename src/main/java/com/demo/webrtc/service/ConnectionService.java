package com.demo.webrtc.service;

import com.demo.webrtc.domain.entity.Connection;

import javax.websocket.Session;
import java.util.Map;
import java.util.Set;

public interface ConnectionService {

    /**
     * 通過會話獲取連接
     * @return 一個連接對象
     */
    Connection getConnection(Session session);

    /**
     * 通過用戶名獲取連接
     * @return 一個連接對象
     */
    Connection getConnection(String userName);

    /**
     * 返回當前在線的用戶數
     * @return 用戶數
     */
    Integer onlineUsersCount();

    /**
     * 返回當前在線的用戶
     * @return List<Connection> 所有Connection
     */
    Map<String, Connection> getAllConnections();

    /**
     * 返回當前在線的用戶名
     * @return Set<String> 所有用戶名
     */
    Set<String> getOnlineUsersName();

    /**
     * 用戶上線
     * @param session 用戶會話對象
     */
    Connection addUser(Session session);

    /**
     * 獲取一個連接
     * @param name 用戶名
     * @return 連接對象
     */
    Connection getOneUser(String name);

    /**
     * 刪除一個連接
     * @param name 用戶名
     */
    void removeUser(String name);

}
