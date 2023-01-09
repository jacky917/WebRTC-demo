package com.demo.webrtc.config.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;

public class SecondShiroRealm extends AuthorizingRealm {

    public SecondShiroRealm() {
        //设置凭证匹配器，修改为hash凭证匹配器
        HashedCredentialsMatcher myCredentialsMatcher = new HashedCredentialsMatcher();
        //设置算法
        myCredentialsMatcher.setHashAlgorithmName("sha1");
        //散列次数
        myCredentialsMatcher.setHashIterations(1024);
//        myCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        this.setCredentialsMatcher(myCredentialsMatcher);
    }

    /**
     * 授權
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("AuthorizationInfo: " + principalCollection);

        HashSet<String> roles = new HashSet<>();
        roles.add(principalCollection.toString());

        return new SimpleAuthorizationInfo(roles);
    }


    /**
     * 認證
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("SecondShiroRealm: " + authenticationToken);
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

        String username = usernamePasswordToken.getUsername();
        StringBuilder sb = new StringBuilder();
        for (Character ch : usernamePasswordToken.getPassword())
            sb.append(ch);
        String password = sb.toString();

        System.out.println("Username = " + username);
        if (!"admin".equals(usernamePasswordToken.getUsername()))
            return null;
        System.out.println("Password = " + password);
        // 認證的實體信息。可以是username，也可以是數據表對應用戶的實體類對象
        Object principal = username;
        Object credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
        String realmName = this.getName();
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,realmName);
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("sec", credentials, credentialsSalt, realmName);
        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "sha1";
        Object credentials = "123456";
//        Object salt = null;
        ByteSource credentialsSalt = ByteSource.Util.bytes("admin");
        int hashIterations = 1024;
        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, credentials, credentialsSalt, hashIterations);
        System.out.println(simpleHash);
    }

}
