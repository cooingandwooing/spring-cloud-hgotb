package com.github.cooingandwooing.gateway.vo;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 断言信息
 * 3.路由断言模型
 * @author gaoxiaofeng
 * @date 2019/3/27 11:08
 */
@Data
public class RoutePredicateVo {

    /**
     * 断言对应的Name
     */
    private String name;

    /**
     * 配置的断言规则
     */
    private Map<String, String> args = new LinkedHashMap<>();
}
