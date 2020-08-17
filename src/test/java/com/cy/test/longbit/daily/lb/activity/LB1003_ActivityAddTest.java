package com.cy.test.longbit.daily.lb.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cy.test.base.PathData;
import com.cy.test.common.MyExtentTestNgFormatter;
import com.cy.test.longbit.daily.lb.LoginServerApi;
import com.cy.test.longbit.resp.CustomUserInfo;
import com.cy.test.base.BaseTest;
import com.cy.test.group.ProjectGroup;
import com.cy.test.group.ServiceGroup;
import com.cy.test.http.utils.HttpPostAPI;
import com.cy.test.utils.DBUnitUtil;
import com.cy.test.utils.JsonUtil;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * Description: tk-itest
 * <p>
 * Created by 璐从今夜白。 on 2019/10/10 16:38
 * <p>
 * Remark: /longbit/crm/activity/add
 */
@Listeners({MyExtentTestNgFormatter.class})
public class LB1003_ActivityAddTest extends PathData {
    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    DBUnitUtil dbunitUtil;

    @Autowired
    @Qualifier("lbDatasource")
    BasicDataSource lbDataSource;
    public String path = lb1003;

    //获取登录接口返回值的auth
    @BeforeMethod(alwaysRun = true)
    public void beforeTestMethod() throws IOException {
        LoginServerApi loginServerApi = new LoginServerApi();
        loginServerApi.login2();
    }

