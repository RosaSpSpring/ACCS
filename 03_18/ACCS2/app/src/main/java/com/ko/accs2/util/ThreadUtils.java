package com.ko.accs2.util;

/**
 * Created by weilv on 2018/4/17.
 */

public class ThreadUtils {
    public static String getThreadInfo() {
        return " @[name=" + Thread.currentThread().getName() + ", id=" + Thread.currentThread().getId()
                + "]";
    }
}
