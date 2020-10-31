package com.lzu.ldap.dao;


import com.unboundid.ldap.sdk.LDAPConnection;

import java.util.HashMap;

/**
 * LDAP 管理API
 * @author Liu
 * @version 1.0.0.1
 * @date 2020/10/19 16:47
 */
public interface LdapManageDao {
    /**
     * 创建连接
     * @return
     */
    LDAPConnection openConnection();

    /**
     * 创建DC
     * @param baseDN
     * @param dc
     * @return
     */
    String createDC(String baseDN, String dc);

    /**
     * 创建 组
     * @param baseDN
     * @param o
     * @return
     */
    String createO(String baseDN, String o);

    /**
     * 创建 DN
     * @param baseDN
     * @param ou
     * @return
     */
    String createOU(String baseDN, String ou);
    /**
     * 创建用户
     * @param baseDN
     * @param uid
     * @return
     */
    String createEntry(String baseDN, String uid);

    /**
     * 修改用户
     * @param requestDN 用户位置
     * @param data 需要修改的属性--值
     * @return
     */
    String modifyEntry(String requestDN, HashMap<String, String> data);

    /**
     *删除用户、组等
     * @param requestDN
     * @return
     */
    String deleteEntry(String requestDN);

    /**
     * 查询用户
     * @param searchDN DN 范围位置
     * @param filter 过滤条件
     * @return
     */
    String queryLdap(String searchDN, String filter);


}