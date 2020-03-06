package com.companyname.projectname.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端分页查询入参实体的父类
 *
 * @author renbaojie
 */
@Data
public class BaseInPageDTO implements Serializable {
    /**
     * 分页页码
     */
    public Integer pageNum = 1;
    /**
     * 每页大小
     */
    public Integer pageSize = 10;
}
