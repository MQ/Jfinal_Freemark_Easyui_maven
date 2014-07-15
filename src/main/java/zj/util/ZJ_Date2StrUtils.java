package zj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ZJ_Date2StrUtils {

	private static String ISO8601_Format_Str = "yyyy-MM-dd'T'HH:mm:ss:SSSZZ";

	private static String DateTime_Format_Str = "yyyy-MM-dd HH:mm:ss";

	// 日期字符串转Date
	public static Date str2date(String dateStr) {
		dateStr = dateStr.replaceAll("-", " ").replaceAll(":", " ").replaceAll("[.]", " ");
		String newTimeStr = "";
		String[] dateStrArray = dateStr.split(" ");
		int[] timeArray = { 1, 1, 1, 0, 0, 0 };
		for (int i = 0; i < dateStrArray.length; i++) {
			if (i < 6) {
				timeArray[i] = Integer.valueOf(dateStrArray[i]);
			}
		}
		newTimeStr = String.format("%s-%s-%s %s:%s:%s", timeArray[0], timeArray[1], timeArray[2], timeArray[3], timeArray[4], timeArray[5]);
		SimpleDateFormat sdf = new SimpleDateFormat(DateTime_Format_Str);
		Date d = null;
		try {
			d = sdf.parse(newTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	// Date转字符串
	public static String date2str(Date date) {
		return new SimpleDateFormat(DateTime_Format_Str).format(date);
	}

	// Date转字符串
	public static String date2ISO8601Str(Date date) {
		return new SimpleDateFormat(ISO8601_Format_Str).format(date);
	}

	public static void main(String[] args) {
		System.out.println(date2ISO8601Str(new Date()));
		System.out.println(str2date("2014-06-04 00:00:00.0"));
	}
}
