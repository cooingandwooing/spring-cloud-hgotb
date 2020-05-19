/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.cooingandwooing.common.core.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.cooingandwooing.common.core.cache.CustomRedisCacheWriter;
import com.github.cooingandwooing.common.core.cache.MultitenantCacheManager;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * redis配置 redis集群缓存.
 *
 * @author gaoxiaofeng
 * @date 2019-3-16 20:40
 */
@Configuration
@EnableCaching
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		// 存储对象需要配置序列化机制
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	/**
	 * 多租户cacheManager.
	 *
	 * @return RedisCacheManager
	 */
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, CacheManagerCustomizers customizerInvoker) {
		RedisCacheWriter redisCacheWriter = new CustomRedisCacheWriter(connectionFactory);
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		Map<String, RedisCacheConfiguration> initialCacheConfigurations = new LinkedHashMap<>();
		// 多租户cacheManager
		RedisCacheManager cacheManager = new MultitenantCacheManager(redisCacheWriter, redisCacheConfiguration, initialCacheConfigurations, true);
		cacheManager.setTransactionAware(false);
		return customizerInvoker.customize(cacheManager);
	}

	@Bean
	public CacheManagerCustomizers cacheManagerCustomizers(
			ObjectProvider<List<CacheManagerCustomizer<?>>> customizers) {
		return new CacheManagerCustomizers(customizers.getIfAvailable());
	}
}
