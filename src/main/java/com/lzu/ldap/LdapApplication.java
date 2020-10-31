package com.lzu.ldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author lw
 */

@SpringBootApplication
//@ComponentScan({"com.lzu.ldap","com.lzu.ldap.util"})
public class LdapApplication {

    public static void main(String[] args) {
        SpringApplication.run(LdapApplication.class, args);
    }

}
