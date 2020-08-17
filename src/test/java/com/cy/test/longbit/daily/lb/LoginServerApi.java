package com.cy.test.longbit.daily.lb;

import com.cy.test.longbit.resp.CustomUserInfo;
import com.cy.test.http.utils.HttpPostAPI;
import com.cy.test.longbit.resp.UserInfoResponse;
import com.google.gson.Gson;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class LoginServerApi{


    public void login()throws IOException {
        String url = "http://dev.longbit.one/longbit/login";
        String requestBody ="{\n" +
                "\t\"mobile\":\"17681827772\",\n" +
                "\t\"code\":\"1234\",\n" +
                "\t\"nationCode\":\"86\"\n" +
                "}";
        // 创建post请求
        HttpPostAPI postApi = new HttpPostAPI();
        // 组装请求头
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        postApi.setHeaders(headers);
        // 设置请求uri和路径参数
        postApi.setUrl(url);
        //打印请求参数
        postApi.setRequestString(requestBody);
        // 执行请求，获取返回结果
        String response = postApi.sendRequest(null);
        UserInfoResponse userInfoDao = new Gson().fromJson(response,UserInfoResponse.class);

        System.out.print("用户登录返回结果response===========" + response);
        CustomUserInfo.getInstance().setToken(userInfoDao.getData().getAuth());

    }

    public void login2()throws IOException {
//        String url = "https://bsqklserver.shuliantt.com/shuliantt/modifiAppShopSwitch?shopCode=680&version=1.4.0&off=true";
        String url = "http://dev.longbit.one:81/longbit/crm/system/login";
        String requestBody ="{\"userName\":\"lilu\",\"password\":\"12345\"}";
        // 创建post请求
        HttpPostAPI postApi = new HttpPostAPI();
        // 组装请求头
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        postApi.setHeaders(headers);
        // 设置请求uri和路径参数
        postApi.setUrl(url);
        //打印请求参数
        postApi.setRequestString(requestBody);
        // 执行请求，获取返回结果
        String response = postApi.sendRequest(null);

        JSONObject jsonObject = JSONObject.fromObject(response);

        System.out.print("用户登录返回结果response===========" + response);
        CustomUserInfo.getInstance().setToken(jsonObject.optString("data"));
        System.out.print("用户登录返回结果" + jsonObject.optString("data"));
    }

}
