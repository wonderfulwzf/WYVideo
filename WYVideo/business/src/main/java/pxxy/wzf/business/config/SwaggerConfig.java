package pxxy.wzf.business.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    /**
     * @auther: 王智芳
     * @Description 创建Docket对象
     * @date: 2021/4/5 9:21
     */
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("王智芳")
                .select()
                .apis(RequestHandlerSelectors.basePackage("pxxy.wzf.system.controller"))
                .build();
    }

    /**
     * @auther: 王智芳
     * @Description 修改swagger展示信息
     * @date: 2021/4/5 9:22
     */
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("王智芳","http://www.pxc.jx.cn/","80504767@qq.com");
        return new ApiInfo(
           "往忆视频:system模块信息",
                "加油搞钱,打工人",
                "v1.0",
                "http://www.pxc.jx.cn/",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }
}
