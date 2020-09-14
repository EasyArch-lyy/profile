package com.jinxiu.profileshow.service;

import com.jinxiu.profileshow.dao.ProfileDao;
import com.jinxiu.profileshow.dto.Profile;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("profileService")
public class ProfileService {

    @Resource
    ProfileDao profileDao;

    public Profile getProfile(String name){
        return profileDao.getProfile(name);
    }

    public boolean addProfile(Profile profile) {
        Integer t = profileDao.addProfile(profile);
        Integer i = profileDao.selectNums();
        if (t.equals(i)) {
            return true;
        } else {
            return false;
        }
    }

    public List<Map<String,String>> getLoginYunMsg(){
        return profileDao.getLoginYunMsg();
    }

    public List<Profile> getPage(){
        return profileDao.getPage();
    }
}
