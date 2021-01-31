 package com.huawei.config;

import org.junit.Assert;
import org.junit.Test;

public class LiteMqConfigTest {

	@Test
	public void test() {
		Assert.assertEquals(Boolean.TRUE, "HomsConsumerR".matches("^\\w+(Consumer|Producer)$"));
	}
}
