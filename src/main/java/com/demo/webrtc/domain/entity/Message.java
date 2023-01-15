package com.demo.webrtc.domain.entity;

import lombok.Data;

@Data
public class Message {

    /**
     * 創建房間
     */
    public static final String TYPE_COMMAND_ROOM_ENTER = "enterRoom";
    /**
     * 獲取房間
     */
    public static final String TYPE_COMMAND_ROOM_LIST = "roomList";
    /**
     * 對話
     */
    public static final String TYPE_COMMAND_DIALOGUE = "dialogue";
    /**
     * 準備
     */
    public static final String TYPE_COMMAND_READY = "ready";
    /**
     * 離開
     */
    public static final String TYPE_COMMAND_OFFER = "offer";
    /**
     * 回答
     */
    public static final String TYPE_COMMAND_ANSWER = "answer";
    /**
     * 申請人
     */
    public static final String TYPE_COMMAND_CANDIDATE = "candidate";

    private String command;
    private String userId;
    private String roomId;
    private String message;
}
