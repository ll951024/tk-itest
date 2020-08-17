package com.cy.test.longbit.daily.lb.longbit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.test.base.PathData;
import com.cy.test.common.MyExtentTestNgFormatter;
import com.cy.test.longbit.daily.lb.LoginServerApi;
import com.cy.test.longbit.resp.CustomUserInfo;
import com.cy.test.group.ProjectGroup;
import com.cy.test.group.ServiceGroup;
import com.cy.test.http.utils.HttpGetAPI;
import com.cy.test.utils.DBUnitUtil;
import com.cy.test.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2019/10/10 16:48
 * <p>
 * Remark:查询用户质押信息接口：/exchange/pledge/detail
 */
@Listeners({MyExtentTestNgFormatter.class})
public class LB1002_PledgeDetailTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login();
    }

    @Test(description = "合约ID不存在", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void contractNoNoexistTest()throws IOException{
        Reporter.log("用例1：输入错误的合约ID", true);
//         请求路径和uri
        String url = crmDev + "/exchange/pledge/detail";
        // 创建get请求
        HttpGetAPI getApi = new HttpGetAPI();
        // 组装请求头
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        getApi.setHeaders(headers);

        // 设置请求uri和路径参数
        getApi.setUrl(url);
        getApi.getUrlParams().put("contractNo","1");


        // 执行请求，获取返回结果
        String response = getApi.sendRequest(null);

        //  System.out.println(response);
        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertEquals(obj.get("message"),"error.pledge_info.not_exists");
        Assert.assertNull(obj.get("data"));

    }

    @Test(description = "合约ID为NULL", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void contractNoNullTest()throws IOException{
        Reporter.log("用例2：合约ID为空查询", true);
//         请求路径和uri
        String url = crmDev + "/exchange/pledge/detail";
        // 创建get请求
        HttpGetAPI getApi = new HttpGetAPI();
        // 组装请求头
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        getApi.setHeaders(headers);

        // 设置请求uri和路径参数
        getApi.setUrl(url);
        getApi.getUrlParams().put("contractNo","");


        // 执行请求，获取返回结果
        String response = getApi.sendRequest(null);

        //  System.out.println(response);
        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertEquals(obj.get("message"),"error.pledge_info.not_exists");
        Assert.assertNull(obj.get("data"));

    }
}
