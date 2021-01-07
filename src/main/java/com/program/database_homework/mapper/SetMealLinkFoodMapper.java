package com.program.database_homework.mapper;

import com.program.database_homework.domain.entity.SetMealLinkFood;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SetMealLinkFoodMapper {
    int deleteByPrimaryKey(@Param("foodId") Integer foodId, @Param("setMealId") Integer setMealId);

    int insert(SetMealLinkFood record);

    List<SetMealLinkFood> selectAll();
}