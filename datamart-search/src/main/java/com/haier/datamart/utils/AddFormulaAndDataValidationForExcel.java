package com.haier.datamart.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint.ValidationType;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.haier.datamart.utils.ExportExcel;

/**
 * 为Excel增加公式列
 * @author LeiZG 2018/7/5
 */
public class AddFormulaAndDataValidationForExcel {

	private static final int MAX_ROW = 30000;
	private static final int MAX_COL = 256;
	
	//字符串映射验证类型
	public static final Map<String, Integer> str_ValidationType 
							= new HashMap<String, Integer>();
	static{
		str_ValidationType.put("限定时刻", ValidationType.TIME);
		str_ValidationType.put("限定日期", ValidationType.DATE);
		str_ValidationType.put("限定小数", ValidationType.DECIMAL);
		str_ValidationType.put("限定整数", ValidationType.INTEGER);
		str_ValidationType.put("限定文本长度", ValidationType.TEXT_LENGTH);
	}
	public AddFormulaAndDataValidationForExcel() {
	}
	/**
	 * 为excel设置列表约束
	 * @param workbook
	 * @param explicitListValues
	 * @param biginCol
	 */
	public static void goAtValidationList(HSSFWorkbook workbook,
					String[] explicitListValues,Integer biginCol){//下拉选设置数据有效性
		CellRangeAddressList addressList = new CellRangeAddressList(
				1,//beginRow
				MAX_ROW-1,//endRow
				biginCol,//biginCol
				biginCol);//endCol
		DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(explicitListValues);
		DataValidation validation = new HSSFDataValidation(addressList, dvConstraint);
		//validation.setSuppressDropDownArrow(true);
		//validation.createErrorBox("错误提示", "限定下拉:("+explicitListValues+")");
		validation.setShowErrorBox(true);
		workbook.getSheetAt(0).addValidationData(validation);
	}
	/**
	 * 为excel设置类型约束
	 * @param workbook
	 * @param parameters
	 * @param biginCol
	 * @throws Exception 
	 */
	public static void  goAtValidationType(HSSFWorkbook workbook,
			String[] parameters,int biginCol) throws Exception{
		/*System.out.println("biginCol:"+biginCol);
		System.out.println("parameters[1]:"+parameters[1]);
		System.out.println("parameters[2]:"+parameters[2]);*/
		// 创建一个规则
			DVConstraint constraint;
			if(str_ValidationType.get(parameters[0])==ValidationType.DATE){
				 constraint 
				 		= DVConstraint.createDateConstraint(
						DVConstraint.OperatorType.BETWEEN,
						parameters[1],
						parameters[2],
						"yyyy/MM/dd");
			}else if(str_ValidationType.get(parameters[0])==ValidationType.TIME){
				 constraint 
		 		 		= DVConstraint.createTimeConstraint
		 		 		(DVConstraint.OperatorType.BETWEEN,
		 		 		parameters[1],
		 		 		parameters[2]);
			}else{
				 constraint 
				 			= DVConstraint.createNumericConstraint
							(str_ValidationType.get(parameters[0]),
							DVConstraint.OperatorType.BETWEEN,
							parameters[1], 
							parameters[2]);
			}
			// 设定生效区域 参数为 beginRow, endRow, beginCol, endCol
			
			CellRangeAddressList regions = new CellRangeAddressList(
					1, 
					MAX_ROW-1, 
					biginCol, 
					biginCol);
			// 创建规则对象
			HSSFDataValidation validation = new HSSFDataValidation(regions, constraint);
			//validation.setSuppressDropDownArrow(true);
			validation.createErrorBox("错误提示", parameters[0]+":最小-"+parameters[1]+"最大-"+parameters[2]);
			validation.setShowErrorBox(true);
			workbook.getSheetAt(0).addValidationData(validation);
			System.out.println("validation:"+validation);
		
		
	}

