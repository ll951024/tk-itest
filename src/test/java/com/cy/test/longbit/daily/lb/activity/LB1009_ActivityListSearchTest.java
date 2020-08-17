package com.cy.test.longbit.daily.lb.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.test.base.ExcelData;
import com.cy.test.base.PathData;
import com.cy.test.common.MyExtentTestNgFormatter;
import com.cy.test.group.ProjectGroup;
import com.cy.test.group.ServiceGroup;
import com.cy.test.http.utils.HttpGetAPI;
import com.cy.test.http.utils.HttpPostAPI;
import com.cy.test.longbit.daily.lb.LoginServerApi;
import com.cy.test.longbit.resp.CustomUserInfo;
import com.cy.test.utils.DBUnitUtil;
import com.cy.test.utils.JsonUtil;
import jxl.read.biff.BiffException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2019/10/29 15:14
 * <p>
 * Remark: 分页查询：Get: /activity/list
 */
@Listeners({MyExtentTestNgFormatter.class})
public class LB1009_ActivityListSearchTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = lb1009;

    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login2();
    }

    @Test(description = "未输入查询条件", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void searchNullTest() throws IOException {
        Reporter.log("用例1：未输入查询条件", true);
        String url = crmDev + path ;
        String requestBody = "{\n" +
                "\"pageSize\":\"10\",\n" +
                "\"pageNo\":\"1\"\n" +
                "}";
        // 创建post请求
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
        Assert.assertNull(obj.get("message"));
    }

    @Test(description = "根据Title进行查询", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void searchTitleTest() throws IOException {
        Reporter.log("用例2：根据Title进行查询", true);
        String url = crmDev + path ;
        String requestBody = "{\n" +
                "\"title\":\"LL\",\n" +
                "\"pageSize\":\"10\",\n" +
                "\"pageNo\":\"1\"\n" +
                "}";
        // 创建post请求
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
        Assert.assertNull(obj.get("message"));
    }

    @Test(description = "根据包含项目名进行查询", priority = 2, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void searchProjectNameTest() throws IOException {
        Reporter.log("用例3：根据包含项目名进行查询", true);
        String url = crmDev + path;
        String requestBody = "{\n" +
                "\"projectName\":\"001\",\n" +
                "\"pageSize\":\"10\",\n" +
                "\"pageNo\":\"1\"\n" +
                "}";
        // 创建post请求
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
        Assert.assertNull(obj.get("message"));
    }


    @Test(description = "根据时间段进行查询", priority = 2, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void searchTimeTest() throws IOException {
        Reporter.log("用例4：根据时间段进行查询", true);
        String url = crmDev+ path ;
        String requestBody = "{\n" +
                "\"startTime\":\"2019-09-20 16:23:00\",\n" +
                "\"endTime\":\"2019-09-20 18:00:00\",\n" +
                "\"pageSize\":\"10\",\n" +
                "\"pageNo\":\"1\"\n" +
                "}";
        // 创建post请求
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
        Assert.assertNull(obj.get("message"));
    }

    @Test(description = "查询第二页数据", priority = 4, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void searchPageTest() throws IOException {
        Reporter.log("用例5：查询第二页数据", true);
        String url = crmDev+ path ;
        String requestBody = "{\n" +
                "\"pageSize\":\"10\",\n" +
                "\"pageNo\":\"2\"\n" +
                "}";
        // 创建post请求
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
        Assert.assertNull(obj.get("message"));
    }




}
