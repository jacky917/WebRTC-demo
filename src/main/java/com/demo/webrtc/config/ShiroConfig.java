package com.demo.webrtc.config;

import com.demo.webrtc.config.roleFilter.AtLeastOneRoleFilter;
import com.demo.webrtc.config.shiro.realm.FirstShiroRealm;
import com.demo.webrtc.config.shiro.realm.SecondShiroRealm;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

//    @Bean
//    RememberMeManager rememberMeManager(){
//        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        simpleCookie.setHttpOnly(true);
//        // 有效期七天
//        simpleCookie.setMaxAge(604800);
//        rememberMeManager.setCookie(simpleCookie);
//        rememberMeManager.setCipherKey(Base64.getDecoder().decode("1111111"));
//        return rememberMeManager;
//    }

    @Bean
    public ModularRealmAuthenticator authenticator(){
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
//        ArrayList<Realm> realms = new ArrayList<>();
//        realms.add(this.firRealm());
//        realms.add(this.secRealm());
//        modularRealmAuthenticator.setRealms(realms);
//        modularRealmAuthenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
//        modularRealmAuthenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    @Bean
    public ShiroFilterFactoryBean filterFactoryBean(@Qualifier("manager") DefaultWebSecurityManager manager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        // 自定義角色認證
        Map<String, Filter> filter = new LinkedHashMap<>();
        filter.put("atLeastOneRole",new AtLeastOneRoleFilter());
        shiroFilter.setFilters(filter);

        Map<String,String> map = new LinkedHashMap<>();
        // 公開接口允許匿名訪問
        map.put("/pub/**","anon");

//        map.put("/manage","perms[manage]");
        map.put("/sysmgr/admin","roles[admin]");
        map.put("/sysmgr/manage","roles[manage]");
        map.put("/websocket","atLeastOneRole[admin,vip,account]");


        // 敏感接口需驗證
        map.put("/sysmgr/**","authc");

        map.put("/**","authc");

//        map.put("/**","anon");

        //設置登入頁面
        shiroFilter.setLoginUrl("/pub/login");

        //未授權頁面
        shiroFilter.setUnauthorizedUrl("/authc");
        shiroFilter.setFilterChainDefinitionMap(map);

        shiroFilter.setSecurityManager(manager);
        return shiroFilter;
    }


//    @Bean
//    public DefaultWebSecurityManager manager(@Qualifier("firRealm") FirstShiroRealm firstShiroRealm){
//        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//        manager.setRealm(firstShiroRealm);
//        return manager;
//    }

    @Bean
    public DefaultWebSecurityManager manager(@Qualifier("authenticator") ModularRealmAuthenticator modularRealmAuthenticator){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(this.firRealm());
//        manager.setAuthenticator(modularRealmAuthenticator);
//        ArrayList<Realm> realms = new ArrayList<>();
//        realms.add(this.firRealm());
//        realms.add(this.secRealm());
//        manager.setRealms(realms);
        return manager;
    }


    @Bean
    public FirstShiroRealm firRealm(){
        return new FirstShiroRealm();
    }

    @Bean
    public SecondShiroRealm secRealm(){
        return new SecondShiroRealm();
    }

//    @Bean
//    public Realm getRealm(){
//        //设置凭证匹配器，修改为hash凭证匹配器
//        HashedCredentialsMatcher myCredentialsMatcher = new HashedCredentialsMatcher();
//        //设置算法
//        myCredentialsMatcher.setHashAlgorithmName("md5");
//        //散列次数
//        myCredentialsMatcher.setHashIterations(512);
//        ShiroRealm realm = new ShiroRealm();
//        realm.setCredentialsMatcher(myCredentialsMatcher);
//        return realm;
//    }

}
