package com.youzi.balance.job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author haoshijing
 * @version 2018年05月03日 11:20
 **/

@RequestMapping("/history")
@Controller
public class SyncController {

    @RequestMapping("/index")
    public String syncHistory(Integer systemId,Integer days){
        return "ok";
    }
}
