package pxxy.wzf.system.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pxxy.wzf.server.service.TestService;

@RestController
public class TestController {

    @Autowired
    private TestService testService;
    /**
     * @auther: 王智芳
     * @Description 测试接口
     * @date: 2021/4/5 9:22
     */
    @RequestMapping("/test")
    public String test(){
        return testService.list().toString();
    }
}
