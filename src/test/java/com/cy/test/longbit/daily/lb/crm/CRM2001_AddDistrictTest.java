package com.cy.test.longbit.daily.lb.crm;

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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2020/7/16 16:58
 * <p>
 * Remark: 新增交易区:/longbit/crm/exchange/district/add_district
 */
public class CRM2001_AddDistrictTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = crm2001;

    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login2();
    }

    @Test(description = "新增交易区", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void addDistrictTest() throws IOException {

        // 创建Post请求
        HttpPostAPI postAPI = new HttpPostAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        postAPI.setHeaders(headers);

        // 设置请求uri和路径参数
        String url = crmDev + path;
        String requestBody = "{\"showHome\":2,\"showMarket\":2,\"resourceMap\":{\"zh-CN\":{\"district\":\"测试\",\"description\":\"测试说明中文\"},\"en-US\":{\"district\":\"test\",\"description\":\"测试说明英文\"},\"ko-KR\":{\"district\":\"test\",\"description\":\"测试说明韩文\"}}}";

        // 设置请求uri和路径参数
        postAPI.setUrl(url);
        postAPI.setRequestString(requestBody);


        // 执行请求，获取返回结果
        String response = postAPI.sendRequest(null);

        //  System.out.println(response);
        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
      //  Assert.assertNotNull(obj.get("data"));
        Assert.assertNull(obj.get("data"));
      //  Assert.assertNull(obj.get("massage"), "交易区已存在");
    }

    @Test(description = "新增交易区名称重复", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void addExistDistrictTest() throws IOException {
        // 创建Post请求
        HttpPostAPI postAPI = new HttpPostAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        postAPI.setHeaders(headers);

        // 设置请求uri和路径参数
        String url = crmDev + path;
        String requestBody = "{\"showHome\":2,\"showMarket\":2,\"resourceMap\":{\"zh-CN\":{\"district\":\"测试\",\"description\":\"测试说明中文\"},\"en-US\":{\"district\":\"test\",\"description\":\"测试说明英文\"},\"ko-KR\":{\"district\":\"test\",\"description\":\"测试说明韩文\"}}}";

        // 设置请求uri和路径参数
        postAPI.setUrl(url);
        postAPI.setRequestString(requestBody);


        // 执行请求，获取返回结果
        String response = postAPI.sendRequest(null);

        //  System.out.println(response);
        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        // Assert.assertNotNull(obj.get("data"));
        Assert.assertNull(obj.get("massage"), "交易区已存在");
    }

}

