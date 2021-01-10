package com.program.database_homework.service;


import com.program.database_homework.common.result.HttpResult;
import com.program.database_homework.domain.entity.Food;
import com.program.database_homework.domain.entity.SetMeal;

import java.util.List;

/**
 * @Auther: Bluebear
 * @Date: 2021/01/06/19:33
 * @Description:
 */

public interface UserService {
    HttpResult userRegister(String userName, String password, String phoneNumber, Integer isAdmin);

    HttpResult userLogin(String userName, String password);

    HttpResult userAddAddress(Integer userId, String address);

    HttpResult userDelAddress(Integer userId, Integer addressId);

    HttpResult userGetAddress(Integer userId);

    HttpResult userAddOrder(Integer userId, Integer addressId, List<Integer> foodIds, List<Integer> foodsCount, List<Integer> setMealIds, List<Integer> setMealsCount);

    HttpResult userGetOrder(Integer userId);

}
