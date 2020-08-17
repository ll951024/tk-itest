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
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2020/7/28 19:54
 * <p>
 * Remark: 修改交易区：/longbit/crm/exchange/district/update_district
 */
public class CRM2003_UpdateDistrictTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    private String path = crm2003;
    private String val1;


    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login2();
    }

    @Test(description = "修改交易区成功", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void updateDistrictSuccessTest() throws IOException {
        // 创建Post请求
        HttpPostAPI postAPI = new HttpPostAPI();
        // 组装请求头
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        postAPI.setHeaders(headers);

        // 设置请求uri和路径参数
        String url = crmDev + path;
        String requestBody = "{\"showHome\":2,\"showMarket\":2,\"resourceMap\":{\"en-US\":{\"district\":\"USDT\",\"description\":\"\"},\"ko-KR\":{\"district\":\"USDT\",\"description\":\"\"},\"zh-CN\":{\"district\":\"USDT\",\"description\":\"\"}},\"id\":1}";
        // 设置请求uri和路径参数
        postAPI.setUrl(url);
        postAPI.setRequestString(requestBody);


        // 执行请求，获取返回结果
        String response = postAPI.sendRequest(null);

        //  System.out.println(response);
        JSONObject obj = JSON.parseObject(response);
        // 断言验证结果
        Assert.assertNull(obj.get("data"));
    }
}
