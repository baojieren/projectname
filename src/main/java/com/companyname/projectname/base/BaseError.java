package com.companyname.projectname.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author renbaojie
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class BaseError implements Serializable {
    protected int code;
    protected String msg;
    private static int COMMON_ERR_CODE_START = 10000;

    public static final BaseError USER_NOT_EXIST = new BaseError(3, "用户不存在");
    public static final BaseError ERR_PASSWORD = new BaseError(4, "密码错误");
    public static final BaseError AUTH_FAILED = new BaseError(401, "鉴权失败");
    public static final BaseError LOGIN_FAILED = new BaseError(402, "登录失败");
    public static final BaseError REJECT = new BaseError(403, "用户无权限访问");

    public static final BaseError REQUEST_ERR = new BaseError(400, "错误请求");
    public static final BaseError SYS_ERR = new BaseError(10000, "服务器异常");
    public static final BaseError DB_ERR = new BaseError(10001, "数据库异常");

    public BaseError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseError nextError(String msg) {
        COMMON_ERR_CODE_START++;
        this.code = COMMON_ERR_CODE_START;
        this.msg = msg;
        return this;
    }
}
