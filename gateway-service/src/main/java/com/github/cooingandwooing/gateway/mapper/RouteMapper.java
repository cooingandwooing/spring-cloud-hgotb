package com.github.cooingandwooing.gateway.mapper;

import com.github.cooingandwooing.gateway.module.Route;
import com.github.cooingandwooing.common.core.persistence.CrudMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Route mapper
 *
 * @author gaoxiaofeng
 * @date 2019/4/2 15:00
 */
@Mapper
public interface RouteMapper extends CrudMapper<Route> {
}
