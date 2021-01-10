package com.program.database_homework.service.Impl;

import com.program.database_homework.common.result.HttpResult;
import com.program.database_homework.common.result.ResultCodeEnum;
import com.program.database_homework.domain.entity.*;
import com.program.database_homework.mapper.*;
import com.program.database_homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


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
    private OrderMapper orderMapper;

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
    public HttpResult userAddOrder(Integer userId, Integer addressId, List<Integer> foodIds, List<Integer> foodsCount, List<Integer> setMealIds, List<Integer> setMealsCount) {
        if (foodIds.isEmpty() || setMealIds.isEmpty()) {
            HttpResult.failure(ResultCodeEnum.Order_Empty_Exception);
        }
        if (isUser(userId).equals(Boolean.FALSE)) {
            return HttpResult.failure(ResultCodeEnum.User_Not_Exists_Exception);
        }
        BigDecimal price = new BigDecimal(0);
        //计算总价

        for (int i = 0; i < foodIds.size(); i++) {
            price = price.add(foodMapper.selectByPrimaryKey(foodIds.get(i)).getPrice()
                    .multiply(BigDecimal.valueOf(foodsCount.get(i))));
        }

        for (int i = 0; i < setMealIds.size(); i++) {
            price = price.add(setMealMapper.selectByPrimaryKey(setMealIds.get(i)).getPrice()
                    .multiply(BigDecimal.valueOf(setMealsCount.get(i))));
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
            OrderLinkFood orderLinkFood = OrderLinkFood.builder()
                    .foodId(foodIds.get(i))
                    .orderId(orderId)
                    .count(foodsCount.get(i))
                    .build();
            orderLinkFoodMapper.insert(orderLinkFood);
        }

        for (int i = 0; i < setMealIds.size(); i++) {
            OrderLinkSetMeal orderLinkSetMeal = OrderLinkSetMeal.builder()
                    .orderId(orderId)
                    .setMealId(setMealIds.get(i))
                    .count(setMealsCount.get(i))
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
        System.out.println("test");
        return HttpResult.success(userMapper.selectAllOrder(userId));
    }

}
