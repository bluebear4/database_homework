package com.program.database_homework.controller;

import com.program.database_homework.common.result.HttpResult;
import com.program.database_homework.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Bluebear
 * @Date: 2021/01/06/19:36
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public HttpResult userRegister(
            @RequestParam(value = "userName") String userName
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "phoneNumber") String phoneNumber) {
        return userService.userRegister(userName, password, phoneNumber);
    }
}
