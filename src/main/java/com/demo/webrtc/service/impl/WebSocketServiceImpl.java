package com.demo.webrtc.service.impl;

import com.demo.webrtc.domain.entity.Connection;
import com.demo.webrtc.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Override
    public String getWebSocketURL() {
        // 獲取本機所有網路接口
        Enumeration<NetworkInterface> naifs;
        try {
            naifs = NetworkInterface.getNetworkInterfaces();
            while (naifs.hasMoreElements()) {
                NetworkInterface nif = naifs.nextElement();
                // 獲取與該網路接口綁定的IP地址
                Enumeration<InetAddress> addresses = nif.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    // 獲取IPv4地址
                    if (addr instanceof Inet4Address) {
                        log.info("網卡接口名稱：{}",nif.getName());
                        log.info("網卡接口地址：{}",addr.getHostAddress());
                        // 獲取到第1個就返回
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            log.error("獲取IPv4地址失敗：{}",e.getMessage());
        }
        return null;
    }

    @Override
    public void onOpen(Connection connection) {

    }

    @Override
    public void onClose(Connection connection) {

    }

    @Override
    public void onError(Connection connection, Throwable error) {

    }

    @Override
    public void onMessage(Connection connection, String message) {

    }

    @Override
    public void pushMessage(String message) {

    }

    @Override
    public void pushMessage(Connection connection, String message) {

    }

    @Override
    public void pushMessage(Map<String, Connection> connMap, String message) {

    }

}
