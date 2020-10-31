package com.lzu.ldap.util;

import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.controls.SubentriesRequestControl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Liu
 * @version 1.0.0.1
 */
@Component
public class LdapUtil {

    private static String host;
    @Value("${spring.ldap.host}")
    public void setHost(String host) {
        LdapUtil.host = host;
    }

    private static int port;
    @Value("${spring.ldap.port}")
    public void setPort(int port) {
        LdapUtil.port = port;
    }

    private static String bindDN;
    @Value("${spring.ldap.bindDN}")
    public void setBindDN(String bindDN) {
        LdapUtil.bindDN = bindDN;
    }

    private static String password;
    @Value("${spring.ldap.password}")
    public void setPassword(String password) {
        LdapUtil.password = password;
    }

    private static LDAPConnection connection = null;

    /**
     * 创建连接
     */
    public static LDAPConnection openConnection() {
        if (connection == null) {
            try {
                connection = new LDAPConnection(host, port, bindDN, password);
            } catch (Exception e) {
                System.out.println("Connect LDAP Server Error：\n" + e.getMessage());
            }
        }
        return connection;
    }

    /**
     * 创建DC
     * @param baseDN
     * @param dc dc名称
     */
    public static boolean createDC(String baseDN, String dc) {
        boolean flag = false;
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
                System.out.println("创建DC" + entryDN + "成功！");
                flag = true;
            } else {
                System.out.println("DC " + entryDN + "已存在！");
            }
        } catch (Exception e) {
            System.out.println("创建DC出现错误：\n" + e.getMessage());
        }
        return flag;
    }

    /**
     * 创建组织
     *
     * @param baseDN
     * @param o      组名
     */
    public static void createO(String baseDN, String o) {
        String entryDN = "o=" + o + "," + baseDN;
        try {
            // 连接LDAP
            openConnection();

            SearchResultEntry entry = connection.getEntry(entryDN);
            if (entry == null) {
                // 不存在则创建
                ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                attributes.add(new Attribute("objectClass", "top", "organization"));
                attributes.add(new Attribute("o", o));
                connection.add(entryDN, attributes);
                System.out.println("创建组织" + entryDN + "成功！");
            } else {
                System.out.println("组织" + entryDN + "已存在！");
            }
        } catch (Exception e) {
            System.out.println("创建组织出现错误：\n" + e.getMessage());
        }
    }

    /**
     * 创建组织单元
     * @param baseDN
     * @param ou 组织名称
     */
    public static void createOU(String baseDN, String ou) {
        String entryDN = "ou=" + ou + "," + baseDN;
        try {
            // 连接LDAP
            openConnection();

            SearchResultEntry entry = connection.getEntry(entryDN);
            if (entry == null) {
                // 不存在则创建
                ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                attributes.add(new Attribute("objectClass", "top", "organizationalUnit"));
                attributes.add(new Attribute("ou", ou));
                connection.add(entryDN, attributes);
                System.out.println("创建组织单元" + entryDN + "成功！");
            } else {
                System.out.println("组织单元" + entryDN + "已存在！");
            }
        } catch (Exception e) {
            System.out.println("创建组织单元出现错误：\n" + e.getMessage());
        }
    }

    /**
     * 创建用户
     *
     * @param baseDN
     * @param uid    用户uid
     */
    public static void createEntry(String baseDN, String uid) {
        String entryDN = "uid=" + uid + "," + baseDN;
        try {
            // 连接LDAP
            openConnection();
            SearchResultEntry entry = connection.getEntry(entryDN);
            if (entry == null) {
                // 不存在则创建
                ArrayList<Attribute> attributes = new ArrayList<Attribute>();
                attributes.add(new Attribute("objectClass", "top", "account"));
                attributes.add(new Attribute("uid", uid));
                connection.add(entryDN, attributes);
                System.out.println("创建用户" + entryDN + "成功！");
            } else {
                System.out.println("用户" + entryDN + "已存在！");
            }
        } catch (Exception e) {
            System.out.println("创建用户出现错误：\n" + e.getMessage());
        }
    }

    /**
     * 修改用户信息
     *
     * @param requestDN
     * @param data
     */
    public static void modifyEntry(String requestDN, HashMap<String, String> data) {
        try {
            // 连接LDAP
            openConnection();

            SearchResultEntry entry = connection.getEntry(requestDN);
            if (entry == null) {
                System.out.println(requestDN + " user:" + requestDN + " 不存在");
                return;
            }
            // 修改信息
            ArrayList<Modification> md = new ArrayList<Modification>();
            for (String key : data.keySet()) {
                md.add(new Modification(ModificationType.REPLACE, key, data.get(key)));
            }
            connection.modify(requestDN, md);

            System.out.println("修改用户信息成！");
        } catch (Exception e) {
            System.out.println("修改用户信息出现错误：\n" + e.getMessage());
        }
    }

    /**
     * 删除用户信息
     *
     * @param requestDN
     */
    public static void deleteEntry(String requestDN) {
        try {
            openConnection();
            SearchResultEntry entry = connection.getEntry(requestDN);
            if (entry == null) {
                System.out.println(requestDN + " user:" + requestDN + "不存在");
                return;
            }
            connection.delete(requestDN);
            System.out.println("删除用户信息成！");
        } catch (Exception e) {
            System.out.println("删除用户信息出现错误：\n" + e.getMessage());
        }
    }

    /**
     * 查询
     *
     * @param searchDN
     * @param filter   过滤条件
     */
    public static void queryLdap(String searchDN, String filter) {
        try {
            // 连接LDAP
            openConnection();

            // 查询企业所有用户
            SearchRequest searchRequest = new SearchRequest(searchDN, SearchScope.SUB, "(" + filter + ")");
            searchRequest.addControl(new SubentriesRequestControl());
            SearchResult searchResult = connection.search(searchRequest);
            System.out.println(">>>共查询到" + searchResult.getSearchEntries().size() + "条记录");
            int index = 1;
            for (SearchResultEntry entry : searchResult.getSearchEntries()) {
                System.out.println((index++) + "\t" + entry.getDN());
            }
        } catch (Exception e) {
            System.out.println("查询错误，错误信息如下：\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String root = "com";
        String dc = "truesens";
        String o = "kedacom";
        String ou = "people";
        String uid = "admin";
        String filter = "objectClass=account";

        createDC("dc=" + root, dc);
        createO("dc=" + dc + ",dc=" + root, o);
        createOU("o=" + o + ",dc=" + dc + ",dc=" + root, ou);
        createEntry("ou=" + ou + ",o=" + o + ",dc=" + dc + ",dc=" + root, uid);
        queryLdap("ou=" + ou + ",o=" + o + ",dc=" + dc + ",dc=" + root, filter);

        HashMap<String,String> data = new HashMap<String,String>(0);
        data.put("userid", uid);
        modifyEntry("uid="+uid+",ou="+ou+",o="+o+",dc="+dc+",dc="+root, data);

        deleteEntry("uid="+uid+",ou="+ou + ",o="+o+",dc=" + dc + ",dc=" + root);
        queryLdap("ou=" + ou + ",o=" + o + ",dc=" + dc + ",dc=" + root, filter);
    }
}
