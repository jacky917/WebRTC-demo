
package com.demo.webrtc.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.demo.webrtc.config.WebSocketEndpointConfig;
import com.demo.webrtc.constant.MessageConstants;
import com.demo.webrtc.domain.entity.Connection;
import com.demo.webrtc.domain.entity.Message;
import com.demo.webrtc.domain.entity.WrUser;
import com.demo.webrtc.service.ConnectionService;
import com.demo.webrtc.service.MessageService;
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

    private static ConnectionService connectionService;

    @Autowired
    public void setRoomService(ConnectionService connectionService) {
        WebSocket.connectionService = connectionService;
    }

//    @Autowired
//    public void setAccountService(AccountService accountService) {
//        WebSocket.accountService = accountService;
//    }

    private static MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        WebSocket.messageService = messageService;
    }

    /**
     * WebSocket連接建立成功
     */
    @OnOpen
    public void onOpen(Session session) {
        Connection connection = connectionService.addUser(session);
        if (connection != null) {
            log.info("用戶 {} 建立WebSocket連接成功。當前在線人數 {}", connection.getUser().getAccount(), connectionService.onlineUsersCount());
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
            connectionService.removeUser(user.getAccount());
            log.info("用戶關閉WebSocket連接成功。當前在線人數 {}", connectionService.onlineUsersCount());
            return;
        }
        log.info("用戶關閉WebSocket連接失敗。");
    }

    /**
     * WebSocket發生錯誤
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("用戶WebSocket連接錯誤");
//        log.info("用戶 {} WebSocket連接錯誤", accountService.getCurrentUser().getAccount());
        log.error(String.valueOf(error));
    }

    /**
     * WebSocket獲取到客戶端消息
     *
     * @param stringMessage 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(Session session, String stringMessage) {
        Connection src = connectionService.getConnection(session);
        log.info("用戶 {} 發送消息 {} ", src.getUser().getAccount(), stringMessage);

        Message message = JSON.parseObject(stringMessage, Message.class);
        switch (message.getCommand()) {
            case MessageConstants.TYPE_COMMAND_DIALOGUE:
            case MessageConstants.TYPE_COMMAND_READY:
            case MessageConstants.TYPE_COMMAND_OFFER:
            case MessageConstants.TYPE_COMMAND_ANSWER:
            case MessageConstants.TYPE_COMMAND_CANDIDATE:
                messageService.sendMessageToUser(message, src, connectionService.getConnection(message.getUserName()));
//                messageService.sendMessageToUser(message, src, connectionService.getConnection(message.getUserName()));
                break;
            case MessageConstants.TYPE_COMMAND_USER_LIST:
//                messageService.sendMessageToEveryOneExcludeSelf(MessageConstants.TYPE_COMMAND_USER_LIST,JSON.toJSONString(connectionService.getOnlineUsersName(), SerializerFeature.WriteNullListAsEmpty),connection);
                System.out.println(connectionService.getOnlineUsersName());
                messageService.sendMessageToEveryOne(MessageConstants.TYPE_COMMAND_USER_LIST,JSON.toJSONString(connectionService.getOnlineUsersName(), SerializerFeature.WriteNullListAsEmpty));
                break;
            default:
        }
    }

//    /**
//     * 除了當前用戶以外推送在線用戶列表
//     * @param connection 當前用戶連接
//     */
//    private void pushUsersList(Connection connection) {
//        Message roomListMessage = new Message();
//        roomListMessage.setCommand(MessageConstants.TYPE_COMMAND_USER_LIST);
//        roomListMessage.setMessage(JSON.toJSONString(connectionService.getOnlineUsersName(),SerializerFeature.WriteNullListAsEmpty));
//        messageService.sendMessageToEveryOneExcludeSelf(roomListMessage, connection);
//    }
//
//    /**
//     * 推送在線用戶列表
//     */
//    private void pushUsersList() {
//        Message roomListMessage = new Message();
//        roomListMessage.setCommand(MessageConstants.TYPE_COMMAND_USER_LIST);
//        roomListMessage.setMessage(JSON.toJSONString(connectionService.getOnlineUsersName(),SerializerFeature.WriteNullListAsEmpty));
//        messageService.sendMessageToEveryOne(roomListMessage);
//    }

}
