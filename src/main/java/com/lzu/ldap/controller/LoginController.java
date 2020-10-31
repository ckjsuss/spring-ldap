package com.lzu.ldap.controller;

import com.lzu.ldap.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liu
 * @version 1.0.0.1
 * @date 2020/10/26 10:43
 */
@RestController
public class LoginController {
    @Autowired
    private LdapService ldapService;

    @Autowired
    public LoginController(LdapService ldapService) {
        this.ldapService = ldapService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(name = "cn") String cn,@RequestParam(name = "password") String password) {
        System.out.println(cn + ":" + password);
        boolean authenticate = ldapService.authenticate(cn, password);
        if (authenticate) {
            return "success";
        }
        return "fail";
    }

}
