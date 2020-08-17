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
 * Created by 璐从今夜白。 on 2020/7/29 16:09
 * <p>
 * Remark: 交易区中删除交易对: /longbit/crm/exchange/district/del_symbol
 */
public class CRM2006_DeleteSymbolTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = crm2006;
    private String val1;


    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login2();
    }

    @Test(description = "删除成功", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void deleteSuccessTest() throws IOException {
        //获取存在的id
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "SELECT linked_id from longbit.lang_resource where table_name = \"exchange_district\" and lang_text = \"测试\";");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("linked_id") != null) {
                val1 = list1.get(0).get("linked_id").toString();
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

            String requestBody = "{\"districtId\":"+ val1+",\"symbolId\":9,\"symbol\":\"ETH-BTC\"}";

            // 设置请求uri和路径参数
            postAPI.setUrl(url);
            postAPI.setRequestString(requestBody);


            // 执行请求，获取返回结果
            String response = postAPI.sendRequest(null);

            //  System.out.println(response);
            JSONObject obj = JSON.parseObject(response);
            // 断言验证结果
            //Assert.assertNotNull(obj.get("data"));
            Assert.assertNull(obj.get("data"));
        }
    }

    @Test(description = "交易区中不存在该交易对", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void deleteFailTest() throws IOException {
        //获取存在的id
        List<Map<String, Object>> list1 = dbunitUtil.queryData(lbDataSource, "SELECT linked_id from longbit.lang_resource where table_name = \"exchange_district\" and lang_text = \"测试\";");
        if (CollectionUtils.isNotEmpty(list1)) {
            if (list1.get(0).get("linked_id") != null) {
                val1 = list1.get(0).get("linked_id").toString();
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

            String requestBody = "{\"districtId\":"+ val1+",\"symbolId\":9,\"symbol\":\"ETH-BTC\"}";

            // 设置请求uri和路径参数
            postAPI.setUrl(url);
            postAPI.setRequestString(requestBody);


            // 执行请求，获取返回结果
            String response = postAPI.sendRequest(null);

            //  System.out.println(response);
            JSONObject obj = JSON.parseObject(response);
            // 断言验证结果
            //Assert.assertNotNull(obj.get("data"));
            Assert.assertNotNull(obj.get("message"),"该交易区中不存在该交易对");
        }
    }

}
