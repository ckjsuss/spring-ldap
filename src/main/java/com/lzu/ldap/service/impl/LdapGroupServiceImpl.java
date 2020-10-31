package com.lzu.ldap.service.impl;

import com.lzu.ldap.entry.Group;
import com.lzu.ldap.service.LdapGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;

/**
 * @author Liu
 * @version 1.0.0.1
 * @date 2020/10/28 9:54
 */
@Service
public class LdapGroupServiceImpl implements LdapGroupService {
    @Autowired
    private LdapTemplate ldapTemplate;

    @Override
    public boolean addGroup(Group group) {
        //开通AD域
        try {
            DirContext ctx = ldapTemplate.getContextSource().getReadWriteContext();
            BasicAttributes attrs = new BasicAttributes();
            BasicAttribute objClassSet = new BasicAttribute("objectClass");
            objClassSet.add("top");
            objClassSet.add("objectClass");
            attrs.put(objClassSet);
            ctx.createSubcontext("cn=newGroup,ou=groups,dc=lz,dc=com",attrs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private Attributes buildAttributes(Group group) {
        Attributes attrs = new BasicAttributes();
        BasicAttribute ocAttr = new BasicAttribute("objectclass");
        ocAttr.add("top");
        ocAttr.add("group");
        attrs.put(ocAttr);
        attrs.put("cn", group.getCommonName());
        attrs.put("sAMAccountName", group.getAccountName());
        attrs.put("name", group.getName());
//        attrs.put("groupType", group.getGroupType());
        return attrs;
    }
}
