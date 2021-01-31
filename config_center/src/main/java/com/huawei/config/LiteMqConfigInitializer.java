package com.huawei.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "ump.litemq")
public class LiteMqConfigInitializer implements InitializingBean {
    private List<LiteMqConfig> clients;

    private String defaultTag;

	private String appId;

	private String umpServeUrl;

	private String appSecret;

	@Inject
	Validator validator;

	/**
	 * 初始化客户端
	 */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(clients)) {
			System.out.println("====未读到配置");
            return;
        }
        for (LiteMqConfig config : clients) {
        	List<String> errorMsg =validate(config);
			System.out.println("校验结果:" + JSON.toJSONString(errorMsg) + "。配置如下：" + JSON.toJSONString(config));
        }
		System.exit(0);
		// initClient();
    }

	/**
	 * 初始化
	 */
	private List<String> validate(LiteMqConfig config) {
		Class<?> clientClass = ClientType.Consumer.typeName.equals(config.getType()) ? ClientType.Consumer.class
				: ClientType.Producer.class;
		Set<ConstraintViolation<LiteMqConfig>> res = validator.validate(config, clientClass);
		List<String> errorMsg = new ArrayList<String>();
		for (ConstraintViolation<LiteMqConfig> validateRes : res) {
			errorMsg.add(validateRes.getMessage());
		}
		return errorMsg;
	}

	@Bean
	public LocalValidatorFactoryBean initValidatorFactory() {
		return new LocalValidatorFactoryBean();
	}

	public interface ClientType {
		interface Producer {
			public static final String typeName = "producer";
		}

		interface Consumer {
			public static final String typeName = "consumer";
		}
	}
}