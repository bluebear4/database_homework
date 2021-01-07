package com.program.database_homework.service.Impl;

import com.program.database_homework.common.result.HttpResult;
import com.program.database_homework.common.result.ResultCodeEnum;
import com.program.database_homework.domain.entity.User;
import com.program.database_homework.mapper.UserMapper;
import com.program.database_homework.service.UserService;
import org.springframework.stereotype.Service;


/**
 * @Auther: Bluebear
 * @Date: 2021/01/06/19:34
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public HttpResult userRegister(String userName, String password, String phoneNumber) {
        User user = User.builder()
                .userName(userName)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
        try{
            userMapper.insert(user);
            return HttpResult.success();
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.failure(ResultCodeEnum.User_Exists_Exception);
        }
    }
}
