package com.cy.test.longbit.daily.lb.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
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
 * Created by 璐从今夜白。 on 2019/10/29 14:17
 * <p>
 * Remark: 用户是否可以投票：Get: /activity/vote/can/vote
 */
@Listeners({MyExtentTestNgFormatter.class})
public class LB1008_ActivityCanVoteTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String val1;
    private String path = lb1008;


    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login();
    }

    @Test(description = "活动状为进行中时", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityTest() throws IOException {
        Reporter.log("用例1：活动状态为进行中时", true);
//         获取存在的活动id
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "select b.id FROM lang_resource a,vote_activity b WHERE a.lang_text = \"LL：测试接口001\" and a.linked_id = b.id");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("id") != null) {
                val1 = list1.get(0).get("id").toString();
            }
        }
        String url = devHost + path + val1;
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

        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertEquals(obj.get("message"),null);
        Assert.assertNotNull(obj.get("data"));
    }

    @Test(description = "活动状为已结束时", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityEndTest() throws IOException {
        Reporter.log("用例2：活动状为已结束时", true);
//         获取存在的活动id
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "select b.id FROM lang_resource a,vote_activity b WHERE a.lang_text = \"LL：活动已结束001\" and a.linked_id = b.id");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("id") != null) {
                val1 = list1.get(0).get("id").toString();
            }
        }
        String url1 = devHost + path + val1;
        // 创建get请求
        HttpGetAPI getApi = new HttpGetAPI();
        // 组装请求头
        HashMap<String,String> headers1 = new HashMap<String,String>();
        headers1.put("Content-Type", "application/json;charset=UTF-8");
        headers1.put("Authorization", CustomUserInfo.getInstance().getToken());
        getApi.setHeaders(headers1);

        // 设置请求uri和路径参数
        getApi.setUrl(url1);

        // 执行请求，获取返回结果
        String response1 = getApi.sendRequest(null);

        JSONObject obj = JSON.parseObject(response1);
        // 断言验证结果
        Assert.assertEquals(obj.get("message"),null);
        Assert.assertEquals(obj.get("data"),false);
    }

    @Test(description = "活动状态未开始时", priority = 2, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityNoStartTest() throws IOException {
        Reporter.log("用例3：活动状态未开始时", true);
//         获取存在的活动id
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "select b.id FROM lang_resource a,vote_activity b WHERE a.lang_text = \"LL：活动未开始001\" and a.linked_id = b.id");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("id") != null) {
                val1 = list1.get(0).get("id").toString();
            }
        }
        String url1 = devHost + path + val1;
        // 创建get请求
        HttpGetAPI getApi = new HttpGetAPI();
        // 组装请求头
        HashMap<String,String> headers1 = new HashMap<String,String>();
        headers1.put("Content-Type", "application/json;charset=UTF-8");
        headers1.put("Authorization", CustomUserInfo.getInstance().getToken());
        getApi.setHeaders(headers1);

        // 设置请求uri和路径参数
        getApi.setUrl(url1);

        // 执行请求，获取返回结果
        String response1 = getApi.sendRequest(null);

        JSONObject obj = JSON.parseObject(response1);
        // 断言验证结果
        Assert.assertEquals(obj.get("message"),null);
        Assert.assertEquals(obj.get("data"),false);
    }

    @AfterTest(description = "测试数据清理，每个测试方法结束时执行一次",alwaysRun = true)
    public void dbunitTest() {
        try {
            dbunitUtil.updateData(lbDataSource, "DELETE FROM b USING lang_resource a,vote_activity b WHERE a.lang_text = \"LL：活动未开始001\" and a.linked_id = b.id; ");
            dbunitUtil.updateData(lbDataSource,"DELETE FROM b USING lang_resource a,vote_activity b WHERE a.lang_text = \"LL：活动已结束001\" and a.linked_id = b.id; ");
        } catch (Exception e) {
            e.printStackTrace();
            //    Assert.fail(e.getMessage());
            System.out.println("删除失败");
        }
    }
}
