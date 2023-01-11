package com.demo.webrtc.service.impl;

import com.demo.webrtc.domain.entity.Connection;
import com.demo.webrtc.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomServiceImpl implements RoomService {

    private Map<String, Set<Connection>> rooms = new ConcurrentHashMap<>();

}
