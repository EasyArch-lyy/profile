package com.jinxiu.profileshow.common;

import com.jinxiu.profileshow.util.MyProperities;

public class LDAPCommon {
    private LDAPCommon(){}
    private static final String LDAP_PORT = "389";
    public static final String LDAPURL = "LDAP://" + MyProperities.findValueByKey("Domain_Controller_IP") + ":" + LDAP_PORT;
    public static final String LDAPENVCTL = "com.sun.jndi.ldap.LdapCtxFactory";

    public static final String LDAPENVAUTH = "simple";

    public static final String LDAP_OU = MyProperities.findValueByKey("ORGANIZATIONAL_UNIT");
    public static final String LDAP_DC = MyProperities.findValueByKey("DOMAIN_COMPONENT");
}
