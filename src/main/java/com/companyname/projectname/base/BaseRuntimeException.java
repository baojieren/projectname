package com.companyname.projectname.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 *
 * @author renbaojie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BaseRuntimeException extends RuntimeException {
    public Integer code;

    public BaseRuntimeException(BaseError error) {
        super(error.getMsg());
        this.code = error.getCode();
    }
}
