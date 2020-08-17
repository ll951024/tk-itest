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
import com.cy.test.utils.DBUnitUtil;
import com.cy.test.utils.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2019/10/17 16:53
 * <p>
 * Remark: 投票接口：/activity/vote
 */
@Listeners({MyExtentTestNgFormatter.class})
public class LB1006_ActivityVoteTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String val1;
    private String val2;


    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login();
    }

    @Test(description = "用户投票成功", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityVoteSuccessTest() throws IOException {
        Reporter.log("用例1：用户投票成功", true);
//         获取存在的活动id
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "select b.id FROM lang_resource a,vote_activity b WHERE a.lang_text = \"LL：测试接口001\" and a.linked_id = b.id");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("id") != null) {
                val1 = list1.get(0).get("id").toString();
            }

    //获取当前活动下的关联的币种id
            List<Map<String, Object>> list = dbunitUtil.queryData(lbDataSource, "SELECT id from vote_activity_coin where symbol = \"LL测试币种\"");
            if (CollectionUtils.isNotEmpty(list)) {
                if (list.get(0).get("id") != null) {
                    val2 = list.get(0).get("id").toString();

                }
            }
        String url = devHost + lb1006;
        String requestBody = "{\"voteActivityId\":\" "+ val1+ "\",\"voteCoins\":[{\"coinId\":\""+val2+"\",\"ticketCount\":1}]}";
        // 创建post请求
        HttpPostAPI postApi = new HttpPostAPI();
        // 组装请求头
        HashMap<String,String> headers = new HashMap<String,String>();
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
        Assert.assertEquals(obj.get("message"),null);
        Assert.assertNull(obj.get("data"));
        }
    }

    @Test(description = "活动未上线用户投票", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityVoteFailTest() throws IOException {
        Reporter.log("用例2：活动未上线用户投票失败", true);
//         获取存在的活动id
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "select b.id FROM lang_resource a,vote_activity b WHERE a.lang_text = \"LL活动状态未上线\" and a.linked_id = b.id");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("id") != null) {
                val1 = list1.get(0).get("id").toString();

            }

            //获取当前活动下的关联的币种id
            List<Map<String, Object>> list = dbunitUtil.queryData(lbDataSource, "SELECT id from vote_activity_coin where symbol = \"BZdown\"");
            if (CollectionUtils.isNotEmpty(list)) {
                if (list.get(0).get("id") != null) {
                    val2 = list.get(0).get("id").toString();

                }
            }
            String url = devHost + lb1006;
            String requestBody = "{\"voteActivityId\":\" "+ val1+ "\",\"voteCoins\":[{\"coinId\":\""+val2+"\",\"ticketCount\":1}]}";
            // 创建post请求
            HttpPostAPI postApi = new HttpPostAPI();
            // 组装请求头
            HashMap<String,String> headers = new HashMap<String,String>();
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
            Assert.assertEquals(obj.get("message"),"error.vote.activity.not.online");
            Assert.assertNull(obj.get("data"));
        }
    }

    @Test(description = "活动id不存在", priority = 2, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityIdNoExistTest() throws IOException {
        Reporter.log("用例3：活动id不存在", true);
        String url = devHost + lb1006;
        String requestBody = "{\"voteActivityId\":99999,\"voteCoins\":[{\"coinId\":99999,\"ticketCount\":1}]}";
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
        Assert.assertEquals(obj.get("message"), "error.param.invalid");
        Assert.assertNull(obj.get("data"));
    }

    @AfterTest(description = "测试数据清理，每个测试方法结束时执行一次",alwaysRun = true)
    public void dbunitTest(){
        try {
            dbunitUtil.updateData(lbDataSource, "DELETE FROM user_vote_log where longbit.user_id = 90 and longbit_activity.vote_activity_id = \""+val1+"\"");
        } catch (Exception e) {
            e.printStackTrace();
            //    Assert.fail(e.getMessage());
            System.out.println("删除失败");
        }
    }

}