	/*public static void goAtFormula
			(HSSFWorkbook workbook,ExcelFormular excelFormular){//增加计算公式
		
		String columnName = excelFormular.getColumnName();
		String[] parameter = excelFormular.getParameters();
		String functionName = excelFormular.getFunctionName();
		String operator = excelFormular.getOperater();
		
		if(workbook!=null){
			ExportExcel ex = new ExportExcel();//引用该类定义好的样式
            HSSFCellStyle columnTopStyle = ex.getColumnTopStyle(workbook);//列头样式
            HSSFCellStyle cellStyle  =	ex.getStyle(workbook);//普通单元格样式
			HSSFSheet firstSheet = workbook.getSheetAt(0);
			//firstSheet.setForceFormulaRecalculation(true);//设置自动计算
			HSSFRow firstRow = firstSheet.getRow(0);
			扩展列头
			HSSFCell increasedOne = firstRow.createCell((int)(firstRow.getLastCellNum()));
			increasedOne.setCellType(HSSFCell.CELL_TYPE_STRING); //设置列头单元格的数据类型
	        HSSFRichTextString text = new HSSFRichTextString(columnName);
	        increasedOne.setCellValue(text); //设置列头单元格的值
	        increasedOne.setCellStyle(columnTopStyle);
            为列设置计算公式
            HSSFRow currentRow;
            HSSFCell lastCell;
            int lastCellIndex = firstSheet.getRow(0).getLastCellNum()-1;//第一行最后一格下标
            System.out.println("第一行最后一格下标:"+lastCellIndex);
            String formula = "";
            
            for(int i=0;i<parameter .length;i++){
                	if(i<parameter .length-1){
                		formula += parameter[i]+"$"+operator;
                	}else{
                		formula += parameter[i]+"$";
                	}
                }
            formula = str_functionNameMapping
            				.get(functionName)
            					.toUpperCase()+"("+formula+")";
            System.out.println("公式:"+formula);
            for(int i=1;i<MAX_ROW;i++){
            	
            	if (firstSheet.getRow(i)==null) {
                    currentRow = firstSheet.createRow(i);
                } 
                currentRow = firstSheet.getRow(i);
            	lastCell = currentRow.createCell(lastCellIndex);
            	lastCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
            	lastCell.setCellStyle(cellStyle);
            	lastCell.setCellFormula(formula.replace("$", (i+1)+""));//设置函数
                	
            }
            System.out.println("输出函数:"+formula.replace("$", 1+""));
          }
	}*/
	
	
	
	/**
	 * 公式预定义格式:验证**(exprs1,exprs2)
	 * @param excelGs
	 * @return 返回适应于goAtValidationType参数的String[]
	 *  strs[0] = type;
		strs[1] = exprs1;
		strs[2] = exprs2;
	 */
	public static String[] parseExcelConstraint(String excelGs){
		/*
		 * 预定义格式:验证**(exprs1,exprs2)
		 */
		int a = excelGs.indexOf("(");
		
		String str = excelGs.substring(0,a);
		
		String str2 = excelGs.substring(a+1,excelGs.length()-1);
		String[] str3 = str2.split(",");
		String[] strs = new String[3];
		strs[0] = str;
		strs[1] = str3[0];
		strs[2] = str3[1];
		/*for (String string : strs) {
			System.out.println("66666"+string);
		}*/
		return strs;
	}
	
