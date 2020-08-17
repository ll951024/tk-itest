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
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2020/7/9 16:09
 * <p>
 * Remark: 删除自选：/exchange/data/symbols/star/delete
 */
public class LB2006_BibiStarDeleteTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;
    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = ex2006;
    private String host = devHost;


    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login();
    }

    @Test(description = "删除自选成功", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void deleteAddTest() throws IOException {
        Reporter.log("用例1：删除自选成功", true);
        //获取请求路径和url
        String url = host +  path  ;
        String requestBody = "{\"symbol\":NEO-USDT}";

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
        //     Assert.assertEquals(obj.get("message"), null);
        //    Assert.assertNull(obj.get("data"));
        System.out.println(response);
    }
}
