package com.github.cooingandwooing.common.core.vo;

import com.github.cooingandwooing.common.core.model.Log;
import com.github.cooingandwooing.common.core.persistence.BaseEntity;
import lombok.Data;

/**
 * logVo
 *
 * @author gaoxiaofeng
 * @date 2019-01-05 17:07
 */
@Data
public class LogVo extends BaseEntity<LogVo> {

    private Log log;

    private String username;
}
