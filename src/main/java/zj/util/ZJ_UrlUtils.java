package zj.util;

public class ZJ_UrlUtils {
	public static String changeURL(String s) {
		if (null == s || "".equals(s)) {
			return s;
		} else {
			return s.replaceAll("http://[^/]+/jhyl/", ZJ_ConfigUtils.getProperty("uploadURL"));
		}
	}
}
