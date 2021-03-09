package com.test.guava;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class TestGuavalocalCacheUtil {
	private static final GuavaLocalCacheUtil<Object> cache = new GuavaLocalCacheUtil<Object>();

	private static final Callable<Object> loader = new Callable<Object>() {

		@Override
		public Object call() throws Exception {
			// TODO Auto-generated method stub
			return 1;
		}

	};

	public static void main(String[] args) throws ExecutionException {
		cache.put("1", "1");
		cache.put("2", 2);
		cache.put("3", 3.00);
		System.out.println(cache.getItemWithClassType("1", loader, String.class));
		System.out.println(cache.getItemWithClassType("2", loader, String.class));
		System.out.println(cache.getItemWithClassType("3", loader, String.class));
	}
}
