package com.solverpeng.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *      author: solverpeng
 *      blog  : http://solverpeng.com
 *      time  : 2017/2/22
 *      desc  : 字符串工具类
 * </pre>
 */
public class StringUtil extends StringUtils {

    /**
     * 去掉传入字符串中HTML标签
     *
     * @param html 传入的带HTML标签的字符串
     * @return 去掉HTML标签后的字符串
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        return m.replaceAll("");
    }

    /**
     * 去掉HTML标签,script标签,style标签 的方法
     *
     * @param htmlStr 传入的带HTML标签的字符串
     * @return 去掉HTML标签后的字符串
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(ConstUtil.REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(ConstUtil.REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(ConstUtil.REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        return htmlStr.trim(); // 返回文本字符串
    }

    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param targetString 目标字符串
     * @param length       截取长度
     * @return 缩略后的字符串
     */
    public static String abbr(String targetString, int length) {
        if (StringUtils.isBlank(targetString)) {
            return "";
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            int currentLength = 0;
            for (char c : targetString.toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length) {
                    stringBuilder.append(c);
                } else {
                    stringBuilder.append("...");
                    break;
                }
            }
            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 判断字符串是不是数字
     *
     * @param string 需要判断的字符串
     * @return 判断结果
     */
    public static boolean validateNumber(String string) {
        if (StringUtils.isNotEmpty(string)) {
            for (int n = 0; n < string.length(); n++) {
                int chr = string.charAt(n);
                if (chr < 48 || chr > 57)
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 去除字符串数组重复元素
     *
     * @param stringArr 字符串数组
     * @return 去重之后的结果
     */
    public static List<String> removeRepeat4StringArray(String[] stringArr) {
        List<String> list = new ArrayList<String>();
        for (String aStringArr : stringArr) {
            if (!list.contains(aStringArr)) {
                list.add(aStringArr);
            }
        }
        return list;
    }

    /**
     * 转化为半角字符
     *
     * @param string 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String string) {
        if (isEmpty(string)) return string;
        char[] chars = string.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param string 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String string) {
        if (isEmpty(string)) return string;
        char[] chars = string.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 半角、全角字符判断
     *
     * @param c 字符
     * @return true：半角； false：全角
     */
    public static boolean isDbcCase(char c) {
        // 基本拉丁字母（即键盘上可见的，空格、数字、字母、符号）
        if (c >= 32 && c <= 127) {
            return true;
        }
        // 日文半角片假名和符号
        else if (c >= 65377 && c <= 65439) {
            return true;
        }
        return false;
    }

    /**
     * 字符串长度取得（区分半角、全角）
     *
     * @param string 需要获取长度的字符串
     * @return 字符串长度
     */
    public static int getLength(String string) {
        int len = 0;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (isDbcCase(c)) { // 半角
                len = len + 1;
            } else { // 全角
                len = len + 2;
            }
        }
        return len;
    }

    /**
     * 字符串截取（区分半角、全角）
     *
     * @param str   字符串
     * @param limit 长度
     * @return 截取后的字符串
     */
    public static String left(String str, int limit) {
        if (getLength(str) <= limit) {
            return str;
        }
        char[] chars = str.toCharArray();
        int charLenSum = 0;
        String result = "";
        for (char aChar : chars) {
            int charLen = isDbcCase(aChar) ? 1 : 2;
            if (charLenSum + charLen > limit) {
                return result;
            }
            charLenSum += charLen;
            result += aChar;
            if (charLenSum == limit) {
                return result;
            }
        }
        return result;
    }

}
