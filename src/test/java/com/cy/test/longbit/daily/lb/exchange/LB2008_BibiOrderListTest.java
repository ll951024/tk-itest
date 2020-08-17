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
 * Created by 璐从今夜白。 on 2020/7/9 16:55
 * <p>
 * Remark: 委托单列表:/exchange/orders/list
 */
public class LB2008_BibiOrderListTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;
    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = ex2008;
    private String host = devHost;

    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login();
    }

    @Test(description = "委托单列表", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void orderListTest() throws IOException {
        Reporter.log("用例1：委托单列表", true);
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
        getAPI.getUrlParams().put("StatusList","1");

        // 执行请求，获取返回结果
        String response = getAPI.sendRequest(null);
        System.out.print("获取返回结果" + response);

        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertNotNull(obj.get("data"));

    }
}
