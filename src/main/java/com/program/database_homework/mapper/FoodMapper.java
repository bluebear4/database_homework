package com.program.database_homework.mapper;

import com.program.database_homework.domain.entity.Food;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Food record);

    Food selectByPrimaryKey(Integer id);

    List<Food> selectAll();

    int updateByPrimaryKey(Food record);
}