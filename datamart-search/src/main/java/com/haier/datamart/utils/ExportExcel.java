package com.haier.datamart.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.haier.datamart.entity.EnteringTableSettingDetail;
import com.haier.datamart.entity.EnteringTableSettingHeader;
import com.haier.datamart.entity.MCellStyle;


public class ExportExcel {

	// 显示的导出表的标题
	private String title;
	// 导出表的列名
	private String[] rowName;

	private List<List<String>> interfaceData=new ArrayList<List<String>>();

	HttpServletResponse response;
	List<EnteringTableSettingHeader> headers=new ArrayList<EnteringTableSettingHeader>();
	List<List<EnteringTableSettingHeader>> sortHeaders=new ArrayList<List<EnteringTableSettingHeader>>();
	List<EnteringTableSettingDetail> settingDetails=new ArrayList<EnteringTableSettingDetail>();
	/**
	 * 老版本:存放公式中包含的id参数定义位置与真实位置的映射关系
	 * 更新后:存放真实位置与真实位置的映射关系
	 */
	private Map<Integer,Integer> maping;
	 
	/**
	 * 用户传入的公式或者约束规则
	 */
	private Map<Integer, String> map;
	// 构造方法，传入要导出的数据
	public ExportExcel() {
		super();
	}


	public ExportExcel(String title, String[] rowName, List<List<String>> interfaceData, HttpServletResponse response,
			Map<Integer, Integer> maping, Map<Integer, String> map,List<EnteringTableSettingHeader> headers,List<EnteringTableSettingDetail> settingDetails) {
		super();
		this.title = title;
		this.rowName = rowName;
		this.interfaceData = interfaceData;
		this.response = response;
		this.maping = maping;
		this.map = map;
		this.headers=headers;
		this.settingDetails=settingDetails;
	}

