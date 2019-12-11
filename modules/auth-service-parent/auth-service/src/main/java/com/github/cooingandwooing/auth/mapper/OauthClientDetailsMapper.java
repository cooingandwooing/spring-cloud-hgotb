package com.github.cooingandwooing.auth.mapper;

import com.github.cooingandwooing.auth.api.module.OauthClientDetails;
import com.github.cooingandwooing.common.core.persistence.CrudMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Oauth2客户端mapper
 *
 * @author gaoxiaofeng
 * @date 2019/3/30 16:39
 */
@Mapper
public interface OauthClientDetailsMapper extends CrudMapper<OauthClientDetails> {
}
