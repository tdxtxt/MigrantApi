package com.soecode.ton.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
	public static boolean isEmpty(String text){
		return null == text || "".equals(text);
	}
    public static String join(CharSequence delimiter, String[] tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token: tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }
    public static List<String> reverseJoin(String delimiter, String content){
    	List<String> result = new ArrayList<>();
    	if(TextUtils.isEmpty(content)) return result;
    	String[] sts = content.split(delimiter);
    	result = Arrays.asList(sts);
    	return result;
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
