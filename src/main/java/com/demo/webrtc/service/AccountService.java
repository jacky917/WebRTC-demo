package com.demo.webrtc.service;

import org.apache.shiro.authc.Account;

public interface AccountService {
    public Account findByUsername(String username);
}
