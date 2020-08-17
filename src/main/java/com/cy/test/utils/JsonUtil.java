package com.cy.test.utils;

import com.google.gson.JsonObject;
import net.sf.json.test.JSONAssert;
import org.apache.http.NameValuePair;
import org.springframework.stereotype.Service;
import org.testng.Assert;
import org.testng.Reporter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JsonUtil {

	public Object get(String jsonStr, String fieldPath) {

		JSONObject jsonObject = null;
		JSONArray jsonArray = null;

		try {
			if (jsonStr.startsWith("[")) {
				jsonArray = JSON.parseArray(jsonStr);
			} else {
				jsonObject = JSON.parseObject(jsonStr);
			}

			String[] filedPaths = fieldPath.split("\\.");

			for (int i = 0; i < filedPaths.length; i++) {

				if (i < filedPaths.length - 1) {
					// 判断下一级是否是数字
					if (filedPaths[i + 1].matches("\\d+")) {
						if (jsonObject == null) {
							jsonArray = jsonArray.getJSONArray(Integer.parseInt(filedPaths[i]));
							jsonObject = null;
						} else {
							jsonArray = jsonObject.getJSONArray(filedPaths[i]);
							jsonObject = null;
						}
					} else {
						if (jsonObject == null) {
							jsonObject = jsonArray.getJSONObject(Integer.parseInt(filedPaths[i]));
							jsonArray = null;
						} else {
							jsonObject = jsonObject.getJSONObject(filedPaths[i]);
							jsonArray = null;
						}
					}
				}

				else {
					if (jsonObject == null) {
						return jsonArray.get(Integer.parseInt(filedPaths[i]));
					} else {
						return jsonObject.get(filedPaths[i]);
					}
				}
			}
		} catch (Exception e) {
			Reporter.log(e.getMessage(), true);
			Assert.fail(e.getMessage());
		}

		return null;
	}

	public void JsonAssert(String actualJsonStr, String actualFieldPath, Object expected) {
		Object actual = this.get(actualJsonStr, actualFieldPath);
		Assert.assertEquals(actual, expected);
	}
	public void JsonAssert(Object actual, Object expected) {
		Assert.assertEquals(actual, expected);
	}

	public void JSONArrayAssert(JSONArray actual,JSONArray expect){
		JSONAssert.assertEquals(actual,expect);
	}

	public String toJson(Map map){
		String result=JSONObject.toJSONString(map);
		return result;
	}

}
