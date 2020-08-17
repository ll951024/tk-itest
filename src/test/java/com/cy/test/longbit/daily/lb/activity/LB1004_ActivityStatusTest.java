package com.cy.test.longbit.daily.lb.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.test.base.BaseTest;
import com.cy.test.base.PathData;
import com.cy.test.common.MyExtentTestNgFormatter;
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
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2019/10/11 17:05
 * <p>
 * Remark: 活动状态上下线：/longbit/crm/activity/{id}/status/{status}
 */
@Listeners({MyExtentTestNgFormatter.class})
public class LB1004_ActivityStatusTest extends PathData {
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
        loginServerApi.login2();

    }

    @Test(description = "修改活动状态为下线", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void updateActivityStatusDownTest() throws IOException {
        Reporter.log("用例1：修改活动状态成功为下线", true);
//         获取存在的活动id
        List<Map<String, Object>> list = dbunitUtil.queryData(lbDataSource, "select b.id FROM longbit_activity.lang_resource a,longbit_activity.vote_activity b WHERE a.lang_text = \"活动进行中\" and a.linked_id = b.id");
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.get(0).get("id") != null) {
                val1 = list.get(0).get("id").toString();

            }
        }
        String url = crmDev + lb1004 + val1 + "/status/1";
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
        Assert.assertEquals(obj.get("message"),null);
        Assert.assertNull(obj.get("data"));

    }

    @Test(description = "修改活动状态为上线", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void updateActivityStatusUpTest() throws IOException {
        Reporter.log("用例2：修改活动状态成功为上线", true);
//         获取存在的活动id
        List<Map<String, Object>> list = dbunitUtil.queryData(lbDataSource, "select b.id FROM longbit_activity.lang_resource a,longbit_activity.vote_activity b WHERE a.lang_text = \"LL：测试接口001\" and a.linked_id = b.id");
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.get(0).get("id") != null) {
                val1 = list.get(0).get("id").toString();

            }
        }
        String url =  crmDev + lb1004 + val1 + "/status/2";
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
        Assert.assertEquals(obj.get("message"),null);
        Assert.assertNull(obj.get("data"));
    }

}