	public void sortHeadersMethod(){
		List<List<EnteringTableSettingHeader>> result=new ArrayList<List<EnteringTableSettingHeader>>();
		Set<Integer> rowNums = new HashSet<Integer>();
		for(EnteringTableSettingHeader header:headers){
			rowNums.add(header.getRownum());
		}
		//安装所在行将header划分组
		Iterator<Integer> it = rowNums.iterator();
		while (it.hasNext()) {
			List<EnteringTableSettingHeader> rowColumns=new ArrayList<EnteringTableSettingHeader>();//当前行全部cell
			int rowIndex=it.next();
			for(EnteringTableSettingHeader header:headers){
				if(rowIndex==header.getRownum()){
					rowColumns.add(header);
				}
			}
			result.add(rowColumns);
		}
		this.sortHeaders=result;
	}
	/*
	 * 导出数据
	 */
	public void export() throws Exception {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet(); // 创建工作表
			int nullNum=0;
			for(List<String> s:interfaceData){
				if(s==null||s.size()==0){
					nullNum++;
				}
			};
			if(interfaceData.size()>0&&nullNum!=interfaceData.size()){
				//有引用外部数据，需要进行锁表
				sheet.protectSheet("1169");//设置锁定密码
			}
			// 产生表格标题行
			// HSSFRow rowm = sheet.createRow(0);
			// HSSFCell cellTiltle = rowm.createCell(0);

			// rowm.setHeight((short) (25 * 30)); //设置高度

			// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
			HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
			// HSSFCellStyle style = this.getStyle(workbook); //单元格样式对象

			// sheet.addMergedRegion(new CellRangeAddress(0, 1, 0,
			// (rowName.length-1)));
			// cellTiltle.setCellStyle(columnTopStyle);
			// cellTiltle.setCellValue(title);

			// 定义所需列数
			sortHeadersMethod();
			int startNum=sortHeaders.size();
			int columnNum = rowName.length;
			//设置单独表头
			//Region region = new Region((short)rowFrom,(short)columnFrom,(short)rowTo,(short)columnTo);//合并从第rowFrom行columnFrom列   
			/*Region region = new Region((short)0,(short)1,(short)3,(short)2);//合并从第rowFrom行columnFrom列
			sheet.addMergedRegion(region);// 到rowTo行columnTo的区域      
			//得到所有区域       
			sheet.getNumMergedRegions();*/
			List<HSSFCellStyle> topStyle=new ArrayList<HSSFCellStyle>();
			for(int x=0;x<sortHeaders.size();x++){
				HSSFRow headerRow = sheet.createRow(x);
				List<EnteringTableSettingHeader> mHeaders=sortHeaders.get(x);
				headerRow.setHeight(mHeaders.get(0).getRowHeight().shortValue());
				for (int n = 0; n < mHeaders.size(); n++) {
					HSSFCell cellRowName = headerRow.createCell(n); // 创建列头对应个数的单元格
					cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
					HSSFRichTextString text = new HSSFRichTextString(mHeaders.get(n).getName());
					cellRowName.setCellValue(text); // 设置列头单元格的值
					HSSFCellStyle style = this.getColumnTopStyle(workbook,mHeaders.get(n));
					if(x==startNum-1){
						topStyle.add(style);
					}
					cellRowName.setCellStyle(style); // 设置列头单元格样式
					sheet.setColumnWidth(n, mHeaders.get(n).getWidth());
				}
			}
			//设置补录配置数据表头
			if(startNum==0){
				HSSFRow rowRowName = sheet.createRow(startNum); // 在索引2的位置创建行(最顶端的行开始的第startNum行)
				rowRowName.setHeight((short) (25 * 30)); // 设置高度
				// 将补录配置列头设置到sheet的单元格中
				for (int n = 0; n < columnNum; n++) {
					HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
					cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
					HSSFRichTextString text = new HSSFRichTextString(rowName[n].trim());
					cellRowName.setCellValue(text); // 设置列头单元格的值
					//ll
					if(topStyle.size()>n){
						cellRowName.setCellStyle(topStyle.get(n));
					}else{
						cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
					}
				}
			}
			//进行单元格合并操作
			List<String> merges=new ArrayList<String>();
			for(int x=0;x<sortHeaders.size();x++){
				List<EnteringTableSettingHeader> mHeaders=sortHeaders.get(x);
				for (EnteringTableSettingHeader h:mHeaders) {
					if(h.getRowFrom()<h.getRowTo()||h.getColumnFrom()<h.getColumnTo()){
						//Region region = new Region((short)rowFrom,(short)columnFrom,(short)rowTo,(short)columnTo);//合并从第rowFrom行columnFrom列   
						//Region region = new Region(h.getRowFrom(),h.getColumnFrom().shortValue(),h.getRowTo(),h.getColumnTo().shortValue());//合并从第rowFrom行columnFrom列
						String merge=h.getRowFrom()+","+h.getColumnFrom().shortValue()+","+h.getRowTo()+","+h.getColumnTo().shortValue();
						if(merges.indexOf(merge)==-1){
							CellRangeAddress region = new CellRangeAddress(h.getRowFrom(),h.getRowTo(),h.getColumnFrom().shortValue(),h.getColumnTo().shortValue());//合并从第rowFrom行columnFrom列
							sheet.addMergedRegion(region);// 到rowTo行columnTo的区域     
							merges.add(merge);
						}
					}
				}
			}
			
			
			/*CellRangeAddress region = new CellRangeAddress(0,0,0,7);//合并从第rowFrom行columnFrom列
			sheet.addMergedRegion(region);*/
			// 将查询出的统一接口数据设置到sheet对应的单元格中
			int maxInterfaceRow=InterfaceDataUtils.getMaxRow(interfaceData);

			for (Integer key : map.keySet()) {
				System.out.println("3333:"+map.get(key));
			}
			for(int i=0;i<maxInterfaceRow;i++){
				HSSFRow row=null;
				if(startNum==0){
					row=sheet.createRow(startNum+i+1);
				}else{
					row=sheet.createRow(startNum+i);
				}
				row.setHeight((short) (25 * 20)); 
				//设置当前行中每一个格子数据
				for (int n = 0; n < columnNum; n++) {
					HSSFCell cellRowName = row.createCell(n);
					List<String> data=interfaceData.get(n);
					if(data.size()>i){
						//设置公式
						if(data.get(i).startsWith("=")){
							cellRowName.setCellType(HSSFCell.CELL_TYPE_FORMULA);//设置类型
							cellRowName.setCellFormula(data.get(i).replace("=", ""));//设置函数
						}else{
							//获取当前配置值
							HSSFRichTextString text = new HSSFRichTextString(data.get(i));
							cellRowName.setCellValue(text); // 设置列头单元格的值
						}
						
						HSSFCellStyle style=getDefaultStyle(workbook);
						//统一接口默认数据部分根据补录配置的is_lock字段放开锁死
						for(int x=0;x<settingDetails.size();x++){
							if(x==n){
								//设置独有样式
								if(StringUtils.isNotBlank(settingDetails.get(x).getColumnStyle())){
									//
									MCellStyle mCellStyle=JSONObject.parseObject(settingDetails.get(x).getColumnStyle(), MCellStyle.class);
									if(mCellStyle!=null)
										style=getColumnStyleBySetting(workbook, mCellStyle);
								}
								if("1".equals(settingDetails.get(x).getIsLock())){
									//是否锁死 0-锁死 1-不锁死
									style.setLocked(false);
								}
								break;
							}
						}
						cellRowName.setCellStyle(style);
						
					}else{
						HSSFCellStyle style=getDefaultStyle(workbook);
						//用户填写部分放开锁死
						style.setLocked(false);
						cellRowName.setCellStyle(style);
					}
				}
			}
			
			if(map!=null&&map.size()!=0){ 
				if(startNum==0){
					AddFormulaAndDataValidationForExcel.goAt(workbook, map,maping,startNum+1,maxInterfaceRow);
				}else{
					AddFormulaAndDataValidationForExcel.goAt(workbook, map,maping,startNum,maxInterfaceRow);
				}
			}
				
			//去掉率的公式
			/*for(int x=0;x<interfaceData.size();x++){
				for(int y=0;y<interfaceData.get(x).size();y++){
					if(StringUtils.isNotBlank(interfaceData.get(x).get(y))&&interfaceData.get(x).get(y).indexOf("率")!=-1){
						HSSFRow row=null;
						if(startNum==0){
							row=sheet.getRow(y+startNum+1);
						}else{
							row=sheet.getRow(startNum+y);
						}
						for(int n = 0; n < columnNum; n++){
							HSSFCell cellRowName = row.getCell(n);
							cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);//设置类型
							String v=cellRowName.getRichStringCellValue().getString();
							if("0".equals(v)){
								cellRowName.setCellValue("");
							}
						}
					}
				}
			}*/
			
			if(sortHeaders.size()==0){
				//重新定义columnNum
				columnNum = workbook.getSheetAt(0).getRow(0).getLastCellNum();
				// 让列宽随着导出的列长自动适应
				for (int colNum = 0; colNum < columnNum; colNum++) {
					sheet.autoSizeColumn(colNum);
					sheet.setColumnWidth(colNum, sheet.getColumnWidth(colNum)*2);
				}
			}
			if (workbook != null) {
					OutputStream output = response.getOutputStream();
					response.reset();
					response.setHeader("Content-disposition",
							"attachment; filename=" + new String(title.getBytes(), "iso-8859-1")
									+ new SimpleDateFormat("yyyyMMdd").format(new Date()).toString() + ".xls");
					response.setContentType("application/msexcel");
					workbook.write(output);
					output.close();
				
			}

		} 

