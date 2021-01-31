 package com.test.guava;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import lombok.extern.log4j.Log4j;

@Log4j
public class TestCacheGetWithCallable {
	private static int count = 1;
	public static final Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(10)
.expireAfterWrite(30, TimeUnit.SECONDS)
			.build();

	public static void main(String args[]) {
		for (int i = 0; i < 12; i++) {
			initConsumer(i);
		}
	}

	public static void initConsumer(int id) {
		final String key = String.valueOf(id);

		Thread t = new Thread() {
			private Map<String, String> localCache = new HashMap<String, String>();
			private int timeInterval = 100;
			@Override
			public void run() {
				try {
					while (timeInterval-- > 1) {
						String value = cache.get(key, new Callable<String>() {
							@Override
							public String call() throws Exception {
								log.error("初始化" + count);
								// TODO Auto-generated method stub
								return "v" + count++;
							}
						});
						localCache.put(key, value);
						Thread.sleep(100);
					}
					log.error("id:" + JSON.toJSONString(localCache));
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
}
