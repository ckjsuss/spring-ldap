package com.lzu.ldap.service;

import com.lzu.ldap.entry.Group;

/**
 * 组管理
 * @author Liu
 * @version 1.0.0.1
 * @date 2020/10/28 9:53
 */
public interface LdapGroupService {
    /**
     * 创建组
     * @param group
     * @return
     */
    boolean addGroup(Group group);
}