	/*
	 * 列头单元格样式
	 */
	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// 设置单元格背景颜色
		style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		return style;

	}
	
	/*
	 * 列头单元格样式
	 */
	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook,EnteringTableSettingHeader header) {

		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeight(header.getFontHeight().shortValue());
		font.setColor(header.getFontColor().shortValue());
		// 设置字体名字
		font.setFontName(header.getFontName());
		
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(header.getAlign().shortValue());
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(header.getVerticalAlignment().shortValue());
		// 设置底边框;
		style.setBorderBottom(header.getBorderBottom().shortValue());
		// 设置底边框颜色;
		style.setBottomBorderColor(header.getBorderBottomColor().shortValue());
		// 设置左边框;
		style.setBorderLeft(header.getBorderLeft().shortValue());
		// 设置左边框颜色;
		style.setLeftBorderColor(header.getBorderLeftColor().shortValue());
		// 设置右边框;
		style.setBorderRight(header.getBorderRight().shortValue());
		// 设置右边框颜色;
		style.setRightBorderColor(header.getBorderBottomColor().shortValue());
		// 设置顶边框;
		style.setBorderTop(header.getBorderTop().shortValue());
		// 设置顶边框颜色;
		style.setTopBorderColor(header.getBorderTopColor().shortValue());
		

		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置单元格背景颜色
		if(header.getFillPattern().shortValue()==1){
			style.setFillForegroundColor(header.getFillForegroundColor().shortValue());
			style.setFillBackgroundColor(header.getFillBackgroundColor().shortValue());
		}
		style.setFillPattern(header.getFillPattern().shortValue());
		
		return style;

	}
	/*
	 * 根据补录配置生成统一接口默认数据的样式
	 */
	public HSSFCellStyle getColumnStyleBySetting(HSSFWorkbook workbook,MCellStyle header) {
		if(header==null){
			return getDefaultStyle(workbook);
		}
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		if(header.getFontHeight()!=null)
		font.setFontHeight(header.getFontHeight().shortValue());
		// 字体加粗
		if(header.getBoldweight()!=null){
			font.setBoldweight(header.getBoldweight().shortValue());
		}else{
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}
		if(header.getFontColor()!=null)
		font.setColor(header.getFontColor().shortValue());
		// 设置字体名字
		if(StringUtils.isNotBlank(header.getFontName()))
		font.setFontName(header.getFontName());
		
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置自动换行;
		style.setWrapText(true);
		// 设置水平对齐的样式为居中对齐;
		if(header.getAlign()!=null)
		style.setAlignment(header.getAlign().shortValue());
		// 设置垂直对齐的样式为居中对齐;
		if(header.getVerticalAlignment()!=null)
		style.setVerticalAlignment(header.getVerticalAlignment().shortValue());
		// 设置底边框;
		if(header.getBorderBottom()!=null)
		style.setBorderBottom(header.getBorderBottom().shortValue());
		// 设置底边框颜色;
		if(header.getBorderBottomColor()!=null)
		style.setBottomBorderColor(header.getBorderBottomColor().shortValue());
		// 设置左边框;
		if(header.getBorderLeft()!=null)
		style.setBorderLeft(header.getBorderLeft().shortValue());
		// 设置左边框颜色;
		if(header.getBorderLeftColor()!=null)
		style.setLeftBorderColor(header.getBorderLeftColor().shortValue());
		// 设置右边框;
		if(header.getBorderRight()!=null)
		style.setBorderRight(header.getBorderRight().shortValue());
		// 设置右边框颜色;
		if(header.getBorderBottomColor()!=null)
		style.setRightBorderColor(header.getBorderBottomColor().shortValue());
		// 设置顶边框;
		if(header.getBorderTop()!=null)
		style.setBorderTop(header.getBorderTop().shortValue());
		// 设置顶边框颜色;
		if(header.getBorderTopColor()!=null)
		style.setTopBorderColor(header.getBorderTopColor().shortValue());
		

		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置单元格背景颜色
		if(header.getFillForegroundColor()!=null)
		style.setFillForegroundColor(header.getFillForegroundColor().shortValue());
		if(header.getFillBackgroundColor()!=null)
		style.setFillBackgroundColor(header.getFillBackgroundColor().shortValue());
		if(header.getFillPattern()!=null)
		style.setFillPattern(header.getFillPattern().shortValue());
		
		return style;

	}
	/*
	 * 列数据信息单元格样式
	 */
	public HSSFCellStyle getDefaultStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("微软雅黑");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}
	
	/*
	 * 鍒楁暟鎹俊鎭崟鍏冩牸鏍峰紡
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		// font.setFontHeightInPoints((short)10);
		// 瀛椾綋鍔犵矖
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("Courier New");
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setFont(font);
		style.setWrapText(false);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;

	}
}
