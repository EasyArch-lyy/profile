package com.jinxiu.profileshow.service;

import com.jinxiu.profileshow.common.LDAPCommon;
import com.jinxiu.profileshow.util.MyProperities;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.Control;
import javax.naming.ldap.PagedResultsResponseControl;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LdapServerService {
    private String ldapUsername;
    private String ldapPassword;
    private String ldapUrl;

    private static final int PAGE_SIZE = 1000;

    private Properties env = new Properties();

    private static final Logger logger = LoggerFactory.getLogger(LdapServerService.class);

    public LdapServerService(String username, String password) {
        StringBuilder sb = new StringBuilder();
        sb.append("cn=");
        sb.append(username);
        sb.append(",ou=");
        sb.append(LDAPCommon.LDAP_OU);
        String[] dcArr = StringUtils.split(LDAPCommon.LDAP_DC, ".");
        for (String aDcArr : dcArr) {
            sb.append(",dc=");
            sb.append(aDcArr);
        }
        ldapUsername = sb.toString();
        ldapPassword = password;
        ldapUrl = LDAPCommon.LDAPURL;
        initLdapEnv();
    }

    public LdapServerService() {
        ldapUsername = MyProperities.findValueByKey("DOMAIN_ACCOUNT");
        ldapPassword = MyProperities.findValueByKey("DOMAIN_PASSWORD");
        ldapUrl = LDAPCommon.LDAPURL;
        initLdapEnv();
    }

    //初始化LDAP环境变量
    private void initLdapEnv() {
        env.put(Context.INITIAL_CONTEXT_FACTORY, LDAPCommon.LDAPENVCTL);
        env.put(Context.SECURITY_AUTHENTICATION, LDAPCommon.LDAPENVAUTH);
        env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
        env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
        env.put(Context.PROVIDER_URL, ldapUrl);
    }

    public boolean verifyLdapUser() {
        InitialDirContext initialDirContext = null;
        try {
            // 链接ldap
            initialDirContext = new InitialDirContext(env);

            logger.info("LDAP认证成功: {}", new Date());
            return true;
        } catch (javax.naming.AuthenticationException e) {
            logger.error("LDAP认证失败: {}", new Date());
            return false;
        } catch (Exception e) {
            logger.error("LDAP认证出错: {}", new Date());
            return false;
        } finally {
            if (initialDirContext != null) {
                try {
                    initialDirContext.close();
                } catch (NamingException e) {
                    logger.error("Close LDAP  InitialDirContext fail", e);
                }
            }
        }
    }

    private boolean isValidName(String ldapUsername) {
        //如果不使能检查则所有都返回true
        if ("false".equalsIgnoreCase(MyProperities.findValueByKey("FILTER_DOMAIN_NAME"))) {
            return true;
        }
        String regEx = "([a-zA-Z]{1,2}([0-9]{4}))|([a-zA-Z]{2}-[a-zA-Z]?([0-9]{4}))";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(ldapUsername);
        return matcher.find() || "liuweigang".equals(ldapUsername);
    }

    private String getAttrValue(Enumeration values) {
        StringBuilder target = new StringBuilder();
        if (values != null) {
            while (values.hasMoreElements()) {
                target.append(",").append(values.nextElement().toString());
            }
        }
        return target.toString().substring(1);
    }

    private boolean isActiveUser(String attrValue) {

        try {
            int value = Integer.parseInt(attrValue);
            //最低位为10 表示该账户为disable
            return (value & 3) != 2;

        } catch (Exception e) {
            logger.error("权限值转为int异常,attrValue:" + attrValue, e);
            return false;
        }
    }

    private static byte[] parseControls(Control[] controls) {
        byte[] cookie = null;
        if (controls != null) {
            for (Control control : controls) {
                if (control instanceof PagedResultsResponseControl) {
                    PagedResultsResponseControl prrc = (PagedResultsResponseControl) control;
                    cookie = prrc.getCookie();
                }
            }
        }
        return (cookie == null) ? new byte[0] : cookie;
    }


}
