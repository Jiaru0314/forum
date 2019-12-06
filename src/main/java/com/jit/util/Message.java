/*
package com.jit.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

*/
/**
 * @program: blog
 * @description:
 * @author: XZQ
 * @create: 2019-10-29 17:28
 **//*

public class Message {

    public static void setMessage(String[] message, RedirectAttributes attributes) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object obj = request.getSession().getAttribute("lang");
        String lang = null;
        if (obj != null) {
            lang = (String) obj;
        }
        if (lang == null || lang.equals("en_US")) {
            attributes.addFlashAttribute("message", message[1]);
        } else {
            attributes.addFlashAttribute("message", message[0]);
        }
    }
}
*/
