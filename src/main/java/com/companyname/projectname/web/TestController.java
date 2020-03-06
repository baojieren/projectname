package com.companyname.projectname.web;

import com.companyname.projectname.entity.dto.BaseOutDTO;
import com.companyname.projectname.entity.po.UserPo;
import com.companyname.projectname.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private TestService testService;

    @GetMapping("get")
    public BaseOutDTO testGet(Integer userId) {
        return testService.test(userId);
    }
}
