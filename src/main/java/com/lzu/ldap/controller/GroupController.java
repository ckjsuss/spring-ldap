package com.lzu.ldap.controller;

import com.lzu.ldap.dao.LdapManageDao;
import com.unboundid.ldap.sdk.LDAPConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liu
 * @version 1.0.0.1
 * @date 2020/10/28 9:59
 */
@RestController
public class GroupController {

    @Autowired
    private LdapManageDao dao;

    /**
     * 测试连接
     * @return
     */
    @RequestMapping(value = "/connect",method = RequestMethod.POST)
    public String connect(){
        LDAPConnection ldapConnection = dao.openConnection();
        if (ldapConnection == null) {
            return "ldap connect failed, check please!";
        }
        if (ldapConnection.isConnected()) {
            return "success";
        }
        return "false";
    }

    /**
     * 创建DC
     * @param baseDN
     * @param dc
     * @return
     */
    @RequestMapping(value = "/createDC",method = RequestMethod.POST)
    public String createDC(@RequestParam(name = "baseDN") String baseDN, @RequestParam(name = "dc") String dc){
        String status = dao.createDC(baseDN, dc);
        if (status == null) {
            return "failed";
        }
        return "success";
    }

}
