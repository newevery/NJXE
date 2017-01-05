package com.xuenen.njxe.utils;

import com.xuenen.njxe.common.BaseApplication;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(BaseApplication.getIntstance());

        @Override
        public void  saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    System.out.println(url + " saveFromResponse " + item);
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            System.out.println("loadForRequest"+cookies.toString());
            return cookies;
        }
    }