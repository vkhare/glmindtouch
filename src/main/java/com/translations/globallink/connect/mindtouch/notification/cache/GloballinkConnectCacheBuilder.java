package com.translations.globallink.connect.mindtouch.notification.cache;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.translations.globallink.connect.mindtouch.notification.GloballinkConstants;

public abstract class GloballinkConnectCacheBuilder<T> implements GloballinkConstants, ApplicationContextAware {

	private static Logger logger = Logger.getLogger(GloballinkConnectCacheBuilder.class);

	private static ApplicationContext context;

	public abstract void createOrUpdateCache(final T data, final String cacheName);

	public abstract void createOrUpdateCache(final T data, final String cacheName, long expiryTime, TimeUnit timeUnit);

	public abstract void deleteCache(String cacheName, Boolean likeSearch);

	public abstract T get(String cacheName);

	public abstract void deleteAll();

	@SuppressWarnings("rawtypes")
	public static void clearAllCache() throws Exception {
		if (context != null) {
			logger.info("Clearing all cache now");
			Map<String, GloballinkConnectCacheBuilder> map = context.getBeansOfType(GloballinkConnectCacheBuilder.class);
			if (map != null) {
				for (Entry<String, GloballinkConnectCacheBuilder> entry : map.entrySet()) {
					try {
						GloballinkConnectCacheBuilder.class.getMethod("deleteAll").invoke(entry.getValue());
					} catch (NoSuchMethodException e) {
						throw e;
					} catch (SecurityException e) {
						throw e;
					} catch (IllegalAccessException e) {
						throw e;
					} catch (IllegalArgumentException e) {
						throw e;
					} catch (InvocationTargetException e) {
						throw e;
					}
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void clearCache(String name, boolean likeSearch) throws Exception {
		if (context != null) {
			if (!likeSearch)
				logger.info("Clearing cache " + name);
			else
				logger.info("Clearing cache containing text " + name);
			Map<String, GloballinkConnectCacheBuilder> map = context.getBeansOfType(GloballinkConnectCacheBuilder.class);
			if (map != null) {
				for (Entry<String, GloballinkConnectCacheBuilder> entry : map.entrySet()) {
					try {
						entry.getValue().getClass().getMethod("deleteCache", new Class[] { String.class, Boolean.class }).invoke(entry.getValue(), name, likeSearch);
					} catch (NoSuchMethodException e) {
						throw e;
					} catch (SecurityException e) {
						throw e;
					} catch (IllegalAccessException e) {
						throw e;
					} catch (IllegalArgumentException e) {
						throw e;
					} catch (InvocationTargetException e) {
						throw e;
					}
				}
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
