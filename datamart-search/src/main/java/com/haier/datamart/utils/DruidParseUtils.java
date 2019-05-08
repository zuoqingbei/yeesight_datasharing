package com.haier.datamart.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.stat.TableStat.Column;
import com.alibaba.druid.stat.TableStat.Name;
import com.haier.datamart.entity.DataInterfaceTableRelative;
import com.haier.datamart.entity.DruidParseEntity;
/**
 * 
 * @author zuoqb
 *解析sql工具类
 */
public class DruidParseUtils {
	public static DruidParseEntity parseSqlByDruid(String sql) {
		DruidParseEntity parseEntity=new DruidParseEntity();
		SQLStatementParser parser = new MySqlStatementParser(sql);
		SQLStatement statement = parser.parseStatement();
		MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
		statement.accept(visitor);
		// 从visitor中拿出你所关注的信息
		List<String> tables = new ArrayList<String>();// 表
		Map<Name, TableStat> parseTables = visitor.getTables();
		for (Name table : parseTables.keySet()) {
			System.out.println("Key = " + table.getName());
			tables.add(table.getName());
		}
		Collection<Column> parseColumns = visitor.getColumns();
		Iterator<Column> it = parseColumns.iterator();
		List<Map<String, Object>> columns = new ArrayList<Map<String, Object>>();
		List<DataInterfaceTableRelative> relatives=new ArrayList<DataInterfaceTableRelative>();
		while (it.hasNext()) {
			Column column = it.next();
			Map<String, Object> map = new HashMap<String, Object>();
			DataInterfaceTableRelative relative=new DataInterfaceTableRelative();
			String tableName=column.getTable();
			if(StringUtils.isNotBlank(tableName)){
				String[] arr=tableName.split("\\.");
				if(arr.length>1){
					map.put("dbName", arr[0]);
					map.put("table", arr[1]);
					relative.setDbName(arr[0]);
					relative.setContentId(arr[1]);
				}else{
					map.put("dbName", "");
					map.put("table",arr[0]);
					relative.setDbName("");
					relative.setContentId(arr[0]);
				}
			}else{
				map.put("dbName", "");
				map.put("table", "");
				relative.setDbName("");
				relative.setContentId("");
			}
			
			map.put("fullName", column.getFullName());
			map.put("name", column.getName());
			map.put("dataType", column.getDataType()==null?"":column.getDataType());
			columns.add(map);
			relative.setContentDetail(column.getName());
			relatives.add(relative);
			System.out.print(column.getTable() + "," + column.getFullName()
					+ "," + column.getName() + "," + (column.getDataType()==null?"":column.getDataType())+"\n");
		};
		/*System.out.println(visitor.getColumns());// 字段
		System.out.println(visitor.getTables());// 表明
		System.out.println(visitor.getRelationships());// 关联
		System.out.println(visitor.getConditions());// 条件
*/		parseEntity.setSql(sql);
		parseEntity.setColumns(columns);
		parseEntity.setTables(tables);
		parseEntity.setRelatives(relatives);
		return parseEntity;
	}
	public static void main(String[] args) {
		 String testseletSql = "select a.*,b.name from tb a left join tbc b on b.id = a.p_id and a.pid = '1' ";
		 parseSqlByDruid(testseletSql);
	}

}
