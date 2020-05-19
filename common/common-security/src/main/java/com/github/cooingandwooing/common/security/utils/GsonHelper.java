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

package com.github.cooingandwooing.common.security.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 基于Gson的json工具类
 *
 * @author gaoxiaofeng
 * @date 2017-11-23 18:03
 */
public class GsonHelper {

    /**
     * Gson对象
     */
    private static final Gson gson = new Gson();

    /**
     * 单例
     */
    private volatile static GsonHelper instance;

    /**
     * 获取单例
     *
     * @author gaoxiaofeng
     * @date 2017/11/23 18:10
     */
    public static GsonHelper getInstance() {
        if (instance == null) {
            synchronized (GsonHelper.class) {
                if (instance == null) {
                    instance = new GsonHelper();
                }
            }
        }
        return instance;
    }


    /**
     * 将json转为对象
     *
     * @param json  json
     * @param clazz clazz
     * @return T
     * @author tangyi
     * @date 2017/11/23 18:09
     */
    public <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    /**
     * 将json转为对象
     *
     * @param json json
     * @param type type
     * @return T
     * @author tangyi
     * @date 2017/11/28 15:41
     */
    public <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    /**
     * 将对象转为json
     *
     * @param src
     * @return String
     * @author tangyi
     * @date 2017/11/23 18:09
     */
    public String toJson(Object src) {
        return gson.toJson(src);
    }
}
