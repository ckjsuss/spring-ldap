package com.lzu.ldap.dao.impl;

import com.lzu.ldap.dao.LdapManageDao;
import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.controls.SubentriesRequestControl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Liu
 * @version 1.0.0.1
 * @date 2020/10/31 14:39
 */

@Service
public class LdapManageDaoImpl implements LdapManageDao {
    @Value("${spring.ldap.host}")
    private String host;

    @Value("${spring.ldap.port}")
    private int port;

    @Value("${spring.ldap.bindDN}")
    private String bindDN;

    @Value("${spring.ldap.password}")
    private String password;

    private static LDAPConnection connection = null;

    /**
     * 创建连接
     */
    @Override
    public LDAPConnection openConnection() {
        if (connection == null) {
            try {
                connection = new LDAPConnection(host, port, bindDN, password);
            } catch (Exception e) {
                System.out.println("Connect LDAP Server Error：\n" + e.getMessage());
            }
        }
        return connection;
    }

    @Override
    public String createDC(String baseDN, String dc) {
        String resultStr = null;
        String entryDN = "dc=" + dc + "," + baseDN;
        try {
            if (connection == null) {
                openConnection();
            }
            SearchResultEntry entry = connection.getEntry(entryDN);
            if (entry == null) {
                ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                attributes.add(new Attribute("objectClass", "top", "organization", "dcObject"));
                attributes.add(new Attribute("dc", dc));
                attributes.add(new Attribute("o", dc));
                connection.add(entryDN, attributes);
                resultStr = "创建DC：" + entryDN + "成功！";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    @Override
    public String createO(String baseDN, String o) {
        String entryDN = "o=" + o + "," + baseDN;
        try {
            if (connection == null) {
                openConnection();
            }
            SearchResultEntry entry = connection.getEntry(entryDN);
            if (entry == null) {
                // 不存在则创建
                ArrayList<Attribute> attributes = new ArrayList<>();
                attributes.add(new Attribute("objectClass", "top", "organization"));
                attributes.add(new Attribute("o", o));
                connection.add(entryDN, attributes);
                return "创建组织" + entryDN + "成功！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "创建组织出现错误：\n" + e.getMessage();
        }
        return  "组织" + entryDN + "已存在！";
    }


    @Override
    public String createOU(String baseDN, String ou) {
        String resultStr;
        String entryDN = "ou=" + ou + "," + baseDN;
        try {
            if (connection == null) {
                openConnection();
            }
            SearchResultEntry entry = connection.getEntry(entryDN);
            if (entry == null) {
                // 不存在则创建
                ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                attributes.add(new Attribute("objectClass", "top", "organizationalUnit"));
                attributes.add(new Attribute("ou", ou));
                connection.add(entryDN, attributes);
                resultStr = "创建组织单元" + entryDN + "成功！";
            } else {
                resultStr = "组织单元" + entryDN + "已存在！";
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultStr = "创建组织单元出现错误：" + e.getMessage();
        }
        return resultStr;
    }

    @Override
    public String createEntry(String baseDN, String uid) {
        String resultStr;
        String entryDN = "uid=" + uid + "," + baseDN;
        try {
            if (connection == null) {
                openConnection();
            }
            SearchResultEntry entry = connection.getEntry(entryDN);
            if (entry == null) {
                // 不存在则创建
                ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                attributes.add(new Attribute("objectClass", "top", "account"));
                attributes.add(new Attribute("uid", uid));
                connection.add(entryDN, attributes);
                resultStr = "创建用户" + entryDN + "成功！";
            } else {
                resultStr = "用户" + entryDN + "已存在！";
            }
        } catch (Exception e) {
            resultStr = "创建用户出现错误：\n" + e.getMessage();
        }
        return  resultStr;
    }

    @Override
    public String modifyEntry(String requestDN, HashMap<String, String> data) {
        String resultStr;
        try {
            if (connection == null) {
                openConnection();
            }
            SearchResultEntry entry = connection.getEntry(requestDN);
            if (entry == null) {
                resultStr =  requestDN + " user:" + requestDN + " 不存在";
            }
            // 修改信息
            ArrayList<Modification> md = new ArrayList<Modification>();
            for (String key : data.keySet()) {
                md.add(new Modification(ModificationType.REPLACE, key, data.get(key)));
            }
            connection.modify(requestDN, md);

            resultStr = "修改用户信息成！";
        } catch (Exception e) {
            e.printStackTrace();
            resultStr = "修改用户信息出现错误：\n" + e.getMessage();
        }
        return resultStr;
    }

    @Override
    public String deleteEntry(String requestDN) {
        String resultStr;
        try {
            if (connection == null) {
                openConnection();
            }
            SearchResultEntry entry = connection.getEntry(requestDN);
            if (entry == null) {
                resultStr =  requestDN + " user:" + requestDN + "不存在";
            }
            connection.delete(requestDN);
            resultStr = "删除用户信息成！";
        } catch (Exception e) {
            e.printStackTrace();
            resultStr = "删除用户信息出现错误：\n" + e.getMessage();
        }
        return resultStr;
    }

    @Override
    public String queryLdap(String searchDN, String filter) {
        String resultStr = null;
        try {
            if (connection == null) {
                openConnection();
            }

            // 查询企业所有用户
            SearchRequest searchRequest = new SearchRequest(searchDN, SearchScope.SUB, "(" + filter + ")");
            searchRequest.addControl(new SubentriesRequestControl());
            SearchResult searchResult = connection.search(searchRequest);
            System.out.println("共查询到" + searchResult.getSearchEntries().size() + "条记录");
            int index = 1;
            for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                resultStr += (index++) + ":" + entry.getDN()+",";
            }
        } catch (Exception e) {
            resultStr = "查询错误，错误信息如下：\n" + e.getMessage();
        }
        return resultStr;
    }
}
