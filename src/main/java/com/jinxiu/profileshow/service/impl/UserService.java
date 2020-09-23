package com.jinxiu.profileshow.service.impl;

import com.jinxiu.profileshow.common.Constants;
import com.jinxiu.profileshow.dao.UserDao;
import com.jinxiu.profileshow.dto.User;
import com.jinxiu.profileshow.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public boolean login(Integer account, String password) {
        return false;
    }

    /**
     * 用户登录，支持本地和LDAP用户
     */
    public boolean login(Integer account, String password, String host) {

        //从数据库中查找本地用户
        User userInfo = searchUser(account);
        if (userInfo.getPasswd().equals(password)) {
            userDao.updateStatus(userInfo.getName(), 1, host);
            return true;
        } else {
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

    public List<User> searchUsers(User user) {
        return userDao.searchUsers(user);
    }

    public Map<String,String> getLoginUser(Integer account,String password){
        Map<String, String> map = new HashMap<>();
        map.put("loginType", "account");
        if (account != null && !("").equals(account)) {
            User user = userDao.searchUser(account);
            if (user.getPasswd().equals(password)) {
                map.put("status", "ok");
                map.put("loginType", "account");
                map.put("role", user.getRole().toString());
                return map;
            }
        }
        map.put("status", "error");
        return map;
    }

    public User currentUser(String hostName){
        User user = userDao.getCurrentUser(hostName);
        return user;
    }

    public String logout(HttpServletRequest request){
        synchronized (request.getSession()) {
            String userName = (String) request.getSession().getAttribute(Constants.NOW_USER_ACCOUNT);
            if (userName != null) {
                request.getSession().removeAttribute(Constants.NOW_USER_ACCOUNT);
                request.getSession().removeAttribute(Constants.NOW_TYPE);
                request.getSession().removeAttribute(Constants.NOW_USER_PWD);
                userDao.updateStatus(userName,0,"");
            }
        }
        return Constants.API_RET_SUCCESS;
    }

}
