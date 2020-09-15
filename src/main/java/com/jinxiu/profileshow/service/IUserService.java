package com.jinxiu.profileshow.service;

import com.jinxiu.profileshow.dto.User;
import java.util.List;

public interface IUserService {

    public boolean addUser(User user);

    public String getPasswd(Integer account);

    public User getUser(String name);

    public boolean deleteUserByN(String name);

    public List<User> getUserList();

    public User searchUser(String name);

    public String login(String username, String password);

    public boolean changeAuthority(Integer account, int role);

    public Integer getAuthority(Integer account);
}
