package it.search.pibisi.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateUtils {

	public static final String PATTERN = "dd/MM/yyyy";
	public static final String PATTERN_CON_ORARIO = "dd/MM/yyyy HH:mm";

	public static String getNow() {
		return new Timestamp(new Date().getTime()).toString();
	}

	public static Timestamp getNow_DDMMYYYYHHMM() {
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_CON_ORARIO);
		String now = formatter.format(new Date());

		try {
			return new Timestamp(formatter.parse(now).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static Timestamp getNow_DDMMYYYY() {
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN);
		String now = formatter.format(new Date());

		try {
			return new Timestamp(formatter.parse(now).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static String formatTimestampToStringWithOrario(Timestamp stringAsTimestamp) {
		if (stringAsTimestamp == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN_CON_ORARIO);
		return formatter.format(new Date(stringAsTimestamp.getTime()));
	}

	public static String formatTimestampToString(Timestamp stringAsTimestamp) {
		if (stringAsTimestamp == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN);
		return formatter.format(new Date(stringAsTimestamp.getTime()));
	}

	public static Timestamp formatStringToTimestamp(String timestampAsString) {
		if (StringUtils.isEmpty(timestampAsString)) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(PATTERN);
		Date dt;
		Timestamp timestamp = null;
		try {
			dt = formatter.parse(timestampAsString);
			timestamp = new Timestamp(dt.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return timestamp;
	}

}
