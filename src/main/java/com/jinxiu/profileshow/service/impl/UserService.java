package com.jinxiu.profileshow.service.impl;

import com.jinxiu.profileshow.common.Constants;
import com.jinxiu.profileshow.dao.UserDao;
import com.jinxiu.profileshow.dto.User;
import com.jinxiu.profileshow.service.IUserService;
import com.jinxiu.profileshow.service.LdapServerService;
import com.jinxiu.profileshow.util.Tools;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserDao userDao;

    public boolean addUser(User user) {

        Integer i = userDao.addUser(user);
        Integer y = userDao.selectNums();
        if (i.equals(y)) {
            return true;
        } else {
            return false;
        }
    }

    public String getPasswd(Integer account) {

        return userDao.getPasswd(account);
    }

    public User getUser(String name){
        return userDao.getUser(name);
    }

    public boolean deleteUserByN(String name) {

        Integer y = userDao.selectNums();
        userDao.deleteUserByN(name);
        Integer z = userDao.selectNums();
        if (y == z + 1) {
            return true;
        } else {
            return false;
        }
    }

    public List<User> getUserList(){
        return userDao.getUserList();
    }

    public User searchUser(String name){
        return name == null ? null : userDao.searchUser(name);
    }

    /**
     * 用户登录，支持本地和LDAP用户
     */
    public String login(String username, String password) {
        try {
            //从数据库中查找本地用户
            User userInfo = searchUser(username);
            // 首先通过LDAP方式验证
            LdapServerService ldapServerService = new LdapServerService(username, password);
            boolean ldapAuthentication = ldapServerService.verifyLdapUser();
            // LDAP认证失败 启动本地用户验证
            if(ldapAuthentication == false){
                logger.error("user {} login failed by LDAP,start auth local", username);
                if(userInfo != null) {
                    // 数据库的密码保存MD5哈希值
                    String sPsd = userInfo.getPasswd();
                    if (StringUtils.isEmpty(sPsd) || StringUtils.isEmpty(password) ||
                            !sPsd.equalsIgnoreCase(Tools.getMD5Str(password, null))) {
                        logger.error("user {} password check failed", username);
                        return Constants.API_RET_ERROR;
                    }
                }else{
                    logger.error("user {} does not exist in database", username);
                    return Constants.API_RET_ERROR;
                }
            }
            logger.info("user {} login success", username);
            return Constants.API_RET_SUCCESS;
        } catch (Exception e) {
            logger.error("exception from login", e);
            return Constants.API_RET_ERROR;
        }
    }

    public boolean changeAuthority(Integer account, int role) {
        return userDao.changeAuthority(account, role);
    }

    public Integer getAuthority(Integer account){
        return userDao.getAuthority(account);
    }
}
