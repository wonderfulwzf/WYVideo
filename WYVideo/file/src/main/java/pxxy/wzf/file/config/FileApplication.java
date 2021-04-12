package pxxy.wzf.file.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("pxxy.wzf")
@MapperScan("pxxy.wzf.server.mapper")
public class FileApplication {
	private static final Logger LOG = LoggerFactory.getLogger(FileApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FileApplication.class);
		Environment environment = app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		LOG.info("file地址:\thttp:127.0.0.1:{}",environment.getProperty("server.port"));

	}

}