    @Test(description = "新建投票活动成功", priority = 0, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activitySuccessTest()throws IOException{
        Reporter.log("用例1：新建投票活动成功", true);
//         请求路径和uri
        String url = crmDev + path ;
        String requestBody ="{\"title\":[{\"lang\":\"zh-CN\",\"langText\":\"LL：接口测试001\"}],\"pcBanner\":[{\"lang\":\"zh-CN\",\"langText\":\"https://lbp-h5.oss-cn-shanghai.aliyuncs.com/crm/15/chat/2020070911511496584204.jpeg\"}],\"appBanner\":[{\"lang\":\"zh-CN\",\"langText\":\"https://lbp-h5.oss-cn-shanghai.aliyuncs.com/crm/15/chat/2020070911511933880997.jpeg\"}],\"noticeUrl\":[{\"lang\":\"zh-CN\",\"langText\":\"www.baidu.com\"}],\"voteDescription\":[{\"lang\":\"zh-CN\",\"langText\":\"<p>暂无活动说明</p>\"}],\"noticeResultUrl\":[{\"lang\":\"zh-CN\",\"langText\":\"\"}],\"startTime\":\"2020-07-09 00:00:00\",\"endTime\":\"2021-02-28 00:00:00\",\"lowestHoldLbp\":0,\"sevenDayVolume\":0,\"authLevelType\":1,\"registerTimeLimit\":\"2020-07-09 00:00:00\",\"whiteList\":\"\",\"changeLbpUnitPrice\":null,\"dayChangeLimit\":null,\"totalChangeLimit\":null,\"initTicket\":2,\"risingStatus\":1,\"changeTicketStatus\":2,\"risingPeriod\":1,\"risingTicket\":1,\"freezeType\":1,\"freezeAmount\":\"\",\"ipLimitType\":1,\"ipLimitCount\":\"\",\"deviceLimitType\":1,\"deviceLimitCount\":\"\",\"voteActivityCoinList\":[{\"canDelete\":false,\"symbol\":\"LL\",\"icon\":\"https://lbp-h5.oss-cn-shanghai.aliyuncs.com/crm/15/chat/2020070911523846499916.jpeg\",\"projectName\":[{\"lang\":\"zh-CN\",\"langText\":\"LL测试项目名称\"}],\"projectIntro\":[{\"lang\":\"zh-CN\",\"langText\":\"LL测试项目简介\"}],\"officialWebsite\":\"longbit.com\",\"whitePaperUrl\":\"longbit.com\"}]}";
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

    @Test(description = "必填项为空新增失败", priority = 1, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityNullTest()throws IOException{
        Reporter.log("用例2：必填项为空新增失败", true);
//         请求路径和uri
        String url = crmDev + path ;
        String requestBody ="{}";
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
        Assert.assertEquals(obj.get("message"),"参数不正确");
        Assert.assertNull(obj.get("data"));

    }

    @Test(description = "新增已结束活动", priority = 2, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityStatusEndTest() throws IOException {
        //新建已结束活动
        String url = crmDev + path ;
        String requestBody = "{\n" +
                "    \"title\":[\n" +
                "        {\n" +
                "            \"lang\":\"zh-CN\",\n" +
                "            \"langText\":\"LL：活动已结束001\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pcBanner\":[\n" +
                "        {\n" +
                "            \"lang\":\"zh-CN\",\n" +
                "            \"langText\":\"https://lbp-dev.oss-cn-hongkong.aliyuncs.com/crm/15/chat/2019092318371051186132.jpeg\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"appBanner\":[\n" +
                "        {\n" +
                "            \"lang\":\"zh-CN\",\n" +
                "            \"langText\":\"https://lbp-dev.oss-cn-hongkong.aliyuncs.com/crm/15/chat/2019092318371594535832.jpeg\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"noticeUrl\":[\n" +
                "        {\n" +
                "            \"lang\":\"zh-CN\",\n" +
                "            \"langText\":\"http://192.168.10.250/login\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"voteDescription\":[\n" +
                "        {\n" +
                "            \"lang\":\"zh-CN\",\n" +
                "            \"langText\":\"安慰别人的时候一套一套的，安慰自己的时候只想找根绳子一套。\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"noticeResultUrl\":[\n" +
                "        {\n" +
                "            \"lang\":\"zh-CN\",\n" +
                "            \"langText\":\"http://192.168.10.250/login\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"startTime\":\"2018-10-10 00:00:00\",\n" +
                "    \"endTime\":\"2018-10-30 00:00:00\",\n" +
                "    \"lowestHoldLbp\":1000,\n" +
                "    \"sevenDayVolume\":0,\n" +
                "    \"authLevelType\":1,\n" +
                "    \"registerTimeLimit\":\"2019-10-10 13:50:00\",\n" +
                "    \"whiteList\":\"90\",\n" +
                "    \"changeLbpUnitPrice\":100,\n" +
                "    \"dayChangeLimit\":80,\n" +
                "    \"totalChangeLimit\":20,\n" +
                "    \"initTicket\":10,\n" +
                "    \"risingStatus\":1,\n" +
                "    \"risingPeriod\":1,\n" +
                "    \"risingTicket\":1,\n" +
                "    \"freezeType\":4,\n" +
                "    \"freezeAmount\":\"100\",\n" +
                "    \"ipLimitType\":1,\n" +
                "    \"ipLimitCount\":\"\",\n" +
                "    \"deviceLimitType\":1,\n" +
                "    \"deviceLimitCount\":\"\",\n" +
                "    \"voteActivityCoinList\":[\n" +
                "        {\n" +
                "            \"canDelete\":true,\n" +
                "            \"symbol\":\"LL测试币种\",\n" +
                "            \"icon\":\"https://lbp-dev.oss-cn-hongkong.aliyuncs.com/crm/15/chat/2019092318384807374725.jpeg\",\n" +
                "            \"projectName\":[\n" +
                "                {\n" +
                "                    \"lang\":\"zh-CN\",\n" +
                "                    \"langText\":\"BZZ001\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"projectIntro\":[\n" +
                "                {\n" +
                "                    \"lang\":\"zh-CN\",\n" +
                "                    \"langText\":\"安慰别人的时候一套一套的，安慰自己的时候只想找根绳子一套。\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"officialWebsite\":\"http://testlink.longbit.co/index.php?caller=login&viewer=\",\n" +
                "            \"whitePaperUrl\":\"http://testlink.longbit.co/index.php?caller=login&viewer=\"\n" +
                "        },\n" +
                "       \n" +
                "         {\n" +
                "            \"canDelete\":true,\n" +
                "            \"symbol\":\"BZZ003\",\n" +
                "            \"icon\":\"https://lbp-dev.oss-cn-hongkong.aliyuncs.com/crm/15/chat/2019092318422724628950.jpeg\",\n" +
                "            \"projectName\":[\n" +
                "                {\n" +
                "                    \"lang\":\"zh-CN\",\n" +
                "                    \"langText\":\"BZ004\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"lang\":\"en-US\",\n" +
                "                    \"langText\":\"BZ004\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"projectIntro\":[\n" +
                "                {\n" +
                "                    \"lang\":\"zh-CN\",\n" +
                "                    \"langText\":\"明明靠脸就能吃饭偏偏要靠才华”并不可悲，可悲的是我们这种“明明靠脸不能吃饭，偏偏靠才华也吃不饱\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"lang\":\"en-US\",\n" +
                "                    \"langText\":\"明明靠脸就能吃饭偏偏要靠才华”并不可悲，可悲的是我们这种“明明靠脸不能吃饭，偏偏靠才华也吃不饱\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"officialWebsite\":\"http://testlink.longbit.co/index.php?caller=login&viewer=\",\n" +
                "            \"whitePaperUrl\":\"http://testlink.longbit.co/index.php?caller=login&viewer=\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
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
        // 断言验证结果
        Assert.assertEquals(obj.get("message"), null);
        Assert.assertNull(obj.get("data"));
    }

    @Test(description = "新增未开始活动", priority = 3, groups = {ServiceGroup.LB, ProjectGroup.LB})
    public void activityStatusNoStartTest() throws IOException{
        //新建未开始活动
        String url = crmDev + path ;
        String requestBody ="{\n" +
                "    \"title\":[\n" +
                "        {\n" +
                "            \"lang\":\"zh-CN\",\n" +
                "            \"langText\":\"LL：活动未开始001\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pcBanner\":[\n" +
                "        {\n" +
                "            \"lang\":\"zh-CN\",\n" +
                "            \"langText\":\"https://lbp-dev.oss-cn-hongkong.aliyuncs.com/crm/15/chat/2019092318371051186132.jpeg\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"appBanner\":[\n" +
                "        {\n" +
                "            \"lang\":\"zh-CN\",\n" +
                "            \"langText\":\"https://lbp-dev.oss-cn-hongkong.aliyuncs.com/crm/15/chat/2019092318371594535832.jpeg\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"noticeUrl\":[\n" +
                "        {\n" +
                "            \"lang\":\"zh-CN\",\n" +
                "            \"langText\":\"http://192.168.10.250/login\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"voteDescription\":[\n" +
                "        {\n" +
                "            \"lang\":\"zh-CN\",\n" +
                "            \"langText\":\"安慰别人的时候一套一套的，安慰自己的时候只想找根绳子一套。\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"noticeResultUrl\":[\n" +
                "        {\n" +
                "            \"lang\":\"zh-CN\",\n" +
                "            \"langText\":\"http://192.168.10.250/login\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"startTime\":\"2022-10-10 00:00:00\",\n" +
                "    \"endTime\":\"2022-10-30 00:00:00\",\n" +
                "    \"lowestHoldLbp\":1000,\n" +
                "    \"sevenDayVolume\":0,\n" +
                "    \"authLevelType\":1,\n" +
                "    \"registerTimeLimit\":\"2022-10-10 13:50:00\",\n" +
                "    \"whiteList\":\"90\",\n" +
                "    \"changeLbpUnitPrice\":100,\n" +
                "    \"dayChangeLimit\":80,\n" +
                "    \"totalChangeLimit\":20,\n" +
                "    \"initTicket\":10,\n" +
                "    \"risingStatus\":1,\n" +
                "    \"risingPeriod\":1,\n" +
                "    \"risingTicket\":1,\n" +
                "    \"freezeType\":4,\n" +
                "    \"freezeAmount\":\"100\",\n" +
                "    \"ipLimitType\":1,\n" +
                "    \"ipLimitCount\":\"\",\n" +
                "    \"deviceLimitType\":1,\n" +
                "    \"deviceLimitCount\":\"\",\n" +
                "    \"voteActivityCoinList\":[\n" +
                "        {\n" +
                "            \"canDelete\":true,\n" +
                "            \"symbol\":\"LL测试币种\",\n" +
                "            \"icon\":\"https://lbp-dev.oss-cn-hongkong.aliyuncs.com/crm/15/chat/2019092318384807374725.jpeg\",\n" +
                "            \"projectName\":[\n" +
                "                {\n" +
                "                    \"lang\":\"zh-CN\",\n" +
                "                    \"langText\":\"BZZ001\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"projectIntro\":[\n" +
                "                {\n" +
                "                    \"lang\":\"zh-CN\",\n" +
                "                    \"langText\":\"安慰别人的时候一套一套的，安慰自己的时候只想找根绳子一套。\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"officialWebsite\":\"http://testlink.longbit.co/index.php?caller=login&viewer=\",\n" +
                "            \"whitePaperUrl\":\"http://testlink.longbit.co/index.php?caller=login&viewer=\"\n" +
                "        },\n" +
                "       \n" +
                "         {\n" +
                "            \"canDelete\":true,\n" +
                "            \"symbol\":\"BZZ003\",\n" +
                "            \"icon\":\"https://lbp-dev.oss-cn-hongkong.aliyuncs.com/crm/15/chat/2019092318422724628950.jpeg\",\n" +
                "            \"projectName\":[\n" +
                "                {\n" +
                "                    \"lang\":\"zh-CN\",\n" +
                "                    \"langText\":\"BZ004\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"lang\":\"en-US\",\n" +
                "                    \"langText\":\"BZ004\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"projectIntro\":[\n" +
                "                {\n" +
                "                    \"lang\":\"zh-CN\",\n" +
                "                    \"langText\":\"明明靠脸就能吃饭偏偏要靠才华”并不可悲，可悲的是我们这种“明明靠脸不能吃饭，偏偏靠才华也吃不饱\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"lang\":\"en-US\",\n" +
                "                    \"langText\":\"明明靠脸就能吃饭偏偏要靠才华”并不可悲，可悲的是我们这种“明明靠脸不能吃饭，偏偏靠才华也吃不饱\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"officialWebsite\":\"http://testlink.longbit.co/index.php?caller=login&viewer=\",\n" +
                "            \"whitePaperUrl\":\"http://testlink.longbit.co/index.php?caller=login&viewer=\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
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
        // 断言验证结果
        Assert.assertEquals(obj.get("message"),null);
        Assert.assertNull(obj.get("data"));
    }



    @AfterMethod(description = "测试数据清理，每个测试方法结束时执行一次",alwaysRun = true)
    @Test
    public void dbunitTest() {
        try {
            dbunitUtil.updateData(lbDataSource, "DELETE FROM b USING longbit_activity.lang_resource a,longbit_activity.vote_activity b WHERE a.lang_text = \"LL：测试接口001\" and a.linked_id = b.id; ");
        } catch (Exception e) {
            e.printStackTrace();
            //    Assert.fail(e.getMessage());
            System.out.println("删除失败");
        }
    }


}
