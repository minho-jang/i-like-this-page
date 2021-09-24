package com.minhojang.ilikethispagebackend.common.util;

import java.sql.Timestamp;
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

}