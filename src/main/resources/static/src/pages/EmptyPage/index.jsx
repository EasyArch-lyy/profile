import { PageContainer } from '@ant-design/pro-layout';
import React, { useState, useEffect } from 'react';
import { Spin, Card, Avatar } from 'antd';
import styles from './index.less';
// import SettingOutlined from "@ant-design/icons/lib/icons/SettingOutlined";
// import EditOutlined from "@ant-design/icons/lib/icons/EditOutlined";
// import EllipsisOutlined from "@ant-design/icons/lib/icons/EllipsisOutlined";
import Meta from "antd/es/card/Meta";
export default () => {
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    setTimeout(() => {
      setLoading(false);
    }, 3000);
  }, []);
  return (
    <PageContainer content="中间件控制页面跳转" className={styles.main}>
      <div
        style={{
          paddingTop: 100,
          textAlign: 'center',
        }}
      >
        <Spin spinning={loading} size="large"/>
      </div>
      <Card
        style={{width: 300}}
        cover={
          <img
            alt="netdata"
            src="src/assets/netdata.png"
          />
        }
        actions={[
          <a href={"http://39.100.149.36:19999"}>前往</a>
        ]}
      >
        <Meta
          // avatar={<Avatar src="src/assets/test.png"/>}
          title="netdata监控页面"
          description="综合资源监控页面"
        />
      </Card>
      <Card
        style={{width: 300}}
        cover={
          <img
            alt="hadoop"
            src="src/assets/hadoop.jpg"
          />
        }
        actions={[
          <a href={"http://39.100.149.36:9000"}>前往</a>
        ]}
      >
        <Meta
          // avatar={}
          title="hadoop监控页面"
          description=""
        />
      </Card>
    </PageContainer>
  );
};
