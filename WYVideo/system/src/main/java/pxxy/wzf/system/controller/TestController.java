package pxxy.wzf.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * @auther: 王智芳
     * @Description 测试接口
     * @date: 2021/4/5 9:22
     */
    @RequestMapping("/test")
    public String test(){
        return "测试接口是否有用";
    }
}
