package com.haier.datamart.utils;

import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by long on 2018/11/23.
 * 获取corn表达式执行时间
 *
 */
public class CornShowUtil {
    /**
     * <p>
     *    获取最近几次的更新时间
     * </p>
     * @param standryCorn 标准的cron表达式
     * @param startDate 开始时间
     * @param count  获取几次的次数
     * @return
     */
    public static List<Date> getRecent(String standryCorn, Date startDate,int count){
        String cronExpression = standryCorn;
        List<Date> list = new ArrayList<>();
        CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
        try {
            cronTriggerImpl.setCronExpression(cronExpression);
        } catch(ParseException e)
        {
            e.printStackTrace();
            throw new RuntimeException(standryCorn+"不是标准的corn表达式");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        List<Date> dates =  TriggerUtils.computeFireTimesBetween(cronTriggerImpl,null,startDate,calendar.getTime());;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int limit = 0;
        for (Date date : dates) {
            if(++limit >count){
                break;
            }
            list.add((date));
        }
        return list;
    } 
    
    public static void main(String[] args) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = null;
    	try {
			  date = sdf.parse("2019-01-31 18:00:01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	List<Date> list = getRecent("01 00 18 1/1 * ? *", date, 1);
    	for (Date date2 : list) {
			
			System.out.println(sdf.format(date2));
			System.out.println(date2.getTime()==date.getTime());
		}
    		
    		
	}
}
