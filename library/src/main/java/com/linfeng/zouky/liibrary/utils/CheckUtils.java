package com.linfeng.zouky.liibrary.utils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author :Zouky
 *         Time :2017/10/24
 *         Description:
 */
public class CheckUtils {
    /**
     * @Create by Zouky
     * @Time :2017/10/24
     * @Params :
     * @Description :  检验对象是否为null      如果是list  或者map size=0也算为null
     */
    public static <T> boolean isBank(T t) {
        if (t == null) {
            return true;
        }
        if (t instanceof Collection) {
            if (((Collection) t).size() == 0) {
                return true;
            }
        } else if (t instanceof Map) {
            if (((Map) t).size() == 0) {
                return true;
            }
        } else if (t instanceof String) {
            if (StrUtil.isBlank(t.toString())) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean isNotBank(T t) {
        return !isBank(t);
    }

    /**
     * 验证密码
     *
     * @param str 要验证的字符串
     * @return
     */
    public static boolean pawMatcher(String str) {
        String regEx = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{8,20}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
