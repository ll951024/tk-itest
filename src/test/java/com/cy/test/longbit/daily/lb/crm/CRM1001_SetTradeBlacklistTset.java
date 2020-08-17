package com.cy.test.longbit.daily.lb.crm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.test.base.PathData;
import com.cy.test.group.ProjectGroup;
import com.cy.test.group.ServiceGroup;
import com.cy.test.http.utils.HttpGetAPI;
import com.cy.test.http.utils.HttpPostAPI;
import com.cy.test.longbit.daily.lb.LoginServerApi;
import com.cy.test.longbit.resp.CustomUserInfo;
import com.cy.test.utils.DBUnitUtil;
import com.cy.test.utils.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2020/7/16 15:17
 * <p>
 * Remark: 设置黑名单：/longbit/crm/user/set_trade_blacklist
 */
public class CRM1001_SetTradeBlacklistTset extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = crm1001;
    private String val1;
    private String val2;


    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login2();
    }

    @Test(description = "设置购买黑名单", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void buyBlacklistTest() throws IOException {

            // 创建Post请求
            HttpPostAPI  postAPI = new HttpPostAPI();
            // 组装请求头
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json;charset=UTF-8");
            headers.put("Authorization", CustomUserInfo.getInstance().getToken());
            postAPI.setHeaders(headers);

            // 设置请求uri和路径参数
           String url = crmDev +  path  ;
           String requestBody = "{\"userId\":56,\"type\":1}";

        // 设置请求uri和路径参数
        postAPI.setUrl(url);
        postAPI.setRequestString(requestBody);


            // 执行请求，获取返回结果
            String response = postAPI.sendRequest(null);

            //  System.out.println(response);
            JSONObject obj = JSON.parseObject(response);
            // 断言验证结果
           // Assert.assertNotNull(obj.get("data"));
            Assert.assertNull(obj.get("massage"));
        }

    @Test(description = "设置购买黑名单", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void sellBlacklistTest() throws IOException {
        // 创建Post请求
        HttpPostAPI  postAPI = new HttpPostAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        postAPI.setHeaders(headers);

        // 设置请求uri和路径参数
        String url = crmDev +  path  ;
        String requestBody = "{\"userId\":56,\"type\":2}";

        // 设置请求uri和路径参数
        postAPI.setUrl(url);
        postAPI.setRequestString(requestBody);

        // 执行请求，获取返回结果
        String response = postAPI.sendRequest(null);

        //  System.out.println(response);
        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertNull(obj.get("massage"));
    }

    @Test(description = "设置购买出售黑名单", priority = 2, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void buySellBlacklistTest() throws IOException {
        // 创建Post请求
        HttpPostAPI  postAPI = new HttpPostAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        postAPI.setHeaders(headers);

        // 设置请求uri和路径参数
        String url = crmDev +  path  ;
        String requestBody = "{\"userId\":56,\"type\":3}";

        // 设置请求uri和路径参数
        postAPI.setUrl(url);
        postAPI.setRequestString(requestBody);

        // 执行请求，获取返回结果
        String response = postAPI.sendRequest(null);

        //  System.out.println(response);
        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertNull(obj.get("massage"));
    }

    @Test(description = "取消黑名单限制", priority = 3, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void cancelBlacklistTest() throws IOException {
        // 创建Post请求
        HttpPostAPI  postAPI = new HttpPostAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        postAPI.setHeaders(headers);

        // 设置请求uri和路径参数
        String url = crmDev +  path  ;
        String requestBody = "{\"userId\":56,\"type\":4}";

        // 设置请求uri和路径参数
        postAPI.setUrl(url);
        postAPI.setRequestString(requestBody);

        // 执行请求，获取返回结果
        String response = postAPI.sendRequest(null);

        //  System.out.println(response);
        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertNull(obj.get("massage"));
    }


    }
