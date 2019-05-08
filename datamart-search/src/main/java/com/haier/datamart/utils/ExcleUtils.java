package com.haier.datamart.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class ExcleUtils {

	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}
	/**
	 * 
	 * @time   2018年12月11日 下午2:22:42
	 * @author zuoqb
	 * @todo   读取Excel中指定范围的数据
	 * @param  @param wb
	 * @param  @param sheetIndex
	 * @param  @param dataRange 格式 D7:F8
	 * @return_type   List<String>
	 */
	public static List<String> getExcelValuesByRange(Workbook wb,int sheetIndex,String dataRange){
		int allSheets=wb.getNumberOfSheets();//sheet页总页数
		List<String> columnData=new ArrayList<String>();//存全部数据
		if(StringUtils.isBlank(dataRange)||allSheets<sheetIndex){
			return columnData;
		};
		Sheet sheet=wb.getSheetAt(sheetIndex-1);
		String[] settings=dataRange.split(":");
		if(settings.length==1){
			//只有一个配置
			String startSetting=settings[0];
			Map<String, Integer> map=dealDataRange(startSetting);
			columnData.add(getCellDataByPosition(sheet,map.get("column"), map.get("rowNum")));
		}else{
			String startSetting=settings[0];
			Map<String, Integer> startMap=dealDataRange(startSetting);
			String endSetting=settings[1];
			Map<String, Integer> endMap=dealDataRange(endSetting);
			int startRow=startMap.get("rowNum");
			int endRow=endMap.get("rowNum");
			int temp=0;
			if(startRow>endRow){
				temp=startRow;
				startRow=endRow;
				endRow=temp;
			}
			int startColumn=startMap.get("column");
			int endColumn=endMap.get("column");
			if(startColumn>endColumn){
				temp=startColumn;
				startColumn=endColumn;
				endColumn=temp;
			}
			for(int row=startRow;row<=endRow;row++){
				for(int column=startColumn;column<=endColumn;column++){
					columnData.add(getCellDataByPosition(sheet, column, row));
				}
			}
		}
		return columnData;
	}

	public static Map<String,Integer> dealDataRange(String setting){
		Map<String, Integer> map=new HashMap<String,Integer>();
		String columnString=setting.replaceAll("\\d+","");
		String rowString=setting.replaceAll(columnString, "");
		int columnNum=excelColStrToNum(columnString, columnString.length())-1;
		int rowNum=Integer.valueOf(rowString)-1;
		map.put("column", columnNum);
		map.put("rowNum", rowNum);
		return map;
	}
	/**
	 * @time   2018年12月11日 下午3:07:09
	 * @author zuoqb
	 * @todo   TODO
	 * @param  @param sheet
	 * @param  @param columnNum
	 * @param  @param rowNum
	 * @return_type   void
	 */
	public static String getCellDataByPosition(Sheet sheet, Integer columnNum,
			Integer rowNum) {
		Row row=sheet.getRow(rowNum);
		if(row==null){
			row=sheet.createRow(rowNum);
		};
		Cell cell=row.getCell(columnNum);
		if(cell==null){
			cell=row.createCell(columnNum);
		}
		cell.setCellType(Cell.CELL_TYPE_STRING);
		return cell.getStringCellValue();
	}

	/**
	 * 验证EXCEL文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean validateExcel(String filePath) {
		if (filePath == null
				|| !(isExcel2003(filePath) || isExcel2007(filePath))) {
			return false;
		}
		return true;
	}

	public static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (Exception e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}
	  /**
     * Excel column index begin 1
     * @param colStr
     * @param length
     * @return
     */
    public static int excelColStrToNum(String colStr, int length) {
        int num = 0;
        int result = 0;
        for(int i = 0; i < length; i++) {
            char ch = colStr.toUpperCase().charAt(length - i - 1);
            num = (int)(ch - 'A' + 1) ;
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }

    /**
     * Excel column index begin 1
     * @param columnIndex
     * @return
     */
    public static String excelColIndexToStr(int columnIndex) {
        if (columnIndex <= 0) {
            return null;
        }
        String columnStr = "";
        columnIndex--;
        do {
            if (columnStr.length() > 0) {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }
    public static void main(String[] args) {
        String colstr = "f";
        int colIndex = excelColStrToNum(colstr, colstr.length());
        System.out.println("'" + colstr + "' column index of " + colIndex);

        
    }

}
