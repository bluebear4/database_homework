package com.program.database_homework.controller;

import com.program.database_homework.common.result.HttpResult;
import com.program.database_homework.service.AdminService;

import javax.servlet.http.HttpSession;

import com.program.database_homework.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: Bluebear
 * @Date: 2021/01/09/18:10
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;

    public AdminController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public String adminRegister(
            @RequestParam(value = "userName") String userName
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "phoneNumber") String phoneNumber
            , HttpSession session) {
        adminService.adminRegister(userName, password, phoneNumber, 1);
        return adminLogin(userName, password, session);
    }

    @PostMapping("/login")
    public String adminLogin(
            @RequestParam(value = "userName") String userName
            , @RequestParam(value = "password") String password
            , HttpSession session) {
        HttpResult admin = adminService.adminLogin(userName, password);
        session.setAttribute("admin", admin);
        if (admin.getSuccess()) {
            return "adminIndex";
        }
        return "adminLogin";
    }

    @GetMapping("/getFood")
    public String adminGetFood(
            @RequestParam(value = "userId") Integer userId, Model model) {
        HttpResult foods = userService.userGetFood(userId);
        model.addAttribute("foods", foods);
        HttpResult types = userService.userGetType(userId);
        model.addAttribute("types", types);
        HttpResult setMeals = userService.userGetSetMeal(userId);
        model.addAttribute("setMeals", setMeals);
        return "adminFood_Order";
    }


    @PostMapping("/addType")
    public String adminAddType(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "typeName") String typeName
            , Model model) {
        adminService.adminAddType(userId, typeName);
        return adminGetFood(userId, model);
    }

    @PostMapping("/addFood")
    public String adminAddFood(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "typeId") Integer typeId
            , @RequestParam(value = "foodName") String foodName
            , @RequestParam(value = "price") BigDecimal price
            , Model model) {
        adminService.adminAddFood(userId, typeId, foodName, price);
        return adminGetFood(userId, model);
    }

    @PostMapping("/addSetMeal")
    public String adminAddSetMeal(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "setMealName") String setMealName
            , @RequestParam(value = "price") BigDecimal price
            , @RequestParam(value = "foodIds") List<Integer> foodIds
            , Model model) {
        adminService.adminAddSetMeal(userId, price, setMealName, foodIds);
        return adminGetFood(userId, model);
    }

    @PostMapping("/finishOrder")
    public HttpResult adminFinishOrder(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "orderId") Integer orderId) {
        return adminService.adminFinishOrder(userId, orderId);
    }

    @PostMapping("/delType")
    public String adminDelType(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "typeId") Integer typeId
            , Model model) {
        adminService.adminDelType(userId, typeId);
        return adminGetFood(userId, model);
    }

    @PostMapping("/delFood")
    public String adminDelFood(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "foodId") Integer foodId
            , Model model) {
        adminService.adminDelFood(userId, foodId);
        return adminGetFood(userId, model);
    }

    @PostMapping("/delSetMeal")
    public String adminDelSetMeal(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "setMealId") Integer setMealId
            , Model model) {
        adminService.adminDelSetMeal(userId, setMealId);
        return adminGetFood(userId, model);
    }
}
