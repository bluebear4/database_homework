package com.program.database_homework.mapper;

import com.program.database_homework.domain.entity.Address;
import com.program.database_homework.domain.entity.Order;
import com.program.database_homework.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    List<User> selectAllUser();

    List<User> selectAllAdmin();

    List<Address> selectAllAddress(Integer id);

    List<Order> selectAllOrder(Integer id);

    int updateByPrimaryKey(User record);
}