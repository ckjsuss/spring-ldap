package com.lzu.ldap.service;

import com.lzu.ldap.entry.Person;

import java.util.List;

/**
 * @author Liu
 * @version 1.0.0.1
 * @date 2020/10/19 16:47
 */
public interface LdapService {

    /**
     * 创建
     * @param person
     * @return
     */
    Person create(Person person);


    /**
     * 校验用户名、密码
     * @param userCn cn 用户名
     * @param pwd 用户密码
     * @return
     */
    boolean authenticate(String userCn, String pwd);

    /**
     * 删除
     * @param person
     */
    void deletePerson(Person person);

    /**
     * 查询
     * @param cn
     * @return
     */
    List<Person> findByCn(String cn);


    /**
     * 查询 分页
     * @param cn
     * @param orderAttr
     * @return
     */
    List<Person> findAllOrder(String cn,String orderAttr);

    /**
     * 修改
     * @param person
     * @return
     */
    Person modifyPerson(Person person);

}