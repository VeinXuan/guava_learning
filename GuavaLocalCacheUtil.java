package com.test.guava;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GuavaLocalCacheUtil<T> {
	private final Cache<String, T> cache = CacheBuilder.newBuilder().maximumSize(10)
			.expireAfterWrite(30, TimeUnit.SECONDS).build();

	public void put(String key, T data) {
		this.cache.put(key, data);
	}

	public T get(String key, Callable<? extends T> loader) throws ExecutionException {
		return this.cache.get(key, loader);
	}

	/**
	 * 测试带泛型的模型
	 *
	 * @param key
	 * @param loader
	 * @param typeToken
	 * @param <X>
	 * @return
	 * @throws ExecutionException
	 */
	public <X> X getItemWithClassType(String key, Callable<? extends T> loader, Class<X> typeToken)
			throws ExecutionException {
		T t = this.cache.get(key, loader);
		X x = typeToken.cast(t);
		return x;
	}

}