	/*解析被[]转义过的字符串*/
	public static String[] getIdFroFormula(String excelGs) throws Exception {
		
		String[] idParameters = null;
		System.out.println(excelGs);
		if (excelGs.contains("[")) {//若含有转义符,则进行解析
			List<String> parametersList = new ArrayList<String>();
			String root = excelGs;
			int endLastIndex = root.lastIndexOf("]");
			int beginIndex = -1;
			int endIndex = -1;
			do {
				beginIndex = excelGs.indexOf("[");//首次 下标
				endIndex = excelGs.indexOf("]");//结束下标
				if(beginIndex==-1||endIndex==-1){
					break;
				}
				idParameters = excelGs.substring(beginIndex+1, endIndex).split("\\D");
				for (String string : idParameters) {
					parametersList.add(string);
				}
				excelGs = excelGs.substring(endIndex + 1);
			} while (endIndex != endLastIndex);
			idParameters = parametersList.toArray(new String[parametersList.size()]);
			/*for (String string : result) {
				System.out.println(string);
			}*/
		}
		return idParameters;
	}
	/**
	 * 
	 * 
	 * @param excelGs 用户传入的公式字符串
	 * @return 返回适值应于goAtValidationList方法的参数
	 */
	public static String[] parseExcelList (String excelGs){
		//预定义格式: 验证列表(中国,美国,英国...)
		String str = excelGs.substring(5,excelGs.length()-1);
		String[] strs = str.split(",");
		return strs;
	}
	/**
	 * 解析excelFormular
	 * @param maping
	 * @param excelGs
	 * @param maping 
	 * @return 解析完毕的excelFormular
	 */
	public static String parseExcelFormular(String excelGs, Map<Integer, Integer> maping){
		String root = excelGs;
		String prifix = root.split("\\[")[0];
		String[] helpFindSuff = root.split("\\]");
		String suffixStr = helpFindSuff[helpFindSuff.length - 1];
		int endLastIndex = root.lastIndexOf("]");
		int beginIndex = -1;
		int endIndex = -1;
		String formula = "";
		String currentStr;
		String afterStr = "";
		String[] idParameters;
		int jueduiIndex1 = 0;
		int jueduiIndex2 = 0;
		do {
			beginIndex = excelGs.indexOf("[");// 首次 下标
			if (endIndex != -1) {
				jueduiIndex1 = beginIndex + root.length() - excelGs.length();
				if (jueduiIndex1 < endLastIndex) {
					// System.out.println("绝对下标"+jueduiIndex1);
					jueduiIndex2 = root.length() - excelGs.length();
					afterStr = root.substring(jueduiIndex2, jueduiIndex1);
					System.out.println(afterStr);
				}

			}
			endIndex = excelGs.indexOf("]");// 结束下标
			if (beginIndex == -1 || endIndex == -1) {
				break;
			}
			idParameters = excelGs.substring(beginIndex + 1, endIndex).split("\\D");
			currentStr = excelGs.substring(beginIndex, endIndex + 1);
			
			String[] currentStrs = currentStr.trim().split("\\d*");
			System.out.println(currentStrs.length);
			for (String string : currentStrs) {
				System.out.println("::"+string);
			}
			currentStr = "";
			
			for (int i=0;i<idParameters.length;i++) {
				Integer realOrder = maping.get(Integer.parseInt(idParameters[i]));//真实顺序
				String a = "";
				if(realOrder!=null){
					 a = IdExcelOrderMaping.ID_EXCEL_ORDERMAPING.get(realOrder+"") + "$";
				}else{
					 a = IdExcelOrderMaping.ID_EXCEL_ORDERMAPING.get(idParameters[i]+"") + "$";
				}
				
              	currentStr = currentStr+currentStrs[(2*i)]+a;
			}
			currentStr = currentStr+"]";
			formula += afterStr + currentStr;
			excelGs = excelGs.substring(endIndex + 1);
		} while (endIndex != -1);
		return formula = (prifix + formula + suffixStr).replace("[", "").replace("]", "");
	}
	/**
	 * 根据参数为Excel添加对应的校验或者公式
	 * @param workbook
	 * @param map
	 */
	public static void goAt(HSSFWorkbook workbook,
						Map<Integer, String> map,Map<Integer, Integer> maping,Integer headerNum,Integer maxRows) throws Exception{
		HSSFSheet firstSheet = workbook.getSheetAt(0);
		firstSheet.setForceFormulaRecalculation(true);//设置自动计算
		ExportExcel ex = new ExportExcel();//引用该类定义好的样式
		HSSFCellStyle cellStyle  =	ex.getStyle(workbook);//普通单元格样式
		
		for (Integer key : map.keySet()) {
			String excelGs = map.get(key);
			if(excelGs!=null&&!"".equals(excelGs)){
				
				if(!excelGs.startsWith("限定")){//如果类型为设置公式
					/*为列设置计算公式*/
					HSSFRow currentRow;
					HSSFCell targetCell;
					/*解析*/
					/*System.out.println("maping.szie:"+maping.size());
					for (Integer string : maping.keySet()) {
	    				System.out.println("遍历maping("+string+":"+maping.get(string)+")");
	    			}*/
	    			String parsed = AddFormulaAndDataValidationForExcel
	    							.parseExcelFormular(excelGs.trim(),maping);
	    			
	    			System.out.println("解析后的公式:"+parsed);
	    			if(maxRows==0){
	    				maxRows=MAX_ROW;
	    			}
					for(int i=headerNum;i<maxRows+headerNum;i++){
		            	
		            	if (firstSheet.getRow(i)==null) {
		                    currentRow = firstSheet.createRow(i);
		                } 
		                currentRow = firstSheet.getRow(i);
		                targetCell = currentRow.createCell(key-1);//设置位置
		                targetCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);//设置类型
		                targetCell.setCellStyle(cellStyle);//设置样式
		                targetCell.setCellFormula(parsed.replace("$", (i+1)+""));//设置函数
		            }
				}else{
					if(excelGs.startsWith("限定列表")){
						String[] explicitListValues = AddFormulaAndDataValidationForExcel
													.parseExcelList(excelGs);
						AddFormulaAndDataValidationForExcel
							.goAtValidationList(workbook, explicitListValues, key-1);
					}else{
						String[] parameters = AddFormulaAndDataValidationForExcel
													.parseExcelConstraint(excelGs);
						AddFormulaAndDataValidationForExcel
							.goAtValidationType(workbook, parameters, key-1);
					}
				}
			}
			
		}
		
	}
	

	
	
}