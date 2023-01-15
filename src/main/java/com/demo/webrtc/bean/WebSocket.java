
package com.demo.webrtc.bean;

import com.demo.webrtc.config.WebSocketEndpointConfig;
import com.demo.webrtc.domain.entity.Connection;
import com.demo.webrtc.domain.entity.WrUser;
import com.demo.webrtc.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Slf4j
@Component
@ServerEndpoint(value = "/websocket", configurator = WebSocketEndpointConfig.class)
public class WebSocket {


//    private static AccountService accountService;

    private static RoomService roomService;

    @Autowired
    public void setRoomService(RoomService roomService) {
        WebSocket.roomService = roomService;
    }

//    @Autowired
//    public void setAccountService(AccountService accountService) {
//        WebSocket.accountService = accountService;
//    }

    /**
     * WebSocket連接建立成功
     */
    @OnOpen
    public void onOpen(Session session) {
        Object obj = session.getUserProperties().get("user");
        if (obj != null) {
            WrUser user = (WrUser) obj;
            Connection connection = new Connection();
            connection.setUser(user);
            connection.setIp((String) session.getUserProperties().get("clientIp"));
            connection.setSession(session);
            roomService.addOnlineUser(connection);
            log.info("用戶 {} 建立WebSocket連接成功。當前在線人數 {}", user.getAccount(), roomService.onlineUsersCount());
        } else {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "No Token"));
            } catch (IOException e) {
                log.error(String.valueOf(e));
            }
        }
    }

    /**
     * WebSocket關閉連接
     */
    @OnClose
    public void onClose(Session session) {
//        roomService.removeOneUser(connection);

        Object obj = session.getUserProperties().get("user");
        if (obj != null) {
            WrUser user = (WrUser) obj;
            roomService.removeOneUser(user.getId());
            log.info("用戶關閉WebSocket連接成功。當前在線人數 {}", roomService.onlineUsersCount());
            return;
        }
        log.info("用戶關閉WebSocket連接失敗。");
    }

        /**
         * WebSocket發生錯誤
         */
        @OnError
        public void onError (Session session, Throwable error){
            log.info("用戶WebSocket連接錯誤");
//        log.info("用戶 {} WebSocket連接錯誤", accountService.getCurrentUser().getAccount());
            log.info(String.valueOf(error));
        }

        /**
         * WebSocket獲取到客戶端消息
         *
         * @param stringMessage 客户端发送过来的消息
         */
        @OnMessage
        public void onMessage (Session session, String stringMessage){
            log.info("用戶發送消息 {} ", stringMessage);
//        log.info("用戶 {} 發送消息", accountService.getCurrentUser().getAccount());
        }

    }
