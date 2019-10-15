package com.taoyuanx.codegen.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.taoyuanx.codegen.dao.GenDao;
import com.taoyuanx.codegen.domain.GenConfig;
import com.taoyuanx.commons.utils.SpringContextUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author dushitaoyuan
 * @date 2019/10/1522:12
 * @desc: 内存缓存配置
 */
@Configuration
public class MemoryCacheConfig {
    @Bean("genConfigCache")
    @Autowired
    public LoadingCache<String, GenConfig> genConfigCache(GenDao genDao) {
        LoadingCache<String, GenConfig> genConfigCache = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).initialCapacity(100).maximumSize(3000).build(new CacheLoader<String, GenConfig>() {

            @Nullable
            @Override
            public GenConfig load(@NonNull String configKey) throws Exception {
                return genDao.getByKey(configKey, null);
            }
        });
        return genConfigCache;

    }
}
