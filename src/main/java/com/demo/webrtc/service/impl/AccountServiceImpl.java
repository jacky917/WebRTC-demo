package com.demo.webrtc.service.impl;

import com.demo.webrtc.service.AccountService;
import org.apache.shiro.authc.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public Account findByUsername(String username) {
        return null;
    }
}
