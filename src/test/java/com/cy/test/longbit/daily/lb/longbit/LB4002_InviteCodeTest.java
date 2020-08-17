package com.cy.test.longbit.daily.lb.longbit;

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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2020/7/7 16:27
 * <p>
 * Remark: 根据邀请码获取用户手机号
 */
public class LB4002_InviteCodeTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;
    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = lb4002;
    private String host = devHost;
    private String val;

    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login();
    }

    @Test(description = "根据邀请码获取用户手机号", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void inviteCodeTest() throws IOException {
        Reporter.log("用例1：根据邀请码获取用户手机号", true);
        // 获取存在的邀请码
     /*   List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "\n" +
                "SELECT invite_code from longbit_activity.activity_invite_code WHERE user_id ='56' and rebate = '5'; ");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("invite_code") != null) {
                val = list1.get(0).get("invite_code").toString();
                //获取请求路径和url
                String url = host + path + val;*/
        //获取请求路径和url
        String url = host + path + "v56ox";

            ///创建get请求
                HttpGetAPI getAPI = new HttpGetAPI();
                // 组装请求头
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json;charset=UTF-8");
                headers.put("Authorization", CustomUserInfo.getInstance().getToken());
                getAPI.setHeaders(headers);

                // 设置请求uri和路径参数
                getAPI.setUrl(url);

                //设置请求url和路径参数
                getAPI.setUrl(url);

                // 执行请求，获取返回结果
                String response = getAPI.sendRequest(null);
                System.out.print("获取返回结果" + response);

                JSONObject obj = JSON.parseObject(response);
                // 断言验证结果
                Assert.assertNotNull(obj.get("data"));

            }
        }

