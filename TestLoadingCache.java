 package com.test.guava;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.extern.log4j.Log4j;

@Log4j
public class TestLoadingCache {
	private static int count = 1;
	public static final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
			.expireAfterWrite(1, TimeUnit.SECONDS)
			.build(new CacheLoader<String, String>() {
				@Override
				public String load(String key) throws Exception {
					log.error(key + "=====>" + count);
					return key + count++;
				}
			});

	public static void main(String args[]) {
		final String key = "a";
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						String value = cache.get(key);
						log.info(key + "=====>" + value);
						Thread.sleep(500);
					}
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
