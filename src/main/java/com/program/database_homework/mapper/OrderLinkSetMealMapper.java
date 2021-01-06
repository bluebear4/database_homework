package com.program.database_homework.mapper;

import com.program.database_homework.domain.entity.OrderLinkSetMeal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderLinkSetMealMapper {
    int deleteByPrimaryKey(@Param("orderId") Integer orderId, @Param("setMealId") Integer setMealId);

    int insert(OrderLinkSetMeal record);

    OrderLinkSetMeal selectByPrimaryKey(@Param("orderId") Integer orderId, @Param("setMealId") Integer setMealId);

    List<OrderLinkSetMeal> selectAll();

    int updateByPrimaryKey(OrderLinkSetMeal record);
}