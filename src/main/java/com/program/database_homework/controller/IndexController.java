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

    @RequestMapping({"/", "userLogin"})
    public String userLogin() {
        return "userLogin";
    }

    @RequestMapping("/userRegister")
    public String userRegister() {
        return "userRegister";
    }

    @RequestMapping({"/admin", "adminLogin"})
    public String adminLogin() {
        return "adminLogin";
    }

    @RequestMapping("/adminRegister")
    public String adminRegister() {
        return "adminRegister";
    }

    @RequestMapping("/index")
    public String userIndex() {
        return "userIndex";
    }

    @RequestMapping("/adminIndex")
    public String adminIndex() {
        return "adminIndex";
    }


    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
