package com.cy.test.longbit.daily.lb.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.test.base.BaseTest;
import com.cy.test.base.PathData;
import com.cy.test.common.MyExtentTestNgFormatter;
import com.cy.test.group.ProjectGroup;
import com.cy.test.group.ServiceGroup;
import com.cy.test.http.utils.HttpGetAPI;
import com.cy.test.http.utils.HttpPostAPI;
import com.cy.test.longbit.daily.lb.LoginServerApi;
import com.cy.test.longbit.resp.CustomUserInfo;
import com.cy.test.longbit.resp.UserInfoResponse;
import com.cy.test.utils.DBUnitUtil;
import com.cy.test.utils.JsonUtil;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2019/10/17 15:08
 * <p>
 * Remark: 可兑换票数：/activity/vote//change/count/{voteActivityId}
 */
@Listeners({MyExtentTestNgFormatter.class})
public class LB1005_ChangeCountTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String val1;


    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login();
    }

    @Test(description = "白名单用户查询可兑换票数", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void whiteUserChangeCountTest() throws IOException {
        Reporter.log("用例1：白名单用户查询可兑换票数", true);
//         获取存在的活动id
        List<Map<String, Object>> list = dbunitUtil.queryData(lbDataSource, "select b.id FROM longbit_activity.lang_resource a,longbit_activity.vote_activity b WHERE a.lang_text = \"LL：测试接口001\" and a.linked_id = b.id");
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.get(0).get("id") != null) {
                val1 = list.get(0).get("id").toString();

            }
        }
        String url = devHost + lb1005 + val1 ;
        // 创建get请求
        HttpGetAPI getApi = new HttpGetAPI();
        // 组装请求头
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        getApi.setHeaders(headers);

        // 设置请求uri和路径参数
        getApi.setUrl(url);

        // 执行请求，获取返回结果
        String response = getApi.sendRequest(null);

        //  System.out.println(response);
        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
      //  Assert.assertEquals(obj.get("message"),null);
        Assert.assertEquals(obj.get("data"),-1);

    }

    @Test(description = "活动id不存在", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityidNoexistTest() throws IOException {
        Reporter.log("用例2:活动id不存在", true);
//         获取存在的活动id
        List<Map<String, Object>> list = dbunitUtil.queryData(lbDataSource, "select b.id FROM longbit_activity.lang_resource a,longbit_activity.vote_activity b WHERE a.lang_text = \"LL：测试接口001\" and a.linked_id = b.id");
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.get(0).get("id") != null) {
                val1 = list.get(0).get("id").toString();

            }
        }
        String url = crmDev + lb1005  + "88888888";
        // 创建get请求
        HttpGetAPI getApi = new HttpGetAPI();
        // 组装请求头
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        getApi.setHeaders(headers);

        // 设置请求uri和路径参数
        getApi.setUrl(url);

        // 执行请求，获取返回结果
        String response = getApi.sendRequest(null);

        //  System.out.println(response);
        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        //  Assert.assertEquals(obj.get("message"),null);
        Assert.assertEquals(obj.get("data"),0);
    }

    @Test(description = "正常用户查询可兑换票数", priority = 2, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void normalUserChangeCountTest() throws IOException {
        String url = "http://dev.longbit.one/longbit/login";
        String requestBody ="{\n" +
                "\t\"mobile\":\"13200000000\",\n" +
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
        UserInfoResponse userInfoDao1 = new Gson().fromJson(response,UserInfoResponse.class);


     //   System.out.print("用户登录返回结果response===========" + response);
        CustomUserInfo.getInstance().setToken(userInfoDao1.getData().getAuth());
        Reporter.log("用例3：正常用户查询可兑换票数", true);

//         获取存在的活动id
        List<Map<String, Object>> list = dbunitUtil.queryData(lbDataSource, "select b.id FROM longbit_activity.lang_resource a,longbit_activity.vote_activity b WHERE a.lang_text = \"LL：测试接口001\" and a.linked_id = b.id");
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.get(0).get("id") != null) {
                val1 = list.get(0).get("id").toString();

            }
        }
        String url1 = crmDev + lb1005 + val1 ;
        // 创建get请求
        HttpGetAPI getApi = new HttpGetAPI();
        // 组装请求头
        HashMap<String,String> headers1= new HashMap<String,String>();
        headers1.put("Content-Type", "application/json;charset=UTF-8");
        headers1.put("Authorization",  userInfoDao1.getData().getAuth());
        getApi.setHeaders(headers1);

        // 设置请求uri和路径参数
        getApi.setUrl(url1);

        // 执行请求，获取返回结果
        String response1 = getApi.sendRequest(null);

        //  System.out.println(response);
        JSONObject obj = JSON.parseObject(response1);
        // 断言验证结果
        //  Assert.assertEquals(obj.get("message"),null);
        Assert.assertEquals(obj.get("data"),20);
    }

    @Test(description = "活动ID为NULL", priority = 3, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityIdNullTest() throws IOException {
        Reporter.log("用例4：活动ID为NULL", true);
        String url = crmDev + lb1005;
        // 创建get请求
        HttpGetAPI getApi = new HttpGetAPI();
        // 组装请求头
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        getApi.setHeaders(headers);

        // 设置请求uri和路径参数
        getApi.setUrl(url);

        // 执行请求，获取返回结果
        String response = getApi.sendRequest(null);

        //  System.out.println(response);
        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
          Assert.assertEquals(obj.get("message"),"No message available");
          Assert.assertNull(obj.get("data"),null);
    }

}
