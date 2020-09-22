package com.jinxiu.profileshow;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * cmdTest
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String result = exec("dir", new File("E:\\project\\CI"));
        System.out.println(result);
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
