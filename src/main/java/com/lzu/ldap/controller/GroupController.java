package com.lzu.ldap.controller;

import com.lzu.ldap.util.LdapUtil;
import com.unboundid.ldap.sdk.LDAPConnection;
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

    @RequestMapping(value = "/connect",method = RequestMethod.POST)
    public String connect(){
        LDAPConnection ldapConnection = LdapUtil.openConnection();
        if (ldapConnection.isConnected()) {
            return "success";
        }
        return "false";
    }

    @RequestMapping(value = "/createDC",method = RequestMethod.POST)
    public String createDC(@RequestParam(name = "baseDN") String baseDN, @RequestParam(name = "dc") String dc){
        boolean status = LdapUtil.createDC(baseDN, dc);
        if (status) {
            return "success";
        }
        return "false";
    }

}
