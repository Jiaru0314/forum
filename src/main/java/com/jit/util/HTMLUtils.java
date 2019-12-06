package com.jit.util;

/**
 * @program: forum
 * @description: HTML工具类
 * @author: XZQ
 * @create: 2019-11-24 13:19
 **/
public class HTMLUtils {

    public static String getText(String content) {
        String txtcontent = content.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
        return txtcontent;
    }
}
