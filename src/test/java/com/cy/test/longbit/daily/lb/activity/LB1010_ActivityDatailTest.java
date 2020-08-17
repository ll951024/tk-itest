package com.cy.test.longbit.daily.lb.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.test.base.PathData;
import com.cy.test.group.ProjectGroup;
import com.cy.test.group.ServiceGroup;
import com.cy.test.http.utils.HttpGetAPI;
import com.cy.test.http.utils.HttpPostAPI;
import com.cy.test.longbit.daily.lb.LoginServerApi;
import com.cy.test.longbit.resp.CustomUserInfo;
import com.cy.test.utils.DBUnitUtil;
import com.cy.test.utils.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2019/10/29 20:26
 * <p>
 * Remark: 获取明细信息：/longbit/crm/activity
 */
public class LB1010_ActivityDatailTest  extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = lb1010;
    private String val1;
    private String val2;


    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login2();
    }

    @Test(description = "活动id存在", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityExistTest() throws IOException {
        //获取存在的活动id
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "select b.id FROM lang_resource a,vote_activity b WHERE a.lang_text = \"LL：测试接口001\" and a.linked_id = b.id");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("id") != null) {
                val1 = list1.get(0).get("id").toString();
            }
            String url = crmDev + path + val1;

            // 创建Get请求
            HttpGetAPI getAPI = new HttpGetAPI();
            // 组装请求头
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json;charset=UTF-8");
            headers.put("Authorization", CustomUserInfo.getInstance().getToken());
            getAPI.setHeaders(headers);

            // 设置请求uri和路径参数
            getAPI.setUrl(url);

            // 执行请求，获取返回结果
            String response = getAPI.sendRequest(null);

            //  System.out.println(response);
            JSONObject obj = JSON.parseObject(response);
            // 断言验证结果
            Assert.assertNotNull(obj.get("data"));
         //   Assert.assertNull(obj.get("massage"));
        }
    }

    @Test(description = "活动id不存在", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityNoExistTest() throws IOException {
            String url = crmDev + path + "/99999";
            // 创建Get请求
            HttpGetAPI getAPI = new HttpGetAPI();
            // 组装请求头
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json;charset=UTF-8");
            headers.put("Authorization", CustomUserInfo.getInstance().getToken());
            getAPI.setHeaders(headers);

            // 设置请求uri和路径参数
            getAPI.setUrl(url);

            // 执行请求，获取返回结果
            String response = getAPI.sendRequest(null);

            //  System.out.println(response);
            JSONObject obj = JSON.parseObject(response);
            // 断言验证结果
            Assert.assertNotNull(obj.get("message"),"系统繁忙，请稍后重试");
            Assert.assertNull(obj.get("data"),null);
        }
    }



