package com.jinxiu.profileshow.controller;

import com.jinxiu.profileshow.service.AntService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/antController")
public class AntController {

    @Resource(name = "antService")
    private AntService antService;

//    @RequestMapping("/api/**")
//    public ApiResult api(HttpServletRequest request, HttpServletResponse response){
//        return api
//    }
//
//    @RequestMapping(value = "/**", method = HTTPMethod.GET)
//    public String index(){
//        return "index";
//    }
}
