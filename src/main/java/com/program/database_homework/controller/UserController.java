package com.program.database_homework.controller;

import com.program.database_homework.common.result.HttpResult;
import com.program.database_homework.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Auther: Bluebear
 * @Date: 2021/01/06/19:36
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String userRegister(
            @RequestParam(value = "userName") String userName
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "phoneNumber") String phoneNumber, HttpSession session) {
        userService.userRegister(userName, password, phoneNumber, 0);
        return userLogin(userName, password, session);
    }


    @PostMapping("/login")
    public String userLogin(
            @RequestParam(value = "userName") String userName
            , @RequestParam(value = "password") String password
            , HttpSession session) {
        HttpResult user = userService.userLogin(userName, password);
        session.setAttribute("user", user);
        if (user.getSuccess()) {
            return "userIndex";
        }
        return "userLogin";
    }


    @GetMapping("/delAddress")
    public String userAddAddress(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "addressId") Integer addressId, Model model) {
        userService.userDelAddress(userId, addressId);
        HttpResult addresses = userService.userGetAddress(userId);
        model.addAttribute("addresses", addresses);
        return "address";
    }

    @GetMapping("/getAddress")
    public String userGetAddress(
            @RequestParam(value = "userId") Integer userId, Model model) {
        HttpResult addresses = userService.userGetAddress(userId);
        model.addAttribute("addresses", addresses);
        return "address";
    }

    @PostMapping("/addAddress")
    public String userAddAddress(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "address") String address, Model model) {
        userService.userAddAddress(userId, address);
        HttpResult addresses = userService.userGetAddress(userId);
        model.addAttribute("addresses", addresses);
        return "address";
    }

    @GetMapping("/getFood")
    public String userGetFood(
            @RequestParam(value = "userId") Integer userId, Model model) {
        HttpResult addresses = userService.userGetAddress(userId);
        model.addAttribute("addresses", addresses);
        HttpResult foods = userService.userGetFood(userId);
        model.addAttribute("foods", foods);
        HttpResult types = userService.userGetType(userId);
        model.addAttribute("types", types);
        HttpResult setMeals = userService.userGetSetMeal(userId);
        model.addAttribute("setMeals", setMeals);
        HttpResult orders = userService.userGetOrder(userId);
        model.addAttribute("orders", orders);
        return "userFood_Order";
    }


    @PostMapping("/addOrder")
    public String userAddOrder(
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "addressId") Integer addressId,
            @RequestParam(value = "foodIds") List<Integer> foodIds,
            @RequestParam(value = "setMealIds") List<Integer> setMealIds, Model model
    ) {
        userService.userAddOrder(userId, addressId, foodIds, setMealIds);
        return userGetFood(userId, model);
    }

    @GetMapping("/finishOrder")
    public String userAddOrder(
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "orderId") Integer orderId,
            Model model
    ) {
        userService.userFinishOrder(userId, orderId);
        return userGetFood(userId, model);
    }
}
