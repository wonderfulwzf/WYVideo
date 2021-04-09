package pxxy.wzf.business.config;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
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
public class BusinessApplication {
	/**
     * 获取日志对象
	 */
	private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

	public static void main(String[] args) {
		//雪花算法id获取
		// 创建 IdGeneratorOptions 对象，请在构造函数中输入 WorkerId：
		IdGeneratorOptions options = new IdGeneratorOptions((short) 6);
		// options.WorkerIdBitLength = 10; // WorkerIdBitLength 默认值6，支持的 WorkerId 最大值为2^6-1，若 WorkerId 超过64，可设置更大的 WorkerIdBitLength
		// ...... 其它参数设置参考 IdGeneratorOptions 定义，一般来说，只要再设置 WorkerIdBitLength （决定 WorkerId 的最大值）。

		// 保存参数（必须的操作，否则以上设置都不能生效）：
		YitIdHelper.setIdGenerator(options);
		// 以上初始化过程只需全局一次，且必须在第2步之前设置。

		SpringApplication app = new SpringApplication(BusinessApplication.class);
		Environment environment = app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		LOG.info("Business地址:\thttp:127.0.0.1:{}",environment.getProperty("server.port"));
	}
}
