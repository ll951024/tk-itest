package com.cy.test.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.cy.test.utils.AppConfigUtil;
import com.cy.test.utils.InitUtil;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class ExcelData {
    public Workbook workbook;
    public Sheet sheet;
    public Cell cell;
    int rows;
    int columns;
    public String fileName;
    public String caseName;
    public ArrayList<String> arrkey = new ArrayList<String>();
    String sourceFile;
    InitUtil initUtil;

    PropertyPlaceholderConfigurer propertyPlaceholderConfigurer ;

    /**
     * @param fileName   excel文件名
     * @param caseName   sheet名
     */
    public ExcelData(String fileName, String caseName) {
        super();
        this.fileName = fileName;
        this.caseName = caseName;
        initUtil=new InitUtil();
    }

    /**
     * 获得excel表中的数据
     */
    public Object[][] getExcelData() throws BiffException, IOException {
        workbook = Workbook.getWorkbook(this.getClass().getClassLoader().getResourceAsStream("test_cases/"+fileName+".xls"));
        sheet = workbook.getSheet(caseName);
        rows = sheet.getRows();
        columns = sheet.getColumns();
        // 为了返回值是Object[][],定义一个多行单列的二维数组
        HashMap<String, String>[][] arrmap = new HashMap[rows - 1][1];
        // 对数组中所有元素hashmap进行初始化
        if (rows > 1) {
            for (int i = 0; i < rows - 1; i++) {
                arrmap[i][0] = new HashMap<>();
            }
        } else {
            System.out.println("excel中没有数据");
        }

        // 获得首行的列名，作为hashmap的key值
        for (int c = 0; c < columns; c++) {
            String cellvalue = sheet.getCell(c, 0).getContents();
            arrkey.add(cellvalue);
        }
        // 遍历所有的单元格的值添加到hashmap中
        for (int r = 1; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                String cellvalue = sheet.getCell(c, r).getContents();
                if(caseName.startsWith("JR")||caseName.startsWith("JT")||caseName.startsWith("JB")) {
                    //修改研判接口期待时间
                    if (cellvalue.contains(InitUtil.oldDay30)) {
                        cellvalue = cellvalue.replace(InitUtil.oldDay30, initUtil.newDay30);
                    }
                    if (cellvalue.contains(InitUtil.oldDay29)) {
                        cellvalue = cellvalue.replace(InitUtil.oldDay29, initUtil.newDay29);
                    }
                    if (cellvalue.contains(InitUtil.oldDay28)) {
                        cellvalue = cellvalue.replace(InitUtil.oldDay28, initUtil.newDay28);
                    }
                    if (cellvalue.contains(InitUtil.oldDay27)) {
                        cellvalue = cellvalue.replace(InitUtil.oldDay27, initUtil.newDay27);
                    }
                }
                if(cellvalue.contains("$")){
                    int startIndex=cellvalue.indexOf("${");
                    String subStr=cellvalue.substring(startIndex);
                    int endIndex=subStr.indexOf("}");
                    String variable=subStr.substring(2,endIndex);
                    String result=AppConfigUtil.getContextProperty(variable.trim()).toString();
                    cellvalue = cellvalue.replace("${"+variable+"}",result);
                }
                arrmap[r - 1][0].put(arrkey.get(c), cellvalue);
            }
        }
        return arrmap;
    }

    /**
     * 获得excel文件的路径
     * @return
     * @throws IOException
     */
    public String getPath() {
        File directory = new File(".");
        try {
			sourceFile = directory.getCanonicalPath() + "//test-classes//test_cases//" + fileName + ".xls";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return sourceFile;
    }

}