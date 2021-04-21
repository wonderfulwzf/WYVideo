package pxxy.wzf.business.rest.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pxxy.wzf.server.service.TestService;

@RestController
@Api(tags = "接口")
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    /**
     * 用户测试
     */
    public static final String BUSINESS_NAME = "测试";

    /**
     * @auther: 王智芳
     * @Description 用于测试ssm框架连通性
     * @date: 2021/4/21 17:47
     */
    @RequestMapping("/test")
    public String test(){
        return testService.list().toString();
    }
}
