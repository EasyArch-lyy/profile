package com.jinxiu.profileshow.controller;

import com.jinxiu.profileshow.common.Constants;
import com.jinxiu.profileshow.dto.User;
import com.jinxiu.profileshow.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public User getUser(@RequestParam("name") String name) {

        return userService.getUser(name);
    }

    @RequestMapping(value = "/delUser")
    public boolean delUser(@RequestParam("name") String name) {

        return userService.deleteUserByN(name);
    }

    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    public User getCurrentUser(@RequestHeader("host") String hostName) {

        return userService.currentUser(hostName);
    }

    @RequestMapping(value = "/getAllUser")
    public List<User> getUserList() {

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
    public boolean checkUserInfo(@RequestParam("account") Integer account,
                                 @RequestParam("passwd") String passwd,
                                 @RequestHeader("host") String host) {

        return userService.login(account, passwd, host);
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
    public String logout(HttpServletRequest request) {

        return userService.logout(request);
    }

    /**
     * 登录认证
     *
     * @param userName
     * @param password
     * @param type     登录类型(账号登录or验证码登录)
     * @return 用户信息(登录状态 ， 登录类型 ， 权限级别)
     */
    @RequestMapping(value = "/getloginuser", method = RequestMethod.GET)
    public Map<String, String> getLoginUser(@RequestParam("userName") Integer userName,
                                            @RequestParam("password") String password,
                                            @RequestParam("type") String type,
                                            @RequestHeader("host") String host) {

        return userService.getLoginUser(userName, password, host);
    }

    @RequestMapping(value = "/searchUsers", method = RequestMethod.POST)
    public List<User> searchUsers(@RequestBody User user) {

        return userService.searchUsers(user);
    }

    @RequestMapping(value = "/getloginuserByPhone", method = RequestMethod.GET)
    public User getloginuserByPhone(@RequestParam("mobile")String mobile,
                                    @RequestParam("captcha")Integer captcha,
                                    @RequestParam("type")String type){

        return userService.getloginuserByPhone(mobile,captcha,type);
    }

//    /login/getloginuser?mobile=18522015989&captcha=1234&type=mobile
}
