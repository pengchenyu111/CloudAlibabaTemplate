package com.pcy.config.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * JetCache配置类，注意生效包名
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.pcy.service.impl")
public class JetCacheConfig {

}
