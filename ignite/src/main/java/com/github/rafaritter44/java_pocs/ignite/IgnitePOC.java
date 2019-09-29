package com.github.rafaritter44.java_pocs.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

public class IgnitePOC {
	
	public static void main(String[] args) {
		Ignite ignite = Ignition.start();
		IgniteCache<String, String> cache = ignite.getOrCreateCache("test");
		cache.put("ping", "pong");
		System.out.println(cache.get("ping"));
		ignite.close();
	}
	
}
