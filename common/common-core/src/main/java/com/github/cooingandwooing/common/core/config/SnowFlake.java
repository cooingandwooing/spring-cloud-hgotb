package com.github.cooingandwooing.common.core.config;

import com.github.cooingandwooing.common.core.properties.SnowflakeProperties;
import com.github.cooingandwooing.common.core.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ID生成配置
 *
 * @author gaoxiaofeng
 * @date 2019/4/26 11:17
 */
@Configuration
public class SnowFlake {

    @Autowired
    private SnowflakeProperties snowflakeProperties;

    @Bean
    public SnowflakeIdWorker initTokenWorker() {
        return new SnowflakeIdWorker(Integer.parseInt(snowflakeProperties.getWorkId()), Integer.parseInt(snowflakeProperties.getDataCenterId()));
    }
}
