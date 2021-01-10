package com.program.database_homework.controller;

import com.program.database_homework.common.result.HttpResult;
import com.program.database_homework.domain.entity.Food;
import com.program.database_homework.service.AdminService;
import com.program.database_homework.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: Bluebear
 * @Date: 2021/01/09/18:10
 * @Description:
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public HttpResult adminRegister(
            @RequestParam(value = "userName") String userName
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "phoneNumber") String phoneNumber) {
        return adminService.adminRegister(userName, password, phoneNumber, 1);
    }

    @PostMapping("/login")
    public HttpResult adminLogin(
            @RequestParam(value = "userName") String userName
            , @RequestParam(value = "password") String password) {
        return adminService.adminLogin(userName, password);
    }

    @PostMapping("/addType")
    public HttpResult adminAddType(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "typeName") String typeName) {
        return adminService.adminAddType(userId, typeName);
    }

    @PostMapping("/addFood")
    public HttpResult adminAddFood(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "typeId") Integer typeId
            , @RequestParam(value = "foodName") String foodName
            , @RequestParam(value = "price") BigDecimal price) {
        return adminService.adminAddFood(userId, typeId, foodName, price);
    }

    @PostMapping("/addSetMeal")
    public HttpResult adminAddSetMeal(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "setMealName") String setMealName
            , @RequestParam(value = "foodIds") List<Integer> foodIds) {
        return adminService.adminAddSetMeal(userId, setMealName, foodIds);
    }

    @PostMapping("/finishOrder")
    public HttpResult adminFinishOrder(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "orderId") Integer orderId) {
        return adminService.adminFinishOrder(userId, orderId);
    }

    @DeleteMapping("/delType")
    public HttpResult adminDelType(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "typeId") Integer typeId) {
        return adminService.adminDelType(userId, typeId);
    }

    @DeleteMapping("/delFood")
    public HttpResult adminDelFood(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "foodId") Integer foodId) {
        return adminService.adminDelFood(userId, foodId);
    }

    @DeleteMapping("/delSetMeal")
    public HttpResult adminDelSetMeal(
            @RequestParam(value = "userId") Integer userId
            , @RequestParam(value = "setMealId") Integer setMealId) {
        return adminService.adminDelSetMeal(userId, setMealId);
    }
}
