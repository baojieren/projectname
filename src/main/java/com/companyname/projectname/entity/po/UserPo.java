package com.companyname.projectname.entity.po;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserPo implements Serializable {
    /**
    * 主键id
    */
    private Integer id;

    /**
    * 名称
    */
    private String name;

    /**
    * 手机号
    */
    private String phone;

    private LocalDateTime createTime;

    private static final long serialVersionUID = 1L;
}