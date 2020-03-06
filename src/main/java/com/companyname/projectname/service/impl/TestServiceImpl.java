package com.companyname.projectname.service.impl;

import com.companyname.projectname.dao.UserMapper;
import com.companyname.projectname.entity.dto.BaseOutDTO;
import com.companyname.projectname.entity.po.UserPo;
import com.companyname.projectname.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Resource
    private UserMapper userMapper;

    @Override
    public BaseOutDTO test(int msg) {
        BaseOutDTO outDTO = new BaseOutDTO();
        UserPo userPo = userMapper.selectByPrimaryKey(msg);
        return outDTO.setData(userPo);
    }
}
