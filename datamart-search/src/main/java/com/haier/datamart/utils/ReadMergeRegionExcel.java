package com.haier.datamart.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import com.haier.datamart.entity.EnteringTableSettingHeader;
/**
 * 
 * @author zuoqb
 *解析Excel表头单元格
 */
public class ReadMergeRegionExcel {
	  	public static void main(String[] args) {
	  		//readExcelToObj("D:\\doc\\2.xls");
	  		List<EnteringTableSettingHeader> headers=readExcelToObj("D:\\doc\\monstsrmx.xlsx");
	  		
		}
	      
	    /**  
	    * 读取excel数据  
	    * @param  path  
	    */  
	    public static List<EnteringTableSettingHeader> readExcelToObj(String path) {  
	      
	        Workbook wb = null;  
	        try {  
	            wb = WorkbookFactory.create(new File(path));  
	            return readExcel(wb, 0, 0, 0);  
	        } catch (InvalidFormatException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }
	        return null;
	    }  
	      
	    /**  
	    * 读取excel文件  
	    * @param  wb   
	    * @param sheetIndex sheet页下标：从0开始  
	    * @param startReadLine 开始读取的行:从0开始  
	    * @param tailLine 去除最后读取的行  
	    */  
	    public static List<EnteringTableSettingHeader> readExcel(Workbook wb,int sheetIndex, int startReadLine, int tailLine) {  
	        
	        Sheet sheet = wb.getSheetAt(sheetIndex); 
	        Row row = null;  
	        List<EnteringTableSettingHeader> headers=new ArrayList<EnteringTableSettingHeader>();  
	        for(int i=startReadLine; i<sheet.getLastRowNum()-tailLine+1; i++) {  
	            row = sheet.getRow(i); 
	            if(row!=null){
	            	int x=0;
	            	for(Cell c : row) {
	            		EnteringTableSettingHeader header=new EnteringTableSettingHeader();
	            		//判断是否具有合并单元格  
	            		CellStyle style=c.getCellStyle();
	            		header.setRowHeight(Integer.valueOf(c.getRow().getHeight()));
	            		header.setDataFormat(Integer.valueOf(style.getDataFormat()));
	            		header.setDataFormatString(style.getDataFormatString());
	            		header.setAlign(Integer.valueOf(style.getAlignment()));
	            		header.setVerticalAlignment(Integer.valueOf(style.getVerticalAlignment()));
	            		header.setRotation(Integer.valueOf(style.getRotation()));
	            		header.setIndention(Integer.valueOf(style.getIndention()));
	            		header.setBorderLeft(Integer.valueOf(style.getBorderLeft()));
	            		header.setBorderRight(Integer.valueOf(style.getBorderRight()));
	            		header.setBorderTop(Integer.valueOf(style.getBorderTop()));
	            		header.setBorderBottom(Integer.valueOf(style.getBorderBottom()));
	            		header.setBorderLeftColor(Integer.valueOf(style.getLeftBorderColor()));
	            		header.setBorderRightColor(Integer.valueOf(style.getRightBorderColor()));
	            		header.setBorderTopColor(Integer.valueOf(style.getTopBorderColor()));
	            		header.setBorderBottomColor(Integer.valueOf(style.getBottomBorderColor()));
	            		header.setFillPattern(Integer.valueOf(style.getFillPattern()));
	            		header.setFillBackgroundColor(Integer.valueOf(style.getFillBackgroundColor()));
	            		header.setFillForegroundColor(Integer.valueOf(style.getFillForegroundColor()));
	            		header.setWidth( sheet.getColumnWidth(x));
	            		x++;
	            		//字体
	            		Font font = wb.getFontAt(c.getCellStyle().getFontIndex());
	            		header.setFontColor(Integer.valueOf(font.getColor()));
	            		header.setFontName(font.getFontName());
	            		header.setFontHeight(Integer.valueOf(font.getFontHeight()));
	            		boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());  
	            		String cellValue="";
	            		if(isMerge) {  
	            			cellValue= getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex()); 
	            			Map<String,Integer> position=getMergedRegionPosition(sheet, i, c.getColumnIndex());
		            		header.setRowFrom(position.get("rowFrom"));
		            		header.setRowTo(position.get("rowTo"));
		            		header.setColumnFrom(position.get("columnFrom"));
		            		header.setColumnTo(position.get("columnTo"));
	            		}else {
	            			cellValue=getCellValue(c);
	            			header.setRowFrom(c.getRowIndex());
		            		header.setRowTo(c.getRowIndex());
		            		header.setColumnFrom(c.getColumnIndex());
		            		header.setColumnTo(c.getColumnIndex());
	            		}
	            		System.out.println(cellValue);
	            		header.setRownum(i);//当前cell所在行序号
	            		header.setOrderNo(c.getColumnIndex());//每一个cell顺序
	            		header.setName(cellValue);//当前cell值
	            		//获取单元格下标
	            		header.setId(UUIDUtils.getUuid());
	            		headers.add(header);
	            	}  
	            }
	              
	        }
	        return headers;
	      
	    }  
	    /**   
	    * 获取合并单元格的值   
	    * @param sheet   
	    * @param row   
	    * @param column   
	    * @return   
	    */    
	    public static String getMergedRegionValue(Sheet sheet ,int row , int column){    
	        
	        int sheetMergeCount = sheet.getNumMergedRegions();    
	            
	        for(int i = 0 ; i < sheetMergeCount ; i++){    
	            CellRangeAddress ca = sheet.getMergedRegion(i);    
	            int firstColumn = ca.getFirstColumn();    
	            int lastColumn = ca.getLastColumn();    
	            int firstRow = ca.getFirstRow();    
	            int lastRow = ca.getLastRow();    
	                
	            if(row >= firstRow && row <= lastRow){    
	                    
	                if(column >= firstColumn && column <= lastColumn){    
	                    Row fRow = sheet.getRow(firstRow);    
	                    Cell fCell = fRow.getCell(firstColumn);    
	                    return getCellValue(fCell) ;    
	                }    
	            }    
	        }    
	            
	        return null ;    
	    }    
	      
	    /**  
	    * 判断合并了行  
	    * @param sheet  
	    * @param row  
	    * @param column  
	    * @return  
	    */  
	    public static boolean isMergedRow(Sheet sheet,int row ,int column) {  
	      
	      int sheetMergeCount = sheet.getNumMergedRegions();  
	      for (int i = 0; i < sheetMergeCount; i++) {  
	        CellRangeAddress range = sheet.getMergedRegion(i);  
	        int firstColumn = range.getFirstColumn();  
	        int lastColumn = range.getLastColumn();  
	        int firstRow = range.getFirstRow();  
	        int lastRow = range.getLastRow();  
	        if(row == firstRow && row == lastRow){  
	            if(column >= firstColumn && column <= lastColumn){  
	                return true;  
	            }  
	        }  
	      }  
	      return false;  
	    }  
	      
	    /**  
	    * 判断指定的单元格是否是合并单元格  
	    * @param sheet   
	    * @param row 行下标  
	    * @param column 列下标  
	    * @return  
	    */  
	    public static boolean isMergedRegion(Sheet sheet,int row ,int column) {  
	      
	      int sheetMergeCount = sheet.getNumMergedRegions();  
	      for (int i = 0; i < sheetMergeCount; i++) {  
	        
	        CellRangeAddress range = sheet.getMergedRegion(i);  
	        int firstColumn = range.getFirstColumn();  
	        int lastColumn = range.getLastColumn();  
	        int firstRow = range.getFirstRow();  
	        int lastRow = range.getLastRow();  
	        if(row >= firstRow && row <= lastRow){  
	            if(column >= firstColumn && column <= lastColumn){  
	            	//System.out.println(firstRow+","+firstColumn+","+lastRow+","+lastColumn+"\n");
	                return true;  
	            }  
	        } 
	      }  
	      return false;  
	    }  
	    /**  
	    * 获取合并单元格 坐标信息
	    * @param sheet   
	    * @param row 行下标  
	    * @param column 列下标  
	    * @return  
	    */  
	    public static  Map<String,Integer> getMergedRegionPosition(Sheet sheet,int row ,int column) {  
	      Map<String,Integer> position=new HashMap<String, Integer>();
	      
	      
	      int sheetMergeCount = sheet.getNumMergedRegions();  
	      for (int i = 0; i < sheetMergeCount; i++) {  
	        
	        CellRangeAddress range = sheet.getMergedRegion(i);  
	        int rowFrom = range.getFirstRow();  
	        int columnFrom = range.getFirstColumn();  
	        int rowTo = range.getLastRow();  
	        int columnTo = range.getLastColumn();  
	        if(row >= rowFrom && row <= rowTo){  
	            if(column >= columnFrom && column <= columnTo){
	            	position.put("rowFrom", rowFrom);
	            	position.put("columnFrom", columnFrom);
	            	position.put("rowTo", rowTo);
	            	position.put("columnTo", columnTo);
	            	System.out.println(rowFrom+","+columnFrom+","+rowTo+","+columnTo+"\n");
	                return position;  
	            }  
	        }
	      }  
	      return position;  
	    }    
	    /**  
	    * 判断sheet页中是否含有合并单元格   
	    * @param sheet   
	    * @return  
	    */  
	    public static boolean hasMerged(Sheet sheet) {  
	            return sheet.getNumMergedRegions() > 0 ? true : false;  
	    }  
	      
	    /**  
	    * 合并单元格  
	    * @param sheet   
	    * @param firstRow 开始行  
	    * @param lastRow 结束行  
	    * @param firstCol 开始列  
	    * @param lastCol 结束列  
	    */  
	    public static void mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {  
	        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));  
	    }  
	      
	    /**   
	    * 获取单元格的值   
	    * @param cell   
	    * @return   
	    */    
	    public static String getCellValue(Cell cell){    
	            
	        if(cell == null) return "";    
	            
	        if(cell.getCellType() == Cell.CELL_TYPE_STRING){    
	                
	            return cell.getStringCellValue();    
	                
	        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){    
	                
	            return String.valueOf(cell.getBooleanCellValue());    
	                
	        }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){    
	                
	            return cell.getCellFormula() ;    
	                
	        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){    
	                
	            return String.valueOf(cell.getNumericCellValue());    
	                
	        }    
	        return "";    
	    }    
}
