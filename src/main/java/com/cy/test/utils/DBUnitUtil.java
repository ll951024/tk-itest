package com.cy.test.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.csv.CsvDataSetWriter;
import org.dbunit.dataset.csv.CsvURLDataSet;
import org.dbunit.dataset.xml.XmlDataSetWriter;
import org.springframework.stereotype.Service;
import org.testng.Assert;

import com.alibaba.fastjson.JSONArray;

/**
 * Created by a on 2017/3/20.
 */

@Service
public class DBUnitUtil {
	/**
	 * 更新插入删除操作
	 * 
	 * @param dataSource
	 * @param sql
	 * @return
	 */
	public int updateData(DataSource dataSource, String sql) {
		int result;
		try {
			QueryRunner runner = new QueryRunner(dataSource);

			result = runner.update(sql);
			dataSource.getConnection().close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 查询数据
	 * 
	 * @param dataSource
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> queryData(DataSource dataSource, String sql) {
		List<Map<String, Object>> result = null;
		try {
			QueryRunner runner = new QueryRunner(dataSource);

			result = runner.query(sql, new MapListHandler());
			dataSource.getConnection().close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Json结果List与数据库map结果断言对比
	 * 
	 * @param dbResult
	 * @param dataList
	 * @param i
	 * @param db_key
	 *            数据库中字段名称
	 * @param json_key
	 *            json返回结果中字段名称
	 */
	public void compareToDB(List<Map<String, Object>> dbResult, JSONArray dataList, int i, String db_key,
			String json_key) {
		Object actual_result = dbResult.get(i).get(db_key);
		if (actual_result instanceof Date) {
			// 去除日期数据最后的..0
			actual_result = String.valueOf(actual_result).substring(0, 19);
		} else {
			actual_result = String.valueOf(actual_result);
		}
		Assert.assertEquals(actual_result, String.valueOf(dataList.getJSONObject(i).get(json_key)));
	}

	public void eportQueryDataIntoCSV(DataSource dataSource, String testFolder, String[] sqls, String[] tableNames)
			throws SQLException, DatabaseUnitException, IOException {
		// 获取当前测试数据所在目录
		String current_dir= getTestDataFolder(testFolder);
		Connection connection = dataSource.getConnection();
		IDatabaseConnection iConnection = new DatabaseConnection(connection);
		QueryDataSet partialDataSet = new QueryDataSet(iConnection);
		for (int i = 0; i < sqls.length; i++) {
			partialDataSet.addTable(tableNames[i], sqls[i]);
		}

		CsvDataSetWriter csvDataSetWriter = new CsvDataSetWriter(current_dir);
		csvDataSetWriter.write(partialDataSet);
	}

	public void exportQueryDataIntoXml(DataSource dataSource, String testFolder, String[] sqls, String[] tableNames)
			throws SQLException, DatabaseUnitException, IOException {
		// 获取当前测试数据所在目录
		String current_dir= getTestDataFolder(testFolder);
		Connection connection = dataSource.getConnection();
		IDatabaseConnection iConnection = new DatabaseConnection(connection);
		QueryDataSet partialDataSet = new QueryDataSet(iConnection);
		for (int i = 0; i < sqls.length; i++) {
			partialDataSet.addTable(tableNames[i], sqls[i]);
		}
		// Writer writer = new FileWriter(file);
		// XmlDataSetWriter xmlWriter = new XmlDataSetWriter(writer,"GB2312");
		// xmlWriter.write(partialDataSet);
		Writer writer = new FileWriter(current_dir);
		XmlDataSetWriter xmlDataSetWriter = new XmlDataSetWriter(writer);
		xmlDataSetWriter.write(partialDataSet);
		writer.flush();
		writer.close();
	}

	public IDataSet getDataSetByQuery(Connection iConnection, String[] sqls) throws DatabaseUnitException {
		QueryDataSet partialDataSet = new QueryDataSet((IDatabaseConnection) iConnection);
		for (String sql : sqls) {
			int start = sql.indexOf("from ") + 5;
			int end = sql.indexOf("where") - 1;
			String tableName = sql.substring(start, end);
			partialDataSet.addTable(tableName, sql);
		}
		return partialDataSet;
	}

	public IDataSet getDataSetFromCSV(String testFolder) throws IOException, DatabaseUnitException {
		String actual_data =  getTestClassDataFolder(testFolder);
		// 加载测试数据
		CsvURLDataSet csvURLDataSet = new CsvURLDataSet(new URL(actual_data));
		return csvURLDataSet;
	}

	public void compareDataSet(IDataSet expectDataSet, IDataSet actualDataSet, Map<String, String[]> ignoreColumsMap)
			throws DatabaseUnitException {
		for (String name : expectDataSet.getTableNames()) {
			ITable expectDataSetTable = expectDataSet.getTable(name);
			ITable actualDataSetTable = actualDataSet.getTable(name);
			Assertion.assertEqualsIgnoreCols(expectDataSetTable, actualDataSetTable, ignoreColumsMap.get(name));
		}

	}

	/**
	 * 获取test class下的测试数据目录
	 * @param testFolder
	 * @return
	 */
	public  String getTestClassDataFolder(String testFolder){
		String path=this.getClass().getResource("/")+"test_data/"+testFolder+"/";
		return path;
	}

	/**
	 * 获取src下的测试数据目录
	 * @param testFolder
	 * @return
	 * @throws IOException
	 */
	public  String getTestDataFolder(String testFolder) throws IOException {
		File directory = new File("");
		String current_dir= directory.getCanonicalPath() + "/src/test/resources/test_data/"+ testFolder;
		return current_dir;
	}
}
