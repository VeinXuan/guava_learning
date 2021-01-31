package com.huawei.config;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.huawei.config.LiteMqConfigInitializer.ClientType;

import lombok.Data;

@Data
public class LiteMqConfig {
	@Pattern(groups = { ClientType.Producer.class,
			ClientType.Consumer.class }, regexp = "^T_\\w+$", message = "topic 不能为空且必须以T_开头")
	private String topic;

	@Min(groups = { ClientType.Consumer.class }, value = 1, message = "线程数不能小于1")
	private int threadNum;

	@Pattern(regexp = "^\\w+(Consumer|Producer)$", groups = { ClientType.Producer.class,
			ClientType.Consumer.class }, message = "configId不能为空且必须以Consumer或者Producer结尾")
	private String configId;

	@Pattern(regexp = "^\\w+$", groups = { ClientType.Consumer.class }, message = "subGroup不能为空")
	private String subGroup;

	@Pattern(regexp = "^\\w+$", groups = { ClientType.Consumer.class }, message = "tag 不能为空")
	private String tag;

	@Pattern(groups = { ClientType.Producer.class,
			ClientType.Consumer.class }, regexp = "^(producer|consumer)$", message = "type 可配置范围只能是consumer、producer")
	private String type;
}