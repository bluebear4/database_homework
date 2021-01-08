package com.program.database_homework.controller;

import com.program.database_homework.common.result.HttpResult;
import com.program.database_homework.service.UserService;
import org.springframework.web.bind.annotation.*;

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
        return userService.userRegister(userName, password, phoneNumber, 0);
    }

    @PostMapping("/login")
    public HttpResult userLogin(
            @RequestParam(value = "userName") String userName
            , @RequestParam(value = "password") String password) {
        return userService.userLogin(userName, password);
    }

    @PostMapping("/addAddress")
    public HttpResult userAddAddress(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "address") String address) {
        return userService.userAddAddress(userId, address);
    }

    @DeleteMapping("/delAddress")
    public HttpResult userAddAddress(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "addressId") Integer addressId) {
        return userService.userDelAddress(userId, addressId);
    }

    @GetMapping("/getAddress")
    public HttpResult userGetAddress(
            @RequestParam(value = "userId") Integer userId) {
        return userService.userGetAddress(userId);
    }

    @GetMapping("/getOrder")
    public HttpResult userGetOrder(
            @RequestParam(value = "userId") Integer userId) {
        return userService.userGetOrder(userId);
    }
}
