package net.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author sifuma@163.com
 *
 */
public class TimeUtil {

	public static String formatTime(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	public static String formatTime(long time, String format) {
		return new SimpleDateFormat(format).format(new Timestamp(time));
	}
}
