package com.haier.datamart.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.haier.datamart.entity.Dict;
import com.haier.datamart.entity.SearchIndex;
import com.haier.datamart.entity.SearchReports;




public class SysIndexReportExport extends SysExcelExportTemplate {
	
    private Map<String, Object> map=new HashMap<String, Object>();
    private String[] sheetNames;
   
	
	public SysIndexReportExport(Map<String, Object> list, String[] sheetNames) {
		super();
		this.map = list;
		this.sheetNames=sheetNames;
		
	}

	@Override
	public String[] getCaptions() {
		return null;
	}

	@Override
	protected void buildBody(int sheetIndex) {
		
		bodyRowStyle=crateBodyCellStyle();
		bodyRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		CellStyle bodyLeftStyle=crateBodyCellStyle();
		CellStyle bodyRightStyle=crateBodyCellStyle();
		bodyRightStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		bodyLeftStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		bodyRightStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		
		CellStyle bodyCenterStyle=crateBodyCellStyle();
		bodyCenterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 设置为文本格式，防止身份证号变成科学计数法  
        DataFormat format = workbook.createDataFormat();  
        bodyLeftStyle.setDataFormat(format.getFormat("@"));  
		
		Sheet sheet = getSheet(sheetIndex);
		int startIndex = this.getBodyStartIndex(sheetIndex);
//		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd"); //格式化当前系统日期
        //设置sheet页内容
	
		if (sheetIndex==0) {
        List<SearchReports> dict=  (List<SearchReports>) map.get("sys");
        for (int i = 0; i < dict.size(); i++) {
			int index = 0;
			Row row1 = sheet.createRow(i + startIndex );
			Cell cell1 = row1.createCell((short) 1);  
	        cell1.setCellStyle(bodyLeftStyle);
			row1.setHeight((short) 300);
			SearchReports dict2 = dict.get(i);
			createStyledCell(row1, index++, dict2.getRemarks()==null? "":dict2.getRemarks().toString(),bodyRowStyle);
			createStyledCell(row1, index++, dict2.getTags()==null? "":dict2.getTags().toString(),bodyRowStyle);
			createStyledCell(row1, index++, dict2.getName()==null? "":dict2.getName().toString(),bodyRowStyle);
			createStyledCell(row1, index++, dict2.getViewNum(),bodyRowStyle);
			}
         }else {
         List<SearchIndex> indexs= 	(List<SearchIndex>) map.get(sheetNames[sheetIndex]);
         for (int i = 0; i < indexs.size(); i++) {
 			int index = 0;
 			Row row1 = sheet.createRow(i + startIndex );
 			Cell cell1 = row1.createCell((short) 1);  
 	        cell1.setCellStyle(bodyLeftStyle);
 			row1.setHeight((short) 300);
 			SearchIndex searchIndex = indexs.get(i);
 			createStyledCell(row1, index++, searchIndex.getName()==null? "":searchIndex.getName().toString(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getCode()==null? "":searchIndex.getCode().toString(),bodyLeftStyle);
 			createStyledCell(row1, index++, searchIndex.getDescs()==null? "":searchIndex.getDescs().toString(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getShowTable()==null? "":searchIndex.getShowTable().toString(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getPlat()==null? "":searchIndex.getPlat().toString(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getDingyi()==null? "":searchIndex.getDingyi().toString(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getIndexType()==null? "":searchIndex.getIndexType().toString(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getIndexFenlei()==null? "":searchIndex.getIndexFenlei().toString(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getExpression()==null? "":searchIndex.getExpression().toString(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getWorkflow()==null? "":searchIndex.getWorkflow(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getCoordinator()==null? "":searchIndex.getCoordinator(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getGetDataMagic()==null? "":searchIndex.getGetDataMagic(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getGetDataTime()==null? "":searchIndex.getGetDataTime(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getGetDataQuart()==null? "":searchIndex.getGetDataQuart(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getTimeLong()==null? "":searchIndex.getTimeLong(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getGetDataType()==null? "":searchIndex.getGetDataType(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getMaskInterfaceUser()==null? "":searchIndex.getMaskInterfaceUser(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getMaskInterfaceUserWorknum()==null? "":searchIndex.getMaskInterfaceUserWorknum(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getMaskInterfaceUserEmail()==null? "":searchIndex.getMaskInterfaceUserEmail(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getItInterfaceUser()==null? "":searchIndex.getItInterfaceUser(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getItInterfaceUserWorknum()==null? "":searchIndex.getItInterfaceUserWorknum(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getItInterfaceUserEmail()==null? "":searchIndex.getItInterfaceUserEmail(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getOfferUser()==null? "":searchIndex.getOfferUser(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getAccuracyStandard()==null? "":searchIndex.getAccuracyStandard(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getDataStatus()==null? "":searchIndex.getDataStatus(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getTags()==null? "":searchIndex.getTags(),bodyRowStyle);
 			createStyledCell(row1, index++, searchIndex.getRemarks()==null? "":searchIndex.getRemarks(),bodyRowStyle);
 		}
         }
      /*   if (sheetIndex%2==0&&sheetIndex!=0) {
		List<SearchReports> report=	(List<SearchReports>) map.get(sheetNames[sheetIndex]);
		for (int i = 0; i < report.size(); i++) {
			int index = 0;
			Row row1 = sheet.createRow(i + startIndex );
			Cell cell1 = row1.createCell((short) 1);  
	        cell1.setCellStyle(bodyLeftStyle);
			row1.setHeight((short) 300);
			SearchReports searchReport = report.get(i);
			createStyledCell(row1, index++, searchReport.getSystemName()==null? "":searchReport.getSystemName().toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchReport.getLink()==null? "":searchReport.getLink().toString(),bodyLeftStyle);
			createStyledCell(row1, index++, searchReport.getScreenUrl()==null? "":searchReport.getScreenUrl().toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchReport.getName()==null? "":searchReport.getName().toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchReport.getPath()==null? "":searchReport.getPath().toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchReport.getUrl()==null? "":searchReport.getUrl().toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchReport.getTags()==null? "":searchReport.getTags().toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchReport.getRemarks()==null? "":searchReport.getRemarks().toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchReport.getNetType()==null? "":searchReport.getNetType().toString(),bodyRowStyle);
			createStyledCell(row1, index++, searchReport.getDescs()==null? "":searchReport.getDescs(),bodyRowStyle);
		}
         }
		*/
		sheet.setColumnWidth(0, 9000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 2800);
		sheet.setColumnWidth(3, 2800);
		sheet.setColumnWidth(4, 2800);
		sheet.setColumnWidth(5, 2800);
		sheet.setColumnWidth(6, 2800);
		sheet.setColumnWidth(7, 2800);
		sheet.setColumnWidth(8, 2800);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 4000);
	
	}

	


	

}
