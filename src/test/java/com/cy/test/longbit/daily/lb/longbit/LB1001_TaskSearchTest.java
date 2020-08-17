package com.cy.test.longbit.daily.lb.longbit;

import com.cy.test.base.BaseTest;
import com.cy.test.base.PathData;
import com.cy.test.common.MyExtentTestNgFormatter;
import com.cy.test.longbit.daily.lb.LoginServerApi;
import com.cy.test.longbit.resp.CustomUserInfo;
import com.cy.test.http.utils.HttpPostAPI;
import com.cy.test.utils.DBUnitUtil;
import com.cy.test.utils.JsonUtil;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
@Listeners({MyExtentTestNgFormatter.class})
public class LB1001_TaskSearchTest extends BaseTest {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

/*//     获取system.properties中定义的host和port
    @Value("${lb.host1}")
    private String host;
    @Value("${lb.port1}")
    private String port;*/
   // private String path = lb1001;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;

    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login();
    }

    @Test
    public void ticketSuccessTest()throws IOException{
        Reporter.log("用例1：taskSearch", true);
//         请求路径和uri
        String url = "http://dev.longbit.one:81/ui/institution/fuzzyMatchByName" ;
        String requestBody ="{\"amount\":\"1000\",\"productId\":1,\"periodsId\":1}";


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

        System.out.print("获取返回结果" + response);

        // 断言验证结果
//        jsonUtil.JsonAssert(response, "data", "null");
//        jsonUtil.JsonAssert(response, "message", "null");
    }


}
