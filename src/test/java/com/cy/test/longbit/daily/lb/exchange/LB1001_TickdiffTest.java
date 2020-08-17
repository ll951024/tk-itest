package com.cy.test.longbit.daily.lb.exchange;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.test.base.PathData;
import com.cy.test.group.ProjectGroup;
import com.cy.test.group.ServiceGroup;
import com.cy.test.http.utils.HttpGetAPI;
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
 * Created by 璐从今夜白。 on 2020/7/8 17:24
 * <p>
 * Remark: 币币交易页面全币种行情（首页横幅行情）:/exchange/tick/tick_diff
 */
public class LB1001_TickdiffTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;
    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = ex1001;
    private String host = devHost;

    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login();
    }

    @Test(description = "币币交易页面全币种行情（首页横幅行情）前10条数据", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void tickDiffLimitTest() throws IOException {
        Reporter.log("用例1：币币交易页面全币种行情（首页横幅行情）", true);
        //获取请求路径和url
        String url = host + path ;

        ///创建get请求
        HttpGetAPI getAPI = new HttpGetAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        getAPI.setHeaders(headers);

        // 设置请求url和路径参数
        getAPI.setUrl(url);
        getAPI.getUrlParams().put("limit","10");

        // 执行请求，获取返回结果
        String response = getAPI.sendRequest(null);
        System.out.print("获取返回结果" + response);

        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertNotNull(obj.get("data"));

    }

    @Test(description = "币币交易页面全币种行情（首页横幅行情）前10条数据", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void tickDiffAllTest() throws IOException {
        Reporter.log("用例2：币币交易页面全币种行情（首页横幅行情）", true);
        //获取请求路径和url
        String url = host + path ;

        //创建post请求
        HttpGetAPI getAPI = new HttpGetAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        getAPI.setHeaders(headers);

        // 设置请求url和路径参数
        getAPI.setUrl(url);

        // 执行请求，获取返回结果
        String response = getAPI.sendRequest(null);
        System.out.print("获取返回结果" + response);

        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertNotNull(obj.get("data"));

    }
}
