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
 * Created by 璐从今夜白。 on 2020/7/9 14:15
 * <p>
 * Remark: 全量k线:/exchange/data/kline
 */
public class LB2002_BibiDataKlineTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;
    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = ex2002;
    private String host = devHost;

    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login();
    }

    @Test(description = "全量k线", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void dataKlineTest() throws IOException {
        Reporter.log("用例1：全量k线", true);
        //获取请求路径和url
        String url = host + path  ;

        ///创建get请求
        HttpGetAPI getAPI = new HttpGetAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        getAPI.setHeaders(headers);

        // 设置请求url和路径参数
        getAPI.setUrl(url);
        getAPI.getUrlParams().put("symbol","BTC-USDT");
        getAPI.getUrlParams().put("interval","1m,5m,15m,30m,60m,4h,1d,1M,1w");
        getAPI.getUrlParams().put("startTime","1583895158153");
        getAPI.getUrlParams().put("endTime","1594263158153");

        // 执行请求，获取返回结果
        String response = getAPI.sendRequest(null);
        System.out.print("获取返回结果" + response);

        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
       // Assert.assertNotNull(obj.get("data"));
        Assert.assertNull(obj.get("data"),null);

    }

}
