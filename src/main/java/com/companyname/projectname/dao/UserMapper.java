package com.companyname.projectname.dao;

import com.companyname.projectname.entity.po.UserPo;

/**
 * user表的dao
 */
public interface UserMapper {
    int insert(UserPo record);

    UserPo selectByPrimaryKey(Integer id);
}