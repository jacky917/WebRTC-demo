package com.demo.webrtc.service;

import com.demo.webrtc.domain.entity.Connection;
import com.demo.webrtc.domain.entity.WrRole;
import com.demo.webrtc.domain.entity.WrUser;
import com.demo.webrtc.domain.entity.WrUserRole;
import org.apache.shiro.subject.Subject;

import java.util.List;

public interface AccountService {

    /**
     * 根據當前Shiro Subject獲取當前用戶對象
     * @return WrUser 返回一個用戶實體對象
     */
    WrUser getCurrentUser();

    /**
     * 根據Shiro Subject獲取用戶對象
     * @param subject Shiro Subject
     * @return WrUser 返回一個用戶實體對象
     */
    WrUser findUserBySubject(Subject subject);

    /**
     * 根據用戶ID查詢
     * @param userID 用戶ID
     * @return WrUser 返回一個用戶實體對象
     */
    WrUser findUserByUserID(String userID);

    /**
     * 根據用戶ID查詢
     * @param userID 用戶ID
     * @return WrUser 返回一個用戶實體對象
     */
    WrUser findUserByUserID(Long userID);

    /**
     * 根據帳號查詢
     * @param userName 用戶名
     * @return WrUser 返回一個用戶實體對象
     */
    WrUser findUserByUserName(String userName);

    /**
     * 根據用戶ID查詢
     * @param userID 用戶ID
     * @return WrUserRole 返回所有角色權限實體對象
     */
    List<WrUserRole> findRolesByUserID(String userID);

    /**
     * 根據用戶ID查詢
     * @param userID 用戶ID
     * @return WrUserRole 返回所有角色權限實體對象
     */
    List<WrUserRole> findRolesByUserID(Long userID);

    /**
     * 根據帳號查詢
     * @param userName 用戶名
     * @return WrUserRole 返回所有角色權限實體對象
     */
    List<WrUserRole> findRolesByUserName(String userName);

    /**
     * 根據權限ID獲取權限名
     * @param RoleID 權限ID
     * @return 權限名
     */
    WrRole getRoleByID(String RoleID);

    /**
     * 根據權限ID獲取權限名
     * @param RoleID 權限ID
     * @return 權限名
     */
    WrRole getRoleByID(Long RoleID);

    /**
     * 構建會話對象
     * @param subject Shiro角色對象
     * @return Connection 會話對象
     */
    Connection getConnection(Subject subject);

}
