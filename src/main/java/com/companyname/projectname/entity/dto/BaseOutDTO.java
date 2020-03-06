package com.companyname.projectname.entity.dto;

import com.companyname.projectname.base.BaseError;
import lombok.Getter;

import java.io.Serializable;

/**
 * api统一返回实体类
 *
 * @author renbaojie
 */
@Getter
public class BaseOutDTO implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public BaseOutDTO() {
        this.code = 200;
        this.msg = "ok";
    }

    public BaseOutDTO setData(Object data) {
        this.data = data;
        return this;
    }

    public BaseOutDTO fail(BaseError baseError) {
        this.code = baseError.getCode();
        this.msg = baseError.getMsg();
        return this;
    }

    public BaseOutDTO fail(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }
}
