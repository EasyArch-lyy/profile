package com.jinxiu.profileshow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Service("operateService")
public class OperateService {

    private static final Logger logger = LoggerFactory.getLogger(OperateService.class);

    public String operateCmd(String cmd){
        String a = null;
        try {
            a=exec(cmd,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public static String exec(String cmd, File dir) throws Exception {
        StringBuilder result = new StringBuilder();
        Process process = null;
        BufferedReader bufferIn = null;
        BufferedReader bufError = null;
        try {
            process = Runtime.getRuntime().exec(cmd,null,dir);
            process.waitFor();
            bufferIn = new BufferedReader(new InputStreamReader(process.getInputStream()));
            bufError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = null;
            while ((line = bufferIn.readLine()) != null){
                result.append(line).append('\n');
            }
            while ((line = bufError.readLine()) != null) {
                result.append(line).append('\n');
            }
            logger.info("执行shell命令：");
        }finally {
            closeStream(bufferIn);
            closeStream(bufError);

            if (process != null){
                //销毁子进程
                process.destroy();
            }
        }
        // 返回执行结果
        return result.toString();
    }

    private static void closeStream(Closeable stream){
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
