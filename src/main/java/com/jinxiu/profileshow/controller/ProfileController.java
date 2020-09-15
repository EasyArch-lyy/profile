package com.jinxiu.profileshow.controller;

import com.jinxiu.profileshow.common.Constants;
import com.jinxiu.profileshow.dto.Profile;
import com.jinxiu.profileshow.service.ProfileService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Resource(name = "profileService")
    private ProfileService profileService;

    /**
     * 根据应用名获取配置
     */
    @RequestMapping("/getProfile")
    public Profile getProfile(@RequestParam("name") String name) {
        return profileService.getProfile(name);
    }

    /**
     * 导航页使用
     */
    @RequestMapping("/getPage")
    public List<Profile> getPage(){
        return profileService.getPage();
    }

    /**
     * 获取服务器账号密码ip
     */
    @RequestMapping("/getLoginYunMsg")
    public List<Map<String,String>> getLoginYunMsg(){
        return profileService.getLoginYunMsg();
    }

    /**
     * 插入profile配置返回插入结果
     * @param pro
     */
    @RequestMapping(value = "/addProfile",method = RequestMethod.POST)
    public boolean addProfile(@RequestBody Profile pro) {
        return profileService.addProfile(pro);
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
            String user = (String) request.getSession().getAttribute(Constants.NOW_USER);
            if (user != null) {
                request.getSession().removeAttribute(Constants.NOW_USER);
                request.getSession().removeAttribute(Constants.NOW_USER_ACCOUNT);
                request.getSession().removeAttribute(Constants.NOW_USER_PWD);
            }
        }
        return Constants.API_RET_SUCCESS;
    }

}
