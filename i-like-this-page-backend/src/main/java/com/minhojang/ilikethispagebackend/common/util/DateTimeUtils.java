package com.minhojang.ilikethispagebackend.common.util;

import com.minhojang.ilikethispagebackend.exception.InvalidArgumentException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

	public static Timestamp timestampOf(LocalDateTime time) {
		return time == null ? null : Timestamp.valueOf(time);
	}

	public static LocalDateTime dateTimeOf(Timestamp timestamp) {
		return timestamp == null ? null : timestamp.toLocalDateTime();
	}

	public static LocalDate localDateOf(String yyyyMMdd) {
		return LocalDate.parse(yyyyMMdd, DateTimeFormatter.BASIC_ISO_DATE);
	}

	public static long millsOf(String yyyyMMddHHmmss) {
		try {
			return new SimpleDateFormat("yyyyMMddHHmmss").parse(yyyyMMddHHmmss).getTime();
		} catch (ParseException e) {
			throw new InvalidArgumentException(e.getMessage(), e);
		}
	}
}