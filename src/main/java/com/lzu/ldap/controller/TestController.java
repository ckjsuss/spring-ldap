package com.lzu.ldap.controller;

import com.lzu.ldap.entry.Person;
import com.lzu.ldap.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ldap 增删改查
 * @author Liu
 * @version 1.0.0.1
 * @date 2020/10/19 17:08
 */
@RestController
public class TestController {
    @Autowired
    private LdapService ldapService;

    @RequestMapping(value = "/findOne",method = RequestMethod.POST)
    public List<Person> findByCn(@RequestParam(name = "cn",required = true) String cn){
        return ldapService.findByCn(cn);
    }

    @PostMapping(value = "/create")
    public Person create(@RequestParam(name = "cn") String cn,@RequestParam(name = "sn") String sn,
                         @RequestParam(name = "userPassword") String userPassword){
        Person person = new Person();
        person.setCn(cn);
        person.setSn(sn);
        person.setUserPassword(userPassword);
        return ldapService.create(person);
    }

    @PostMapping(value = "/update")
    public Person update(@RequestParam(name = "cn") String cn,@RequestParam(name = "sn") String sn,
                         @RequestParam(name = "userPassword") String userPassword){
        Person person = new Person();
        person.setCn(cn);
        person.setSn(sn);
        person.setUserPassword(userPassword);
        return ldapService.modifyPerson(person);
    }

    @PostMapping(value = "/delete")
    public void delete(@RequestParam(name = "cn")String cn){
        Person person = new Person();
        person.setCn(cn);
        ldapService.deletePerson(person);
    }

}
