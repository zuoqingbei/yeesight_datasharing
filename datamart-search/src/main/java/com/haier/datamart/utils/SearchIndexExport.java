package com.haier.datamart.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

import com.haier.datamart.entity.SearchIndex;



public class SearchIndexExport extends AbstractExcelExportTemplate {
	
private List<SearchIndex> list = new ArrayList<SearchIndex>();
	
	public SearchIndexExport(List<SearchIndex> list) {
		super();
		this.list = list;
	}

	@Override
	public String[] getSheetNames() {
		return new String[] { "指标详情" };
	}

	@Override
	public String[][] getTitles() {
		return new String[][] { 
				{"指标名称","指标编码","指标逻辑","展示表","平台","定义","指标类型","指标分类","计算公式","工作流","调度","取数逻辑","取数时间","取数频次","取数时长","取数方式","业务员接口人","业务员接口人工号","业务接口人邮箱","技术接口人","技术接口人工号","技术接口人邮箱","接口提供者","指标准确性标准","数据状态","标签","备注"},
		};
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

		for (int i = 0; i < list.size(); i++) {
			int index = 0;
			Row row1 = sheet.createRow(i + startIndex );
			Cell cell1 = row1.createCell((short) 1);  
	        cell1.setCellStyle(bodyLeftStyle);
			row1.setHeight((short) 300);
			SearchIndex searchIndex = list.get(i);
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
