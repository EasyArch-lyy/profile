package com.jinxiu.profileshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class ProfileShowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileShowApplication.class, args);
    }

}
