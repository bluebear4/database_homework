package com.program.database_homework.mapper;

import com.program.database_homework.domain.entity.SetMeal;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SetMealMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SetMeal record);

    SetMeal selectByPrimaryKey(Integer id);

    List<SetMeal> selectAll();

    int updateByPrimaryKey(SetMeal record);
}