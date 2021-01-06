package com.program.database_homework.mapper;

import com.program.database_homework.domain.entity.SetMeal;
import java.util.List;

public interface SetMealMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SetMeal record);

    SetMeal selectByPrimaryKey(Integer id);

    List<SetMeal> selectAll();

    int updateByPrimaryKey(SetMeal record);
}