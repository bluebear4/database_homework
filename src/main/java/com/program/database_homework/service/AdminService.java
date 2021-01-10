package com.program.database_homework.service;

import com.program.database_homework.common.result.HttpResult;
import com.program.database_homework.domain.entity.Food;
import com.program.database_homework.domain.entity.SetMeal;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: Bluebear
 * @Date: 2021/01/09/15:13
 * @Description:
 */
public interface AdminService {
    HttpResult adminRegister(String userName, String password, String phoneNumber, Integer isAdmin);

    HttpResult adminLogin(String userName, String password);

    HttpResult adminAddType(Integer id, String typeName);

    HttpResult adminDelType(Integer id, Integer typeId);

    HttpResult adminAddFood(Integer id, Integer typeId, String foodName, BigDecimal price);

    HttpResult adminDelFood(Integer id, Integer foodId);

    HttpResult adminAddSetMeal(Integer id, String setMealName, List<Integer> foodIds);

    HttpResult adminDelSetMeal(Integer id, Integer setMealId);

    HttpResult adminFinishOrder(Integer id, Integer orderId);

}
