//package com.cy.test.longbit.daily.lb.crm;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.cy.test.base.PathData;
//import com.cy.test.group.ProjectGroup;
//import com.cy.test.group.ServiceGroup;
//import com.cy.test.http.utils.HttpGetAPI;
//import com.cy.test.longbit.daily.lb.LoginServerApi;
//import com.cy.test.longbit.resp.CustomUserInfo;
//import com.cy.test.utils.DBUnitUtil;
//import com.cy.test.utils.JsonUtil;
//import org.apache.commons.dbcp.BasicDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import java.io.IOException;
//import java.util.HashMap;
//
///**
// * Description: tk-itest
// * <p>
// * Created by 璐从今夜白。 on 2020/7/30 11:06
// * <p>
// * Remark: gateway公告查询:  /longbit/notice/query
// */
//public class CRM4006_QueryNoticeTest extends PathData {
//    @Autowired
//    JsonUtil jsonUtil;
//
//    @Autowired
//    DBUnitUtil dbunitUtil;
//
//    @Autowired
//    @Qualifier("lbDatasource")
//    BasicDataSource lbDataSource;
//    private String path = crm4006;
//
//    //获取登录接口返回值的auth
//    @BeforeMethod(alwaysRun = true)
//    public void beforeTestMethod() throws IOException {
//        LoginServerApi loginServerApi = new LoginServerApi();
//        loginServerApi.login2();
//    }
//
//    @Test(description = "gateway公告查询", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
//    public void queryNoticeTest() throws IOException {
//        // 创建Get请求
//        HttpGetAPI getAPI = new HttpGetAPI();
//        // 组装请求头
//        HashMap<String, String> headers = new HashMap<String, String>();
//        headers.put("Content-Type", "application/json;charset=UTF-8");
//        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
//        getAPI.setHeaders(headers);
//
//        // 设置请求url和路径参数
//        String url = crmDev + path;
//
//        // 设置请求uri和路径参数
//        getAPI.setUrl(url);
//        getAPI.getUrlParams().put("type","1");
//        getAPI.getUrlParams().put("status","1");
//
//
//        // 执行请求，获取返回结果
//        String response = getAPI.sendRequest(null);
//
//        //  System.out.println(response);
//        JSONObject obj = JSON.parseObject(response);
//        // 断言验证结果
//        Assert.assertNull(obj.get("message"));
//
//    }
//}
