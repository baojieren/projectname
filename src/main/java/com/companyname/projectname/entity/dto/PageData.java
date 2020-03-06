package com.companyname.projectname.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询的数据实体类
 */
@Data
public class PageData implements Serializable {
    public int total;
    public List list;
}
