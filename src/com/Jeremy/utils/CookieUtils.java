package com.Jeremy.utils;

import javax.servlet.http.Cookie;

//找cookie
public class CookieUtils {
    public static Cookie findCookie(Cookie[] allCookie ,String cookieName){
        if (cookieName == null){
            return null;
        }
        if (allCookie != null){
            for (Cookie c : allCookie){
                if (cookieName.equals(c.getName())){
                    return c;
                }
            }
        }
        return null;
    }
}
