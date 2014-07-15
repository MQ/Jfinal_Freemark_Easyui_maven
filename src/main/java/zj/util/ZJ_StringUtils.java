package zj.util;


public class ZJ_StringUtils {

	public static boolean isNotEmpty(Object obj) {
		if (null == obj || "".equals(obj.toString().trim())) {
			return false;
		} else {
			return true;
		}
	}

	public static String removeLastChar(String sourceStr, String removeStr) {
		if (null != sourceStr && null != removeStr && sourceStr.endsWith(removeStr)) {
			sourceStr = sourceStr.substring(0, sourceStr.length() - removeStr.length());
		}
		return sourceStr;
	}

	public static String removeFirstChar(String sourceStr, String removeStr) {
		if (null != sourceStr && null != removeStr && sourceStr.startsWith(removeStr)) {
			sourceStr = sourceStr.replaceFirst(removeStr, "");
		}
		return sourceStr;
	}

	/**
	 * 把类似 1,2,3转为 '1','2','3'
	 * 
	 * @param str
	 * @return
	 */
	public static String changeStr(String str) {
		if (null == str || "".equals(str.trim()) || str.startsWith("'")) {
			return str;
		} else {
			String str2 = "";
			String[] temp = str.split(",");
			for (String string : temp) {
				if (!"".equals(string)) {
					str2 += ",'" + string + "'";
				}
			}
			if (str2.startsWith(",")) {
				str2 = str2.substring(1, str2.length());
			}
			return str2;
		}
	}

	public static void main(String[] args) {
		String aa = ",1,2,3,4,";
		String bb = "1,2";
		System.out.println(removeLastChar(aa, ","));
		System.out.println(removeFirstChar(aa, ","));
		System.out.println(changeStr(bb));
	}
}
