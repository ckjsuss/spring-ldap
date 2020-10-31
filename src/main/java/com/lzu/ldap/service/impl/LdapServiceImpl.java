package com.lzu.ldap.service.impl;

import com.lzu.ldap.entry.Person;
import com.lzu.ldap.service.LdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import java.util.List;

/**
 * @author Liu
 * @version 1.0.0.1
 * @date 2020/10/19 16:48
 */
@Service
public class LdapServiceImpl implements LdapService {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public Person create(Person person) {

        ldapTemplate.create(person);
        return null;
    }

    @Override
    public boolean authenticate(String userCn, String pwd) {
        EqualsFilter filter = new EqualsFilter("cn", userCn);
        try {
            // 如果不同的ou 下有相同的uid 会报IncorrectResultSizeDataAccessException;
            return ldapTemplate.authenticate("", filter.toString(), pwd);
//            return ldapTemplate.authenticate("", "(&(cn="+userCn+")((homeDirectory=/home/liuwei)))", pwd);
//            return ldapTemplate.authenticate("", "(cn="+userCn+")", pwd);
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Person> findByCn(String cn) {
        String filter = "(cn=" + cn + ")";
        List<Person> list = ldapTemplate.search("", filter, (AttributesMapper<Person>) attributes -> {
            Person person = new Person();
            Attribute attribute_cn = attributes.get("cn");
            if (attribute_cn != null) {
                person.setCn((String) attribute_cn.get());
            }

            Attribute attribute_uid = attributes.get("uid");
            if (attribute_uid != null) {
                person.setSn((String) attribute_uid.get());
            }
            return person;
        });
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public List<Person> findAllOrder(String cn, String orderAttr) {
        String filter = "(cn=" + cn + ")";
        SearchControls searchControls = new SearchControls();
        List<Person> persons = ldapTemplate.search("", filter, searchControls, (AttributesMapper<Person>) attributes -> {
            Person person = new Person();
            Attribute attribute_cn = attributes.get("cn");
            if (attribute_cn != null) {
                person.setCn((String) attribute_cn.get());
            }

            Attribute attribute_uid = attributes.get("uid");
            if (attribute_uid != null) {
                person.setSn((String) attribute_uid.get());
            }
            return person;
        });
        return persons;
    }

    @Override
    public Person modifyPerson(Person person) {
        ldapTemplate.update(person);
        return person;
    }

    @Override
    public void deletePerson(Person person) {
        ldapTemplate.delete(person);
    }

}
