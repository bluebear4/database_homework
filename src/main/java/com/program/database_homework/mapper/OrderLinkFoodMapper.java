package com.program.database_homework.mapper;

import com.program.database_homework.domain.entity.OrderLinkFood;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderLinkFoodMapper {
    int deleteByPrimaryKey(@Param("orderId") Integer orderId, @Param("foodId") Integer foodId);

    int insert(OrderLinkFood record);

    OrderLinkFood selectByPrimaryKey(@Param("orderId") Integer orderId, @Param("foodId") Integer foodId);

    List<OrderLinkFood> selectAll();

    int updateByPrimaryKey(OrderLinkFood record);
}