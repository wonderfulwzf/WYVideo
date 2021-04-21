package pxxy.wzf.business.rest.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "接口")
public class TestController {

    /**
     * 日志BUSINESS_NAME
     */
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    public static final String BUSINESS_NAME = "";

    @RequestMapping("/test")
    public String test(){
        return "用于测试";
    }
}
