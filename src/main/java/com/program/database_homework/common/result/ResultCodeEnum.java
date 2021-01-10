package com.program.database_homework.common.result;

/**
 * @Auther: Bluebear
 * @Date: 2021/01/06/19:38
 * @Description:
 */
public enum ResultCodeEnum {
    /*** 通用部分 100 - 599***/
    // 成功请求
    SUCCESS(200, "successful"),
    // 重定向
    REDIRECT(301, "redirect"),
    // 资源未找到
    NOT_FOUND(404, "not found"),
    // 服务器错误
    SERVER_ERROR(500, "server error"),
    /*** 这里可以根据不同模块用不同的区级分开错误码，例如:  ***/
    // 1000～1999 区间表示用户模块错误
    User_Exists_Exception(10001, "用户已存在"),
    User_Not_Exists_Exception(10002, "用户不存在"),
    User_Login_Fail_Exception(10003, "用户名或密码错误"),
    User_User_Name_Empty_Exception(10004, "用户名为空"),
    User_Password_Empty_Exception(10005, "密码为空"),
    User_Phone_Number_Empty_Exception(10006, "手机号为空"),
    User_Password_Too_Short_Exception(10005, "密码长度不足6"),
    //
    Address_Empty_Exception(20001, "地址为空"),
    Address_Not_Exists_Exception(20002, "用户-地址不存在"),
    Address_Exists_Exception(20003, "用户-地址已存在"),
    //
    Order_Empty_Exception(30001, "订单为空"),
    //
    Type_Empty_Exception(40001, "类型为空"),
    Type_Not_Exists_Exception(40002, "类型不存在"),
    //
    Food_Empty_Exception(50001, "菜品为空"),
    Food_Not_Exists_Exception(50002, "菜品不存在"),
    //
    Set_Meal_Empty_Exception(60001, "套餐为空"),
    Set_Meal_Not_Exists_Exception(60002, "套餐不存在"),
    ;

    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
