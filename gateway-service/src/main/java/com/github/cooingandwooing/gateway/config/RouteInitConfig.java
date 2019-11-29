package com.github.cooingandwooing.gateway.config;

import com.github.cooingandwooing.gateway.service.RouteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 初始化的时候加载路由数据
 *
 * @author gaoxiaofeng
 * @date 2019/4/2 14:40
 */
@Slf4j
@AllArgsConstructor
@Configuration
public class RouteInitConfig {

    private final RouteService routeService;

    @PostConstruct
    public void initRoute() {
        routeService.refresh();
    }
}
