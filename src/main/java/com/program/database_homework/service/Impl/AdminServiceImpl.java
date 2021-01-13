package com.program.database_homework.service.Impl;

import com.program.database_homework.common.result.HttpResult;
import com.program.database_homework.common.result.ResultCodeEnum;
import com.program.database_homework.domain.entity.*;
import com.program.database_homework.mapper.*;
import com.program.database_homework.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: Bluebear
 * @Date: 2021/01/09/15:18
 * @Description:
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private SetMealLinkFoodMapper setMealLinkFoodMapper;

    @Autowired
    private OrderMapper orderMapper;

    private Boolean isAdmin(Integer userId) {
        List<User> users = userMapper.selectAllAdmin();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public HttpResult adminRegister(String userName, String password, String phoneNumber, Integer isAdmin) {
        User user = User.builder()
                .userName(userName)
                .password(password)
                .phoneNumber(phoneNumber)
                .isAdmin(isAdmin)
                .build();
        //判空
        if (userName == null || userName.length() == 0) {
            return HttpResult.failure(ResultCodeEnum.User_User_Name_Empty_Exception);
        }
        if (password == null) {
            return HttpResult.failure(ResultCodeEnum.User_Password_Empty_Exception);
        }
        if (phoneNumber == null || phoneNumber.length() == 0) {
            return HttpResult.failure(ResultCodeEnum.User_Phone_Number_Empty_Exception);
        }
        //判长度
        if (password.length() < 6) {
            return HttpResult.failure(ResultCodeEnum.User_Password_Too_Short_Exception);
        }
        //判重名
        try {
            userMapper.insert(user);
            return HttpResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.failure(ResultCodeEnum.User_Exists_Exception);
        }
    }

    @Override
    public HttpResult adminLogin(String userName, String password) {
        if (userName == null || userName.length() == 0) {
            return HttpResult.failure(ResultCodeEnum.User_User_Name_Empty_Exception);
        }
        if (password == null || password.length() == 0) {
            return HttpResult.failure(ResultCodeEnum.User_Password_Empty_Exception);
        }
        List<User> users = userMapper.selectAllAdmin();
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                if (user.getPassword().equals(password)) {
                    return HttpResult.success(user);
                }
            }
        }
        return HttpResult.failure(ResultCodeEnum.User_Login_Fail_Exception);
    }

    @Override
    public HttpResult adminGetType(Integer id) {
        if (isAdmin(id).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        return HttpResult.success(typeMapper.selectAll());
    }

    @Override
    public HttpResult adminAddType(Integer id, String typeName) {
        if (typeName == null || typeName.length() == 0) {
            return HttpResult.failure(ResultCodeEnum.Type_Empty_Exception);
        }
        if (isAdmin(id).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }

        List<Type> types = typeMapper.selectAll();
        for (Type type : types) {
            if (type.getTypeName().equals(typeName)) {
                return HttpResult.success(type.getId());
            }
        }
        Type type = Type.builder()
                .typeName(typeName)
                .build();
        typeMapper.insert(type);
        return adminAddType(id, typeName);
    }

    @Override
    public HttpResult adminDelType(Integer id, Integer typeId) {
        if (isAdmin(id).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        Type type = typeMapper.selectByPrimaryKey(typeId);
        if (type == null) return HttpResult.failure(ResultCodeEnum.Type_Not_Exists_Exception);
        typeMapper.deleteByPrimaryKey(typeId);
        return HttpResult.success(type.getTypeName() + "已删除");
    }

    @Override
    public HttpResult adminAddFood(Integer id, Integer typeId, String foodName, BigDecimal price) {
        if (isAdmin(id).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        if (foodName == null || foodName.length() == 0) {
            return HttpResult.failure(ResultCodeEnum.Food_Empty_Exception);
        }
        Type type = typeMapper.selectByPrimaryKey(typeId);
        if (type == null) return HttpResult.failure(ResultCodeEnum.Type_Not_Exists_Exception);
        Food food = Food.builder()
                .typeId(typeId)
                .foodName(foodName)
                .price(price)
                .build();
        foodMapper.insert(food);
        return HttpResult.success();
    }

    @Override
    public HttpResult adminDelFood(Integer id, Integer foodId) {
        if (isAdmin(id).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        Food food = foodMapper.selectByPrimaryKey(foodId);
        if (food == null) return HttpResult.failure(ResultCodeEnum.Food_Not_Exists_Exception);
        foodMapper.deleteByPrimaryKey(foodId);
        return HttpResult.success(food.getFoodName() + "已删除");
    }


    @Override
    public HttpResult adminAddSetMeal(Integer id, BigDecimal price, String setMealName, List<Integer> foodIds) {
        if (isAdmin(id).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        if (setMealName == null || setMealName.length() == 0) {
            return HttpResult.failure(ResultCodeEnum.Set_Meal_Empty_Exception);
        }
        if (foodIds.isEmpty() == true) {
            return HttpResult.failure(ResultCodeEnum.Food_Empty_Exception);
        }
        SetMeal setMeal = SetMeal.builder()
                .setMealName(setMealName)
                .price(price)
                .build();
        setMealMapper.insert(setMeal);

        List<SetMeal> setMeals = setMealMapper.selectAll();
        Integer setMealId = setMeals.get(setMeals.size() - 1).getId();
        for (Integer foodId : foodIds) {
            SetMealLinkFood setMealLinkFood = SetMealLinkFood.builder()
                    .setMealId(setMealId)
                    .foodId(foodId)
                    .build();
            setMealLinkFoodMapper.insert(setMealLinkFood);
        }
        return HttpResult.success();
    }

    @Override
    public HttpResult adminDelSetMeal(Integer id, Integer setMealId) {
        if (isAdmin(id).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        SetMeal setMeal = setMealMapper.selectByPrimaryKey(setMealId);
        if (setMeal == null) return HttpResult.failure(ResultCodeEnum.Set_Meal_Not_Exists_Exception);
        setMealMapper.deleteByPrimaryKey(setMealId);
        return HttpResult.success(setMeal.getSetMealName() + "已删除");
    }

    @Override
    public HttpResult adminFinishOrder(Integer id, Integer orderId) {
        if (isAdmin(id).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        Order order = orderMapper.selectByPrimaryKey(orderId);
        order.setIsFinished(1);
        orderMapper.updateByPrimaryKey(order);
        return HttpResult.success();
    }


}
