package com.program.database_homework.service.Impl;

import com.program.database_homework.common.result.HttpResult;
import com.program.database_homework.common.result.ResultCodeEnum;
import com.program.database_homework.domain.entity.*;
import com.program.database_homework.mapper.*;
import com.program.database_homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


/**
 * @Auther: Bluebear
 * @Date: 2021/01/06/19:34
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private UserLinkAddressMapper userLinkAddressMapper;

    @Autowired
    private FoodMapper foodMapper;

    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private SetMealLinkFoodMapper setMealLinkFoodMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private OrderLinkFoodMapper orderLinkFoodMapper;

    @Autowired
    private OrderLinkSetMealMapper orderLinkSetMealMapper;

    private Boolean isUser(Integer userId) {
        List<User> users = userMapper.selectAllUser();
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private Integer addAddress(String addressName) {
        List<Address> addresses = addressMapper.selectAll();
        for (Address address : addresses) {
            if (address.getAddress().equals(addressName)) {
                return address.getId();
            }
        }
        Address address = Address.builder()
                .address(addressName)
                .build();
        addressMapper.insert(address);
        return addAddress(addressName);
    }


    @Override
    public HttpResult userRegister(String userName, String password, String phoneNumber, Integer isAdmin) {
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
    public HttpResult userLogin(String userName, String password) {
        if (userName == null || userName.length() == 0) {
            return HttpResult.failure(ResultCodeEnum.User_User_Name_Empty_Exception);
        }
        if (password == null || password.length() == 0) {
            return HttpResult.failure(ResultCodeEnum.User_Password_Empty_Exception);
        }
        List<User> users = userMapper.selectAllUser();
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
    public HttpResult userAddAddress(Integer userId, String address) {
        if (address == null || address.length() == 0) {
            return HttpResult.failure(ResultCodeEnum.Address_Empty_Exception);
        }
        if (isUser(userId).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        Integer addressId = addAddress(address);

        UserLinkAddress userLinkAddress = UserLinkAddress.builder()
                .userId(userId)
                .addressId(addressId)
                .build();
        try {
            userLinkAddressMapper.insert(userLinkAddress);
            return HttpResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResult.failure(ResultCodeEnum.Address_Exists_Exception);
        }
    }

    @Override
    public HttpResult userDelAddress(Integer userId, Integer addressId) {
        if (isUser(userId).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }

        userLinkAddressMapper.deleteByPrimaryKey(userId, addressId);
        return HttpResult.success();
    }

    @Override
    public HttpResult userGetAddress(Integer userId) {
        if (isUser(userId).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        return HttpResult.success(userMapper.selectAllAddress(userId));
    }

    @Override
    public HttpResult userGetType(Integer userId) {
        if (isUser(userId).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        return HttpResult.success(typeMapper.selectAll());
    }

    @Override
    public HttpResult userGetFood(Integer userId) {
        if (isUser(userId).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        return HttpResult.success(foodMapper.selectAll());
    }

    @Override
    public HttpResult userGetSetMeal(Integer userId) {
        if (isUser(userId).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        Map<SetMeal, List<String>> result = new HashMap<SetMeal, List<String>>();
        List<SetMealLinkFood> setMealLinkFoods = setMealLinkFoodMapper.selectAll();
        List<SetMeal> setMeals = setMealMapper.selectAll();
        List<Food> foods = foodMapper.selectAll();
        for (SetMeal setMeal : setMeals) {
            List<String> value = new ArrayList<>();
            for (SetMealLinkFood setMealLinkFood : setMealLinkFoods) {
                if (setMealLinkFood.getSetMealId().equals(setMeal.getId())) {
                    for (Food food : foods) {
                        if (food.getId().equals(setMealLinkFood.getFoodId())) {
                            value.add(food.getFoodName());
                        }
                    }
                }
            }
            result.put(setMeal, value);
        }
        return HttpResult.success(result);
    }

    @Override
    public HttpResult userAddOrder(Integer userId, Integer addressId, List<Integer> foodIds, List<Integer> setMealIds) {
        if (foodIds.isEmpty() && setMealIds.isEmpty()) {
            HttpResult.failure(ResultCodeEnum.Order_Empty_Exception);
        }
        if (isUser(userId).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        BigDecimal price = new BigDecimal(0);
        //计算总价
        for (Integer foodId : foodIds) {
            if (foodId < 0) break;
            price = price.add(foodMapper.selectByPrimaryKey(foodId).getPrice());
        }
        for (Integer setMealId : setMealIds) {
            if (setMealId < 0) break;
            price = price.add(setMealMapper.selectByPrimaryKey(setMealId).getPrice());

        }

        Order order = Order.builder()
                .userId(userId)
                .addressId(addressId)
                .price(price)
                .isFinished(0)
                .build();
        orderMapper.insert(order);
        //寻找单号插入关系
        List<Order> orders = orderMapper.selectAll();
        Integer orderId = orders.get(orders.size() - 1).getId();
        for (int i = 0; i < foodIds.size(); i++) {
            if (foodIds.get(i) < 0) break;
            OrderLinkFood orderLinkFood = OrderLinkFood.builder()
                    .foodId(foodIds.get(i))
                    .orderId(orderId)
                    .count(1)
                    .build();
            orderLinkFoodMapper.insert(orderLinkFood);
        }

        for (int i = 0; i < setMealIds.size(); i++) {
            if (setMealIds.get(i) < 0) break;
            OrderLinkSetMeal orderLinkSetMeal = OrderLinkSetMeal.builder()
                    .orderId(orderId)
                    .setMealId(setMealIds.get(i))
                    .count(1)
                    .build();
            orderLinkSetMealMapper.insert(orderLinkSetMeal);
        }
        return HttpResult.success();
    }

    @Override
    public HttpResult userGetOrder(Integer userId) {
        if (isUser(userId).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        List<Food> foods = foodMapper.selectAll();
        Map<Integer, String> foodName = new HashMap<>();
        for (Food food : foods) {
            foodName.put(food.getId(),food.getFoodName());
        }


        List<SetMeal> setMeals = setMealMapper.selectAll();
        Map<Integer, String> setMealName = new HashMap<>();
        for (SetMeal setMeal : setMeals) {
            setMealName.put(setMeal.getId(),setMeal.getSetMealName());
        }


        List<Order> orders = userMapper.selectAllOrder(userId);
        Map<Order, List<String>> result = new HashMap<Order, List<String>>();
        List<OrderLinkFood> orderLinkFoods = orderLinkFoodMapper.selectAll();
        List<OrderLinkSetMeal> orderLinkSetMeals = orderLinkSetMealMapper.selectAll();

        for (Order order : orders) {
            List<String> value= new ArrayList<>();
            for (OrderLinkFood orderLinkFood : orderLinkFoods) {
                if(order.getId().equals(orderLinkFood.getOrderId())){
                    value.add(foodName.get(orderLinkFood.getFoodId()));
                }
            }
            for (OrderLinkSetMeal orderLinkSetMeal : orderLinkSetMeals) {
                if(order.getId().equals(orderLinkSetMeal.getOrderId())){
                    value.add(setMealName.get(orderLinkSetMeal.getSetMealId()));
                }
            }
            result.put(order,value);
        }
        return HttpResult.success(result);
    }

    @Override
    public HttpResult userFinishOrder(Integer userId, Integer orderId) {
        if (isUser(userId).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        Order order = orderMapper.selectByPrimaryKey(orderId);
        order.setIsFinished(1);
        orderMapper.updateByPrimaryKey(order);
        return HttpResult.success();
    }

}
