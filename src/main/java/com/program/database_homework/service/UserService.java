package com.program.database_homework.service;


import com.program.database_homework.common.result.HttpResult;
import org.springframework.stereotype.Service;

/**
 * @Auther: Bluebear
 * @Date: 2021/01/06/19:33
 * @Description:
 */

public interface UserService {
    HttpResult userRegister(String userName,String password,String phoneNumber);
}
