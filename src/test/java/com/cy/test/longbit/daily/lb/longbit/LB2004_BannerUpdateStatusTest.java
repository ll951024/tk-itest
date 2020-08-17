package com.cy.test.longbit.daily.lb.longbit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
 * Created by 璐从今夜白。 on 2019/10/28 17:56
 * <p>
 * Remark: Banner上下线状态更新：longbit/crm/banner/update_status
 */
@Listeners({MyExtentTestNgFormatter.class})
public class LB2004_BannerUpdateStatusTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;
    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = lb2004;
    private String val;

    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login2();

    }

    @Test(description = "已上线Banner下线", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void bannerStatusDownTest() throws IOException {
        Reporter.log("用例1：已上线Banner下线", true);
        // 获取存在的BannerId
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "SELECT id from sys_banner where title = \"LL:新增banner接口测试\";");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("id") != null) {
                val = list1.get(0).get("id").toString();
                // 请求路径和url
                String url = crmDev + path;
                String requestBody = "{\"id\":\"" + val + "\",\"type\":1,\"status\":2}";
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

                 System.out.print("获取返回结果" + response);

                JSONObject obj = JSON.parseObject(response);
                // 断言验证结果
                Assert.assertNull(obj.get("message"), null);
                Assert.assertNull(obj.get("data"), null);
            }
        }
    }

    @Test(description = "已下线Banner上线", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void bannerStatusUpTest() throws IOException {
        Reporter.log("用例2：已下线Banner上线", true);
        // 获取存在的BannerId
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "SELECT id from sys_banner where title = \"LL:新增banner接口测试\";");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("id") != null) {
                val = list1.get(0).get("id").toString();
                // 请求路径和url
                String url = "http://dev.longbit.one:81" + path;
                String requestBody ="{\"id\":\"" + val + "\",\"type\":1,\"status\":1}";
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

                 System.out.print("获取返回结果" + response);

                JSONObject obj = JSON.parseObject(response);
                // 断言验证结果
                Assert.assertNull(obj.get("message"), null);
                Assert.assertNull(obj.get("data"),null);
            }
        }
    }
}
