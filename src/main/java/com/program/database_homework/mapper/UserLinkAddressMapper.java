package com.program.database_homework.mapper;

import com.program.database_homework.domain.entity.UserLinkAddress;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserLinkAddressMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("addressId") Integer addressId);

    int insert(UserLinkAddress record);

    List<UserLinkAddress> selectAll();
}