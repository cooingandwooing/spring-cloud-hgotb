package com.github.cooingandwooing.user.service;

import com.github.cooingandwooing.common.core.model.Log;
import com.github.cooingandwooing.common.core.service.CrudService;
import com.github.cooingandwooing.user.mapper.LogMapper;
import org.springframework.stereotype.Service;

/**
 * 日志service
 *
 * @author cooingandwooing
 * @date 2018/10/31 20:43
 */
@Service
public class LogService extends CrudService<LogMapper, Log> {
}
