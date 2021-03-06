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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2020/7/17 14:54
 * <p>
 * Remark: 删除交易区:/longbit/crm/exchange/district/del_district
 */
public class CRM2002_DeleteDistrictTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = crm2002;
    private String val1;


    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login2();
    }

    @Test(description = "交易区没有关联交易对，删除成功", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void deleteSuccessTest() throws IOException {
        //获取存在的id
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "select id FROM longbit.exchange_district WHERE editor_name = \"lilu\" and can_delete = 1");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("id") != null) {
                val1 = list1.get(0).get("id").toString();
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

            String requestBody = "{\"id\":"+val1+"}";

            // 设置请求uri和路径参数
            postAPI.setUrl(url);
            postAPI.setRequestString(requestBody);


            // 执行请求，获取返回结果
            String response = postAPI.sendRequest(null);

            //  System.out.println(response);
            JSONObject obj = JSON.parseObject(response);
            // 断言验证结果
            //Assert.assertNotNull(obj.get("data"));
            Assert.assertNull(obj.get("massage"), "该交易区不能删除");
        }
    }


    @Test(description = "交易区关联交易对，删除失败", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void deleteFailTest() throws IOException {
        //获取存在的活动id
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "select id FROM longbit.exchange_district WHERE editor_name = \"lilu\" and can_delete = 2");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("id") != null) {
                val1 = list1.get(0).get("id").toString();
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

            String requestBody = "{\"id\":"+val1+"}";

            // 设置请求uri和路径参数
            postAPI.setUrl(url);
            postAPI.setRequestString(requestBody);


            // 执行请求，获取返回结果
            String response = postAPI.sendRequest(null);

            //  System.out.println(response);
            JSONObject obj = JSON.parseObject(response);
            // 断言验证结果
            Assert.assertNotNull(obj.get("message"),"交易区中还有交易对，不能删除该交易区");
        }
    }



}
