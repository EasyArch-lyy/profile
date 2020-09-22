package com.jinxiu.profileshow.controller;

import com.jinxiu.profileshow.service.OperateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 对环境操作
 */

@RestController
@RequestMapping("operate")
public class OperateController {

    @Resource(name = "operateService")
    private OperateService operateService;

    @RequestMapping("/cmd")
    public String operateCmd(@RequestParam("cmd")String cmd){
        return operateService.operateCmd(cmd);
    }
}
