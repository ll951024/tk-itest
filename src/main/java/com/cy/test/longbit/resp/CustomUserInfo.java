package com.cy.test.longbit.resp;

public class CustomUserInfo {
    private volatile static CustomUserInfo instance = null;
    private String token;
    private String token2;
    private String token3;

    private CustomUserInfo() {

    }


    public static CustomUserInfo getInstance() {
        //先检查实例是否存在，如果不存在才进入下面的同步块
        if (instance == null) {
            //同步块，线程安全的创建实例
            synchronized (CustomUserInfo.class) {
                //再次检查实例是否存在，如果不存在才真的创建实例
                if (instance == null) {
                    instance = new CustomUserInfo();
                }
            }
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken2() {
        return token2;
    }

    public void setToken2(String token2) {
        this.token2 = token2;
    }

}
