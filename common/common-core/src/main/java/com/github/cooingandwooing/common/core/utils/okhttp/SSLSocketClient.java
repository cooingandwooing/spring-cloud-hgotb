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

package com.github.cooingandwooing.common.core.utils.okhttp;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * SSLSocketFactory for OkHttpUtil 45:46
 *
 * @author gaoxiaofeng
 * @date 2018-12-04 16:53
 */
public class SSLSocketClient {

    /**
     * 获取这个SSLSocketFactory
     *
     * @return SSLSocketFactory
     * @author tangyi
     * @date 2018/12/4 16:54
     */
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取TrustManager
     *
     * @return TrustManager
     * @author gaoxiaofeng
     * @date 2018/12/4 16:55
     */
    private static TrustManager[] getTrustManager() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
    }

    /**
     * 获取HostnameVerifier
     *
     * @return HostnameVerifier
     * @author gaoxiaofeng
     * @date 2018/12/4 16:56
     */
    public static HostnameVerifier getHostnameVerifier() {
        return (requestedHost, remoteServerSession) -> requestedHost.equalsIgnoreCase(remoteServerSession.getPeerHost());
    }
}
