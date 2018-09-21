package com.liugeng.myspring.util;

public class StringUtils {
    public static boolean hasLength(String str){
        return hasLength((CharSequence) str);
    }
    public static boolean hasLength(CharSequence str){
        return (str != null && str.length() > 0);
    }
    public static boolean hasText(String str){
        return hasText((CharSequence) str);
    }
    public static boolean hasText(CharSequence str){
        if(!hasLength(str)){
            return false;
        }
        for(int i = 0; i < str.length(); i++){
            if(!Character.isWhitespace(str.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
