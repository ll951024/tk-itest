package com.cy.test.longbit.daily.lb.longbit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.test.base.BaseTest;
import com.cy.test.base.PathData;
import com.cy.test.common.MyExtentTestNgFormatter;
import com.cy.test.group.ProjectGroup;
import com.cy.test.group.ServiceGroup;
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
 * Created by 璐从今夜白。 on 2019/10/23 16:19
 * <p>
 * Remark: 编辑Banner: /longbit/crm/banner/update
 */
@Listeners({MyExtentTestNgFormatter.class})
public class LB2002_BannerUpdateTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;
    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = lb2002;
    private String val;

    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login2();

    }

    @Test(description = "编辑Banner成功", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void bannerSuccessTest() throws IOException {
        Reporter.log("用例1：编辑Banner成功", true);
        // 获取存在的BannerId
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "SELECT id from sys_banner where title = \"LL:新增banner接口测试\";");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("id") != null) {
                val = list1.get(0).get("id").toString();
//         请求路径和uri
                String url = crmDev  + path;
                String requestBody = "{\"id\":\"" + val +"\",\"title\":\"LL:新增banner接口测试\",\"status\":1,\"resource\":{\"zh-CN\":{\"name\":\"cat1.jpg\",\"url\":\"https://longbiton.oss-cn-hongkong.aliyuncs.com/crm/12/chat/2019101723433448859492.jpeg\",\"link\":\"http://192.168.10.250/event/coin-updown?id=55\",\"content\":1,\"img\":\"{\\\"url\\\":\\\"https://longbiton.oss-cn-hongkong.aliyuncs.com/crm/12/chat/2019101723433448859492.jpeg\\\",\\\"name\\\":\\\"cat1.jpg\\\"}\"}},\"type\":1,\"lang\":\"zh-CN\"}";
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
                Assert.assertEquals(obj.get("message"), null);
                Assert.assertNull(obj.get("data"));
            }
        }
    }

    @Test(description = "编辑Banner标题为空", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void bannerFailTest() throws IOException {
        Reporter.log("用例1：编辑Banner标题成功", true);
        // 获取存在的BannerId
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "SELECT id from sys_banner where title = \"LL:新增banner接口测试\";");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("id") != null) {
                val = list1.get(0).get("id").toString();
//         请求路径和uri
                String url = "http://192.168.10.250:81" + path;
                String requestBody = "{\"id\":\"" + val +"\",\"title\":\"\",\"status\":1,\"resource\":{\"zh-CN\":{\"name\":\"cat1.jpg\",\"url\":\"https://longbiton.oss-cn-hongkong.aliyuncs.com/crm/12/chat/2019101723433448859492.jpeg\",\"link\":\"http://192.168.10.250/event/coin-updown?id=55\",\"content\":1,\"img\":\"{\\\"url\\\":\\\"https://longbiton.oss-cn-hongkong.aliyuncs.com/crm/12/chat/2019101723433448859492.jpeg\\\",\\\"name\\\":\\\"cat1.jpg\\\"}\"}},\"type\":1,\"lang\":\"zh-CN\"}";
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
                Assert.assertEquals(obj.get("message"), null);
                Assert.assertNull(obj.get("data"));
            }
        }
    }

     @AfterTest(description = "测试数据清理，每个测试方法结束时执行一次",alwaysRun = true)
    public void dbunitTest() {
        try {
            dbunitUtil.updateData(lbDataSource, "DELETE FROM sys_banner WHERE title = \"\";");
        } catch (Exception e) {
            e.printStackTrace();
            //    Assert.fail(e.getMessage());
            System.out.println("删除失败");
        }
    }



}




