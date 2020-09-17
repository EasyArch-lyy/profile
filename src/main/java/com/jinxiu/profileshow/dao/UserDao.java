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

    List<User> getUserList();

    User getUser(@Param("name")String name);

    String getPasswd(@Param("account") Integer account);

    void deleteUserByA(@Param("account")String account);

    void deleteUserByN(@Param("name")String name);

    int getAuthority(@Param("account")Integer account);

    boolean changeAuthority(@Param("account") Integer account, @Param("role") Integer role);
}
