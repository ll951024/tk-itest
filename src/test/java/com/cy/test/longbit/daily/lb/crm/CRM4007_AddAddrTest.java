package com.cy.test.longbit.daily.lb.crm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.test.base.PathData;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Maps;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2020/7/30 11:13
 * <p>
 * Remark: 添加地址库：  /longbit/crm/withdraw/addr/add
 */
public class CRM4007_AddAddrTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = crm4007;
    private String val1;

    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login2();
    }

    @Test(description = "新增提现地址成功", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void addAddrSuccessTest() throws IOException {
            // 创建Post请求
            HttpPostAPI postAPI = new HttpPostAPI();
            // 组装请求头
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json;charset=UTF-8");
            headers.put("Authorization", CustomUserInfo.getInstance().getToken());
            postAPI.setHeaders(headers);

            // 设置请求uri和路径参数
            String url = crmDev + path;
            String requestBody = "{\"coinCode\":\"GXC\",\"address\":\"lltest\",\"status\":0}";
            // 设置请求uri和路径参数
            postAPI.setUrl(url);
            postAPI.setRequestString(requestBody);


            // 执行请求，获取返回结果
            String response = postAPI.sendRequest(null);

            //  System.out.println(response);
            JSONObject obj = JSON.parseObject(response);
            // 断言验证结果
            Assert.assertNull(obj.get("message"));

        }

    @Test(description = "新增提现地址已存在", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void addAddrExistTest() throws IOException {
        List<Map<String, Object>> list2 = dbunitUtil.queryData(lbDataSource, "SELECT address  FROM longbit.sys_withdraw_address where address='lltest';");
        if (CollectionUtils.isNotEmpty(list2)) {
            if (list2.get(0).get("address") != null) {
                val1 = list2.get(0).get("address").toString();
            }

            // 创建Post请求
            HttpPostAPI postAPI = new HttpPostAPI();
            // 组装请求头
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json;charset=UTF-8");
            headers.put("Authorization", CustomUserInfo.getInstance().getToken());
            postAPI.setHeaders(headers);

            // 设置请求uri和路径参数
            String url = crmDev + path;
            Map<String, Object> map = Maps.newHashMap();
            map.put("coinCode", "GXC");
            map.put("address", val1);
            map.put("status", 0);
            String requestBody = JSON.toJSONString(map);

            // 设置请求uri和路径参数
            postAPI.setUrl(url);
            postAPI.setRequestString(requestBody);


            // 执行请求，获取返回结果
            String response = postAPI.sendRequest(null);

            //  System.out.println(response);
            JSONObject obj = JSON.parseObject(response);
            // 断言验证结果
            Assert.assertNotNull(obj.get("message"),"币种和地址组合已存在");

        }
    }

    @AfterTest(description = "测试数据清理，每个测试方法结束时执行一次",alwaysRun = true)
    public void dbunitTest() {
        try {
            dbunitUtil.updateData(lbDataSource, "DELETE FROM longbit.sys_withdraw_address where address='lltest';");
        } catch (Exception e) {
            e.printStackTrace();
            //    Assert.fail(e.getMessage());
            System.out.println("删除失败");
        }
    }


}
