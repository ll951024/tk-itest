package com.cy.test.longbit.daily.lb.exchange;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.test.base.PathData;
import com.cy.test.group.ProjectGroup;
import com.cy.test.group.ServiceGroup;
import com.cy.test.http.utils.HttpPostAPI;
import com.cy.test.longbit.daily.lb.LoginServerApi;
import com.cy.test.longbit.resp.CustomUserInfo;
import com.cy.test.utils.DBUnitUtil;
import com.cy.test.utils.JsonUtil;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2020/7/9 16:18
 * <p>
 * Remark: 委托下单：/exchange/orders/order
 */
public class LB2007_BibiOrderorderTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;
    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = ex2007;
    private String host = devHost;


    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login();
    }

    @Test(description = "限价委托买单", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void orderBuyUintpriceTest() throws IOException {
        Reporter.log("用例1：限价委托买单", true);
        //获取请求路径和url
        String url = host +  path  ;
        String requestBody = "{\"symbol\":\"LBP-USDT\",\"orderType\":\"limit\",\"side\":\"buy\",\"unitPrice\":\"45.00000\",\"amount\":\"10\"}";

        //创建post请求
        HttpPostAPI postApi = new HttpPostAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        postApi.setHeaders(headers);


        // 设置请求uri和路径参数
        postApi.setUrl(url);
        postApi.setRequestString(requestBody);

        // 执行请求，获取返回结果
        String response = postApi.sendRequest(null);

        // System.out.print("获取返回结果" + response);

        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertEquals(obj.get("message"), null);
        Assert.assertEquals(obj.get("data"),null);

    }


    @Test(description = "限价委托卖单", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void orderSellUintpriceTest() throws IOException {
        Reporter.log("用例2：限价委托卖单", true);
        //获取请求路径和url
        String url = host +  path  ;
        String requestBody = "{\"symbol\":\"LBP-USDT\",\"orderType\":\"limit\",\"side\":\"sell\",\"unitPrice\":\"45.00000\",\"amount\":\"10\"}";

        //创建post请求
        HttpPostAPI postApi = new HttpPostAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        postApi.setHeaders(headers);


        // 设置请求uri和路径参数
        postApi.setUrl(url);
        postApi.setRequestString(requestBody);

        // 执行请求，获取返回结果
        String response = postApi.sendRequest(null);

        // System.out.print("获取返回结果" + response);

        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertEquals(obj.get("message"), null);
        Assert.assertEquals(obj.get("data"),null);

    }

    @Test(description = "市价委托买单", priority = 2, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void orderBuyMarketTest() throws IOException {
        Reporter.log("用例3：市价委托买单", true);
        //获取请求路径和url
        String url = host + path;
        String requestBody = "{\"symbol\":\"LBP-USDT\",\"orderType\":\"market\",\"side\":\"buy\",\"unitPrice\":\"\",\"amount\":\"10\"}";

        //创建post请求
        HttpPostAPI postApi = new HttpPostAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        postApi.setHeaders(headers);


        // 设置请求uri和路径参数
        postApi.setUrl(url);
        postApi.setRequestString(requestBody);

        // 执行请求，获取返回结果
        String response = postApi.sendRequest(null);

        // System.out.print("获取返回结果" + response);

        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertEquals(obj.get("message"), null);
        Assert.assertEquals(obj.get("data"), null);

    }

    @Test(description = "市价委托卖单", priority = 3, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void orderSellMarketTest() throws IOException {
        Reporter.log("用例4：市价委托卖单", true);
        //获取请求路径和url
        String url = host +  path  ;
        String requestBody = "{\"symbol\":\"LBP-USDT\",\"orderType\":\"market\",\"side\":\"sell\",\"unitPrice\":\"\",\"amount\":\"10\"}";

        //创建post请求
        HttpPostAPI postApi = new HttpPostAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        postApi.setHeaders(headers);


        // 设置请求uri和路径参数
        postApi.setUrl(url);
        postApi.setRequestString(requestBody);

        // 执行请求，获取返回结果
        String response = postApi.sendRequest(null);

        // System.out.print("获取返回结果" + response);

        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertEquals(obj.get("message"), null);
        Assert.assertEquals(obj.get("data"),null);

    }
}
