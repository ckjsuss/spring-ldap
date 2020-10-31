//package com.lzu.ldap.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
//import org.springframework.ldap.core.ContextSource;
//import org.springframework.ldap.core.LdapTemplate;
//import org.springframework.ldap.core.support.LdapContextSource;
//
///**
// * @author Liu
// * @version 1.0.0.1
// * @date 2020/10/19 16:40
// */
//@Configuration
//@EnableLdapRepositories
//public class SptingDataLdapConfig {
//    @Value("${spring.ldap.urls}")
//    private String ldapUrl;
//    @Value("${spring.ldap.username}")
//    private String userName;
//    @Value("${spring.ldap.password}")
//    private String passWord;
//    @Value("${spring.ldap.base}")
//    private String base;
//
//
//    @Bean
//    public ContextSource contextSource() {
//        LdapContextSource ldapContextSource = new LdapContextSource();
//        ldapContextSource.setBase(base);
//        ldapContextSource.setUrl(ldapUrl);
//        ldapContextSource.setUserDn(userName);
//        ldapContextSource.setPassword(passWord);
//        return ldapContextSource;
//    }
//
//    @Bean
//    public LdapTemplate ldapTemplate(ContextSource contextSource) {
//        return new LdapTemplate(contextSource);
//    }
//}
