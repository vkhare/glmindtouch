package com.translations.globallink.connect.mindtouch.notification.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Component("globallinkConnectCacheBuilder")
public class GloballinkConnectCacheBuilderImpl<T> extends GloballinkConnectCacheBuilder<T> {

	private static Logger logger = Logger.getLogger(GloballinkConnectCacheBuilderImpl.class);

	private Map<String, Cache<String, T>> cacheMap = new HashMap<String, Cache<String, T>>();

	@Override
	public void createOrUpdateCache(final T data, final String cacheName) {
		try {
			if (!this.cacheMap.containsKey(cacheName)) {
				long time = PD_CACHE_EXPIRY_TIME;
				Cache<String, T> cache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(time, TimeUnit.MINUTES).build();
				cache.put(cacheName, data);
				this.cacheMap.put(cacheName, cache);
			} else {
				this.cacheMap.get(cacheName).put(cacheName, data);
			}
			logger.trace("Creating new cache " + cacheName);
		} catch (Exception e) {
			logger.error("Error in creating cache " + cacheName);
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void createOrUpdateCache(final T data, final String cacheName, long expiryTime, TimeUnit timeUnit) {
		try {
			if (!this.cacheMap.containsKey(cacheName)) {
				Cache<String, T> cache = CacheBuilder.newBuilder().maximumSize(1).expireAfterWrite(expiryTime, timeUnit).build();
				cache.put(cacheName, data);
				this.cacheMap.put(cacheName, cache);
			} else {
				this.cacheMap.get(cacheName).put(cacheName, data);
			}
			logger.trace("Creating new cache " + cacheName);
		} catch (Exception e) {
			logger.error("Error in creating cache " + cacheName);
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void deleteCache(String cacheName, Boolean likeSearch) {
		try {
			if (!likeSearch) {
				if (this.cacheMap.containsKey(cacheName)) {
					this.cacheMap.get(cacheName).invalidateAll();
					this.cacheMap.remove(cacheName);
					logger.trace("Cache " + cacheName + " cleared successfully");
				}
			} else {
				Iterator<String> iter = this.cacheMap.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					if (key.toUpperCase().contains(cacheName.toUpperCase())) {
						try {
							this.cacheMap.get(key).invalidateAll();
							iter.remove();
							logger.trace("Cache " + key + " cleared successfully");
						} catch (Exception e) {
							logger.error("Error in deleting cache " + key);
							logger.error(e.getMessage(), e);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in deleting cache " + cacheName);
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void deleteAll() {
		logger.trace("Delete All Cache is being called.");
		if (this.cacheMap != null) {
			logger.trace(this.cacheMap.keySet());
			try {
				for (String key : this.cacheMap.keySet()) {
					this.cacheMap.get(key).invalidateAll();
				}
				this.cacheMap.clear();
			} catch (Exception e) {
				logger.error("Error in deleting caches.");
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public T get(String cacheName) {
		if (this.cacheMap.containsKey(cacheName)) {
			Cache<String, T> cache = this.cacheMap.get(cacheName);
			return cache.getIfPresent(cacheName);
		}
		logger.debug("Cache " + cacheName + " does not exists");
		return null;
	}
}
