package com.github.cooingandwooing.common.core.config;

import com.github.cooingandwooing.common.core.constant.CommonConstant;
import com.github.cooingandwooing.common.core.properties.SysProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 系统启动时的一些处理
 * SpringBoot在项目启动后会遍历所有实现CommandLineRunner的实体类并执行run方法，如果需要按照一定的顺序去执行，那么就需要在实体类上使用一个@Order注解（或者实现Order接口）来表明顺序
 * @author gaoxiaofeng
 * @date 2019/07/14 16:09
 */
@Slf4j
@AllArgsConstructor
@Component
public class AppStartupRunner implements CommandLineRunner {

    private final SysProperties sysProperties;

    @Override
    public void run(String... args) throws Exception {
        log.info("================ start command line ================ ");
        log.info("set system properties...");
        // 设置系统属性
        if (StringUtils.isNotBlank(sysProperties.getCacheExpire()))
            System.setProperty(CommonConstant.CACHE_EXPIRE, sysProperties.getCacheExpire());
        log.info("================ end command line ================");
    }
}
