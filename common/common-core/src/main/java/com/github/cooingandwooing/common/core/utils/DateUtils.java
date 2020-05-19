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

package com.github.cooingandwooing.common.core.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * 日期工具类.
 *
 * @author gaoxiaofeng
 * @date 2019/4/28 16:03
 */
@Slf4j
public class DateUtils {

	protected DateUtils() {
		throw new UnsupportedOperationException();
	}

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private static final DateTimeFormatter FORMATTER_MILLIS = DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS");

	/**
	 * 日期转string.
	 *
	 * @param date date
	 * @return String
	 */
	public static String localDateToString(LocalDateTime date) {
		return date != null ? date.format(FORMATTER) : "";
	}

	/**
	 * 日期转string.
	 *
	 * @param date date
	 * @return String
	 */
	public static String localDateMillisToString(LocalDateTime date) {
		return date != null ? date.format(FORMATTER_MILLIS) : "";
	}

	/**
	 * LocalDate转Date.
	 *
	 * @param localDate localDate
	 * @return Date
	 */
	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * LocalDateTime转Date.
	 *
	 * @param localDateTime localDateTime
	 * @return Date
	 */
	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Date转LocalDate.
	 *
	 * @param date date
	 * @return LocalDate
	 */
	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * Date转LocalDateTime.
	 *
	 * @param date date
	 * @return LocalDateTime
	 */
	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/**
	 * 两个时间之差.
	 *
	 * @param startDate startDate
	 * @param endDate   endDate
	 * @return 分钟
	 */
	public static Integer getBetweenMinutes(Date startDate, Date endDate) {
		int minutes = 0;
		try {
			if (startDate != null && endDate != null) {
				long ss;
				if (startDate.before(endDate)) {
					ss = endDate.getTime() - startDate.getTime();
				}
				else {
					ss = startDate.getTime() - endDate.getTime();
				}
				minutes = (int) (ss / (60 * 1000));
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return minutes;
	}

	/**
	 * 两个时间只差.
	 *
	 * @param startDate startDate
	 * @param endDate   endDate
	 * @return 秒数
	 */
	public static Integer getBetweenSecond(Date startDate, Date endDate) {
		int seconds = 0;
		try {
			if (startDate != null && endDate != null) {
				long ss;
				if (startDate.before(endDate)) {
					ss = endDate.getTime() - startDate.getTime();
				}
				else {
					ss = startDate.getTime() - endDate.getTime();
				}
				seconds = (int) (ss / (1000));
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return seconds;
	}
}
