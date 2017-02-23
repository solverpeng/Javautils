package com.solverpeng.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <pre>
 *      author: solverpeng
 *      blog  : http://solverpeng.com
 *      time  : 2017/2/22
 *      desc  : 编码解码工具类
 * </pre>
 */
public abstract class EncodeUtil {
    /**
     * Hex编码.
     */
    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    /**
     * Hex解码.
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * Base64编码.
     */
    public static String encodeBase64(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
     */
    public static String encodeUrlSafeBase64(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    /**
     * Base64解码.
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input);
    }


    /**
     * Base62编码。
     */
    public static String encodeBase62(byte[] input) {
        char[] chars = new char[input.length];
        for (int i = 0; i < input.length; i++) {
            chars[i] = ConstUtil.BASE62[((input[i] & 0xFF) % ConstUtil.BASE62.length)];
        }
        return new String(chars);
    }

    /**
     * Html 转码.
     */
    public static String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml4(html);
    }

    /**
     * Html 解码.
     */
    public static String unescapeHtml(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml4(htmlEscaped);
    }

    /**
     * Xml 转码.
     */
    public static String escapeXml(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    /**
     * Xml 解码.
     */
    public static String unescapeXml(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    /**
     * URL 编码, Encode默认为UTF-8
     * <p>若想自己指定字符集,可以使用{@link #urlEncode(String input, String charset)}方法</p>
     *
     * @param input 要编码的字符串。
     * @return 编码为UTF-8的字符串
     */
    public static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, ConstUtil.DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * URL 编码，可以指定编码字符集
     * <p>若系统找不到指定编码字符集，则直接将 input 原样返回</p>
     * @param input     要编码的字符串
     * @param charset   字符集
     * @return 编码为字符集的字符串
     */
    public static String urlEncode(String input, String charset) {
        try {
            return URLEncoder.encode(input, charset);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     * <p>若想自己指定字符集，可以使用{@link #urlDecode(String, String)}方法</p>
     *
     * @param input 要解码的字符串
     * @return 解码为UTF-8的字符串
     */
    public static String urlDecode(String input) {

        try {
            return URLDecoder.decode(input, ConstUtil.DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

    /**
     * URL解码，可以指定解码集
     * <p>若系统不支持指定的解码字符集，则将input原样返回</p>
     *
     * @param input     要解码的字符串
     * @param charset   解码字符集
     * @return 解码为字符集的字符串
     */
    public static String urlDecode(String input, String charset) {

        try {
            return URLDecoder.decode(input, ConstUtil.DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

}
