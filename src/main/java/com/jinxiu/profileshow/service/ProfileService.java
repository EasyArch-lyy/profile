package com.jinxiu.profileshow.service;

import com.jinxiu.profileshow.dao.AssetDao;
import com.jinxiu.profileshow.dao.ProfileDao;
import com.jinxiu.profileshow.dto.Asset;
import com.jinxiu.profileshow.dto.Profile;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("profileService")
public class ProfileService {

    @Resource
    ProfileDao profileDao;

    @Resource
    AssetDao assetDao;

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

    // 获取所有服务器资源
    public List<Asset> getAssets(){
        return assetDao.getAssets();
    }

}
