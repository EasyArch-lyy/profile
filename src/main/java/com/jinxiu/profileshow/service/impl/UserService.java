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

    public User searchUser(Integer account){
        return account == null ? null : userDao.searchUser(account);
    }

    /**
     * 用户登录，支持本地和LDAP用户
     */
    public boolean login(Integer account, String password) {
            //从数据库中查找本地用户
        User userInfo = searchUser(account);
        if (userInfo.getPasswd().equals(password)) {
            return true;
        }else{
            return false;
        }
    }

    public boolean changeAuthority(Integer account, int role) {
        return userDao.changeAuthority(account, role);
    }

    public Integer getAuthority(Integer account){
        return userDao.getAuthority(account);
    }

    public boolean checkUserInfo(Integer account, String passwd) {

        if (passwd.equals(userDao.getPasswd(account))) {
            return true;
        } else {
            return false;
        }
    }
}
