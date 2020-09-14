package com.jinxiu.profileshow.dao;

import com.jinxiu.profileshow.dto.Profile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("profileDao")
public interface ProfileDao {

    int addProfile(@Param("profile") Profile profile);

    Profile getProfile(@Param("name") String name);

    int selectNums();

    List<Map<String,String>> getLoginYunMsg();

    List<Profile>getPage();
}
