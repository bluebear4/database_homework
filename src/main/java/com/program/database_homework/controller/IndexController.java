package com.program.database_homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: Bluebear
 * @Date: 2021/01/11/17:11
 * @Description:
 */
@Controller
public class IndexController {

    @RequestMapping({"/","userLogin"})
    public String login(){
        return "userLogin";
    }

    @RequestMapping("/index")
    public String index(){
        return "userIndex";
    }

    @RequestMapping("/userRegister")
    public String userRegister(){
        return "userRegister";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
}
