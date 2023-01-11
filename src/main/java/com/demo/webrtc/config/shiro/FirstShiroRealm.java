package com.demo.webrtc.config.shiro;

import com.demo.webrtc.domain.entity.WrUser;
import com.demo.webrtc.domain.entity.WrUserRole;
import com.demo.webrtc.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

@Slf4j
public class FirstShiroRealm extends AuthorizingRealm {

    @Autowired
    AccountService accountService;

    public FirstShiroRealm() {
        //設置憑證匹配器，修改為hash憑證匹配器
        HashedCredentialsMatcher myCredentialsMatcher = new HashedCredentialsMatcher();
        //設置算法
        myCredentialsMatcher.setHashAlgorithmName("md5");
        //散列次數
        myCredentialsMatcher.setHashIterations(1024);
//        myCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        this.setCredentialsMatcher(myCredentialsMatcher);
    }

    /**
     * 授權
     * @param principalCollection 帳戶信息
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        WrUser user = (WrUser)principalCollection.getPrimaryPrincipal();
//        log.info("AuthorizationInfo: " + principalCollection.fromRealm(getName()).iterator().next());
        log.info("AuthorizationInfo: " + user);

        HashSet<String> roles = new HashSet<>();

        List<WrUserRole> sqlRoles = accountService.findRolesByUserID(user.getId());

        //TODO 目前直接查出來注入Bean裡面，之後考慮用Redis實現。
        for(WrUserRole role : sqlRoles){
            roles.add(accountService.getRoleByID(role.getId()).getName());
            log.info("Add role: " + accountService.getRoleByID(role.getId()).getName());
        }
        return new SimpleAuthorizationInfo(roles);
    }


    /**
     * 認證
     * @param authenticationToken 帳戶信息
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("FirstShiroRealm: " + authenticationToken);
        // 前台輸入的帳號密碼對象
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String username = token.getUsername();
        StringBuilder sb = new StringBuilder();
        for (Character ch : token.getPassword())
            sb.append(ch);
        String password = sb.toString();

        log.info("====================================");
        log.info("Username = " + username);
        log.info("Password = " + password);
        log.info("====================================");
        // 認證的實體信息。可以是username，也可以是數據表對應用戶的實體類對象

        // 以下數據是從數據庫獲取的
        WrUser user = accountService.findUserByUserName(username);
        // 用戶不存在
        if(user == null) return null;
        // 認證的實體信息，可以是Username，也可以是數據表對應實體類對象
        // Object principal = user;
        // 數據庫獲取的帳戶密碼
        // Object credentials = user.getPassword();
        // 當前Realm對象的name
        // String realmName = this.getName();
        // SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,realmName);
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getAccount());
        // SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, this.getName());
        return new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, this.getName());
    }

    public static void main(String[] args) {
        // 產生測試用加密密文
        String hashAlgorithmName = "md5";
        Object credentials = "123456";
//        Object salt = null;
        ByteSource credentialsSalt = ByteSource.Util.bytes("admin");
        int hashIterations = 1024;
        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations);
        log.info(String.valueOf(simpleHash));
    }

}
