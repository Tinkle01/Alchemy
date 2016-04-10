package index.alchemy.util;

import java.util.HashMap;
import java.util.Map;

public class Cache<K, V> {
	
	protected Map<K, V> cache = new HashMap<K, V>();
	private int max;
	
	public Cache() {
		this(-1);
	}
	
	public Cache(int max) {
		this.max = max;
	}
	
	public int getMaxCache() {
		return max;
	}
	
	public Cache<K, V> setMaxCache(int max) {
		this.max = max;
		return this;
	}
	
	public V get(K key) {
		return cache.get(key);
	}
	
	public V add(K key, V val) {
		if (max > 0 && cache.size() > max) cache.clear();
		return cache.put(key, val);
	}
	
	public V del(K key) {
		return cache.remove(key);
	}
	
}
