package com.github.cooingandwooing.common.core.utils;

import com.github.cooingandwooing.common.core.model.ResponseBean;

/**
 *
 *
 * @author gaoxiaofeng
 * @date 2019-10-08 12:03
 */
public class ResponseUtil {

	/**
	 * 是否成功
	 * @param responseBean responseBean
	 * @return boolean
	 */
	public static boolean isSuccess(ResponseBean<?> responseBean) {
		return responseBean != null && responseBean.getStatus() == ResponseBean.SUCCESS;
	}
}
