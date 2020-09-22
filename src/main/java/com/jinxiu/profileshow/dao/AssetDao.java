package com.jinxiu.profileshow.dao;

import com.jinxiu.profileshow.dto.Asset;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("assetDao")
public interface AssetDao {

    List<Asset> getAssets();

    boolean addAsset(@Param("asset")Asset asset);

    void delAsset(@Param("assetId")Integer id);

    void updateAsset(@Param("asset")Asset asset);
}
