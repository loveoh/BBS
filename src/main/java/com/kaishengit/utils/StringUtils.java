package com.kaishengit.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by loveoh on 2016/12/15.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    private static Logger logger = LoggerFactory.getLogger(StringUtils.class);

    /**
     * 解决IOS8859-1字符串转换成UTF-8字符串
     * @param str
     * @return
     */
    public static String iosChangeToUtf8(String str){

        try {
            return new String(str.getBytes("iso8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("字符串{}转错误",str);
            throw new RuntimeException("字符串"+str+"转码错误",e);
        }
    }
}
