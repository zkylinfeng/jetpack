package com.linfeng.zouky.liibrary.utils;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by :Zouky
 * Time :2017/10/17
 * Description:
 */

public class StrUtil {
    public static final int INDEX_NOT_FOUND = -1;

    public static String trimAllWhitespace(String str)
    {
        return str.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "").toString();
    }

    public static boolean isBlank(String str)
    {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        //int strLen;
        if ("null".equalsIgnoreCase(str)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str)
    {
        return !isBlank(str);
    }

    public static String stripEnd(String str, String stripChars)
    {
        int end;
        if ((str == null) || ((end = str.length()) == 0)) {
            return str;
        }
        // int end;
        if (stripChars == null) {
            while ((end != 0) && (Character.isWhitespace(str.charAt(end - 1)))) {
                end--;
            }
        }
        if (stripChars.length() == 0) {
            return str;
        }
        while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
            end--;
        }
        return str.substring(0, end);
    }

    public static String null2Zero(String str)
    {
        if ((str == null) || (str.equals(""))) {
            return "0";
        }
        return str;
    }

    public static boolean isNumeric(String str)
    {
        if (isBlank(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String rmStrInNum(String str)
    {
        String str1 = "";
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                str1 = str1 + str.charAt(i);
            }
        }
        return str1;
    }

    public static boolean isMobileNO(String mobiles)
    {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static int getStrLength(String str, String code)
    {
        int length = 0;
        try
        {
            byte[] bt = str.getBytes(code);
            length = bt.length;
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return length;
    }

    public static int getStrLength(String str)
    {
        return getStrLength(str, "GBK");
    }

    public static int str2Int(String str)
    {
        int a = 0;
        if (isNumeric(str)) {
            a = Integer.valueOf(str).intValue();
        }
        return a;
    }

    public static long str2Long(String str)
    {
        long a = 0L;
        if (isNumeric(str)) {
            a = Long.valueOf(str).longValue();
        }
        return a;
    }

    public static float str2Float(String str)
    {
        float a = 0.0F;
        if (isNumeric(str)) {
            a = (float)Long.valueOf(str).longValue();
        }
        return a;
    }

    public static double str2Double(String str)
    {
        double a = 0.0D;
        if (isNumeric(str)) {
            a = Long.valueOf(str).longValue();
        }
        return a;
    }

    public static void main(String[] args)
    {
        System.out.println("==fhb===" + isNumeric("5"));
        System.out.println("==fhb2===" + stripEnd("wqfgdhgkj", "h"));
        System.out.println("==fhb3===" + str2Int("null"));
        System.out.println("==fhb4===" + str2Int(""));
        System.out.println("==fhb5===" + str2Float(""));
        System.out.println("==fhb6===" + str2Long(""));

        System.out.println("==fhb7===" + Integer.parseInt(""));
    }

    /**
     * 首字母大写
     *
     * @param string
     * @return
     */
    public static String toUpperCase4Index(String string) {
        if (isBlank(string))return "";
        char[] methodName = string.toCharArray();
        methodName[0] = toUpperCase(methodName[0]);
        return String.valueOf(methodName);
    }

    /**
     * 字符转成大写
     *
     * @param chars
     * @return
     */
    public static char toUpperCase(char chars) {
        if (97 <= chars && chars <= 122) {
            chars ^= 32;
        }
        return chars;
    }
}

