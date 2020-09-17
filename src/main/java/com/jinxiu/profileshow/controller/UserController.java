package com.jinxiu.profileshow.controller;

import com.jinxiu.profileshow.common.Constants;
import com.jinxiu.profileshow.dto.User;
import com.jinxiu.profileshow.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public boolean addUser(@RequestBody User user) {

        return userService.addUser(user);
    }

    @RequestMapping(value = "/getPasswd")
    public String getPasswd(@RequestParam("account") Integer account) {

        return userService.getPasswd(account);
    }

    @RequestMapping(value = "/getUser")
    public User getUser(@RequestParam("name")String name){
        return userService.getUser(name);
    }

    @RequestMapping(value = "/delUser")
    public boolean delUser(@RequestParam("name")String name){
        return userService.deleteUserByN(name);
    }

    @RequestMapping(value = "/getAllUser")
    public List<User> getUserList(){
        return userService.getUserList();
    }

    /**
     * 用户登录校验
     *
     * @param account 用户账号
     * @param passwd  用户密码
     * @return boolean
     */
    @RequestMapping(value = "/checkUserInfo")
    public boolean checkUserInfo(@RequestParam("account") Integer account, @RequestParam("passwd") String passwd) {

        return userService.login(account, passwd);
    }

    /**
     * 修改用户权限
     *
     * @param account 用户账号
     * @param role    权限id
     */
    @RequestMapping(value = "/changeAuthority", method = RequestMethod.POST)
    public boolean changeAuthority(@RequestParam(value = "account") int account,
                                   @RequestParam(value = "role") int role) {

        return userService.changeAuthority(account, role);
    }

    /**
     * 查询用户权限
     *
     * @param request HttpServletRequest
     * @return 用户权限信息
     */
    @RequestMapping(value = "/getUserRole")
    public Map<String, Object> getUserRole(HttpServletRequest request) {

        Map<String, Object> paraMap = new HashMap<>();
        Integer account = ((Integer) request.getSession().getAttribute(Constants.NOW_USER_ACCOUNT));
        Integer role = userService.getAuthority(account);
        paraMap.put("userRole", role);
        return paraMap;
    }

    /**
     * 用户推出登录
     *
     * @param request HttpServletRequest
     * @return success
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request){
        synchronized (request.getSession()) {
            String user = (String) request.getSession().getAttribute(Constants.NOW_USER_NAME);
            if (user != null) {
                request.getSession().removeAttribute(Constants.NOW_USER_NAME);
                request.getSession().removeAttribute(Constants.NOW_USER_ACCOUNT);
                request.getSession().removeAttribute(Constants.NOW_USER_PWD);
            }
        }
        return Constants.API_RET_SUCCESS;
    }

    /**
     * 获取当前登录用户
     *
     * @param request HttpServletRequest
     * @return 用户信息
     */
    @RequestMapping(value = "/getloginuser", method = RequestMethod.GET)
    public Map<String, Object> getLoginUser(HttpServletRequest request) {
        Map<String, Object> paraMap = new HashMap<>();
        Integer account = (Integer) request.getSession().getAttribute(Constants.NOW_USER_ACCOUNT);
        if (account != null && !("").equals(account)) {
            User user = userService.searchUser(account);
            String name = user.getName();
            paraMap.put("name", name);
            paraMap.put("account", account);
            paraMap.put("role", user.getRole());
        }
        paraMap.put("user", account);
        return paraMap;
    }
}
