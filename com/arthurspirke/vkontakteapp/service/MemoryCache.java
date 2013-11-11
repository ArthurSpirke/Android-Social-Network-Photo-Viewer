package com.arthurspirke.vkontakteapp.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache<K, V> {

	private final Map<K, V> cache;
	
	public MemoryCache() {
		this.cache = new ConcurrentHashMap<K, V>();
	}



	public void put(K key, V value) {
         cache.put(key, value);
	}
	
	public V get(K url){
		if(contains(url)){
			return cache.get(url);
		}
		
		return null;
	}
	
	
	public boolean contains(K url){
		return cache.containsKey(url);
	}
	
	public void clear(){
		cache.clear();
	}

}
