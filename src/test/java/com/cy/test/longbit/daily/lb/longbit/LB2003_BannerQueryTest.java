package com.cy.test.longbit.daily.lb.longbit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.test.base.BaseTest;
import com.cy.test.base.ExcelData;
import com.cy.test.base.PathData;
import com.cy.test.common.MyExtentTestNgFormatter;
import com.cy.test.group.ProjectGroup;
import com.cy.test.group.ServiceGroup;
import com.cy.test.http.utils.HttpGetAPI;
import com.cy.test.longbit.daily.lb.LoginServerApi;
import com.cy.test.longbit.resp.CustomUserInfo;
import com.cy.test.utils.DBUnitUtil;
import com.cy.test.utils.JsonUtil;
import jxl.read.biff.BiffException;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2019/10/24 11:15
 * <p>
 * Remark: Banner查询接口：/longbit/crm/banner/query
 */
@Listeners({MyExtentTestNgFormatter.class})
public class LB2003_BannerQueryTest extends BaseTest {
    @Autowired
    JsonUtil jsonUtil;
    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
  //  private String path = lb2003;

    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login2();
    }

    // 获取excel用例中的字段
    @DataProvider(name = "LB2003_BannerQueryTest")
    public Object[][] getData() throws BiffException, IOException {
        ExcelData excelData = new ExcelData("longbit_cases", "LB2003_BannerQueryTest");
        return excelData.getExcelData();
    }
    //测试用例
    @Test(description = "条件查询", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB},dataProvider = "LB2003_BannerQueryTest")
    public void bannerQueryTest(HashMap<String,String> data) throws IOException{
        //获取excel参数
        String TC_ID=data.get("TC_ID");
        String TC_Name=data.get("TC_Name");
        String pageSize=data.get("pageSize");
        String pageNo=data.get("pageNo");
        String title=data.get("title");
        String type=data.get("type");
     /*   int expectCode = Integer.parseInt(data.get("expectCode"));
        String expectSuccess = data.get("expectSuccess");*/

        //请求路径和url
       // String url =  crmDev + path;
        String url = "http://dev.longbit.one:81/longbit/crm/banner/query";
        System.out.println(url);
      //  System.out.println(TC_ID);
        System.out.println(TC_Name);

        //创建get 请求
        HttpGetAPI getAPI = new HttpGetAPI();

        // 组装请求头
        HashMap<String,String> headers = new HashMap<String,String>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        headers.put("Authorization", CustomUserInfo.getInstance().getToken());
        getAPI.setHeaders(headers);


        //设置请求url和路径参数
        getAPI.setUrl(url);
        getAPI.getUrlParams().put("pageNo",pageNo);
        getAPI.getUrlParams().put("pageSize",pageSize);
        getAPI.getUrlParams().put("title",title);
        getAPI.getUrlParams().put("type",type);

        // 执行请求，获取返回结果
        String response = getAPI.sendRequest(null);

        // System.out.print("获取返回结果" + response);
        JSONObject obj = JSON.parseObject(response);

        // 断言验证结果
        Assert.assertEquals(obj.get("message"),null);

    }

}
