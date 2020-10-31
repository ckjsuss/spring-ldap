package com.lzu.ldap.entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.List;

/**
 * @author Liu
 * @version 1.0.0.1
 */
@Entry(objectClasses = {"group", "top"})
public class Group {
    @Id
    @JsonIgnore
    private Name id;

    @DnAttribute(value = "CN", index = 0)
    @Attribute(name = "sAMAccountName")
    private String accountName;

    private String name;

    @Attribute(name = "cn")
    private String commonName;

    public Name getId() {
        return id;
    }

    public void setId(Name id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }
}
