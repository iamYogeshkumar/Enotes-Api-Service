package com.enotes.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.enotes.service.CacheManagerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CacheManagerImpl implements CacheManagerService {

	@Autowired
	private CacheManager cacheManager;

	@Override
	public Collection<String> getCache() {
		Collection<String> cacheNames = cacheManager.getCacheNames();

		for (String cachName : cacheNames) {

			Cache cache = cacheManager.getCache(cachName);

			log.info("Cache Name :: {}", cache);

		}

		return cacheNames;
	}

	@Override
	public Cache getCacheName(String cacheName) {

		Cache cache = cacheManager.getCache(cacheName);

		log.info("Cache Name :: {}", cache);

		return cache;
	}

	@Override
	public void removeAllCache() {

		Collection<String> cacheNames = cacheManager.getCacheNames();

		for (String cacheName : cacheNames) {

			Cache cache = cacheManager.getCache(cacheName);

			if (cache != null) {
				cache.clear();
			}
		}

		log.info("All caches cleared successfully");
	}

}
