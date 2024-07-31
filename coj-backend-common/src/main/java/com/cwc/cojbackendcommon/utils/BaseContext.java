package com.cwc.cojbackendcommon.utils;


public class BaseContext {

    public static ThreadLocal<UserContext> threadLocal = new ThreadLocal<>();

    public static void setCurrentUser(UserContext userContext) {
        threadLocal.set(userContext);
    }

    public static UserContext getCurrentUser() {
        return threadLocal.get();
    }

    public static void removeCurrentUser() {
        threadLocal.remove();
    }

}
