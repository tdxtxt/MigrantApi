package com.soecode.ton.help;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
	public static boolean isEmpty(String text){
		return null == text || "".equals(text);
	}

	public static boolean isInteger(String str) {
		if (TextUtils.isEmpty(str))
			return false;
		Matcher isNum = Pattern.compile("^[+-]?[0-9]+$").matcher(str);  
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	public static int getInteger(String str) {
		if (!isInteger(str))
			return -9999;
		return Integer.parseInt(str);
	}
}
