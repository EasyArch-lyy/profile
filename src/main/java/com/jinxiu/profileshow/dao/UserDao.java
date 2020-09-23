package com.jinxiu.profileshow.dao;

import com.jinxiu.profileshow.dto.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserDao {

    int selectNums();

    int addUser(@Param("User") User user);

    User searchUser(@Param("account") Integer account);

    List<User> searchUsers(@Param("user") User user);

    List<User> getUserList();

    User getUser(@Param("name")String name);

    String getPasswd(@Param("account") Integer account);

    void deleteUserByA(@Param("account")String account);

    void deleteUserByN(@Param("name")String name);

    int getAuthority(@Param("account")Integer account);

    boolean changeAuthority(@Param("account") Integer account, @Param("role") Integer role);

    // 根据ip地址获取当前用户
    User getCurrentUser(@Param("host")String host);

    // 根据登录登出修改当前用户登录状态和登录ip地址
    void updateStatus(@Param("name")String name,@Param("status")Integer status, @Param("loginip")String loginIp);

    // 根据验证码获取用户
    User getloginuserByPhone(@Param("mobile")String mobile, @Param("captcha")Integer captcha);

}
