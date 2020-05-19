/*
 * Copyright 2013-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.cooingandwooing.auth.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.github.cooingandwooing.auth.api.module.WxSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 微信Service
 *
 * @author gaoxiaofeng
 * @date 2019/07/05 20:33
 */
@Slf4j
@AllArgsConstructor
@Service
public class WxSessionService {

    private final WxMaService wxMaService;

    /**
     * 根据code获取WxSession
     *
     * @param code code
     * @return WxSession
     * @author gaoxiaofeng
     * @date 2019/07/05 20:37:02
     */
    public WxSession getSession(String code) {
        WxSession session = null;
        try {
            WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(code);
            session = new WxSession(result.getOpenid(), result.getSessionKey());
            log.info("获取wx session成功，openId: {}, sessionKey: {}", session.getOpenId(), session.getSessionKey());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return session;
    }

    /**
     * 根据code获取WxSession
     *
     * @param code code
     * @return WxSession
     * @author gaoxiaofeng
     * @date 2019/07/06 14:01:13
     */
    public WxSession code2Session(String code) {
        WxSession session = null;
        try {
            WxMaJscode2SessionResult result = wxMaService.jsCode2SessionInfo(code);
            session = new WxSession(result.getOpenid(), result.getSessionKey());
            log.info("获取wx session成功，openId: {}, sessionKey: {}", session.getOpenId(), session.getSessionKey());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return session;
    }
}
