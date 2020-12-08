package com.camelot.share;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Week {

    public static void main(String[] args) throws ParseException {

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("startDate","2020-08-31");
        stringObjectHashMap.put("endDate","2020-09-27");
        List<String> week = doDateByStatisticsType("week", stringObjectHashMap);
        System.out.println(week);
    }

    /**
     * 根据传入的参数，来对日期区间进行拆分，返回拆分后的日期List
     * @param statisticsType
     * @param map
     * @return
     * @throws ParseException
     * @author lihq 2019-6-24
     * @editor
     * @editcont
     */
    public static List<String> doDateByStatisticsType(String statisticsType, Map<String, Object> map) throws ParseException{
        List<String> listWeekOrMonth = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = (String)map.get("startDate");
        String endDate = (String)map.get("endDate");
        Date sDate = dateFormat.parse(startDate);
        Calendar sCalendar = Calendar.getInstance();
        sCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        sCalendar.setTime(sDate);
        Date eDate = dateFormat.parse(endDate);
        Calendar eCalendar = Calendar.getInstance();
        eCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        eCalendar.setTime(eDate);
        boolean bool =true;
        if(statisticsType.equals("week")){
            while(sCalendar.getTime().getTime()<eCalendar.getTime().getTime()){
                if(bool||sCalendar.get(Calendar.DAY_OF_WEEK)==2||sCalendar.get(Calendar.DAY_OF_WEEK)==1){
                    listWeekOrMonth.add(dateFormat.format(sCalendar.getTime())+"标记");
                    bool = false;
                }
                sCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            listWeekOrMonth.add(dateFormat.format(eCalendar.getTime()));
            if(listWeekOrMonth.size()%2!=0){
                listWeekOrMonth.add(dateFormat.format(eCalendar.getTime()));
            }
        }else{
            while(sCalendar.getTime().getTime()<eCalendar.getTime().getTime()){
                if(bool||sCalendar.get(Calendar.DAY_OF_MONTH)==1||sCalendar.get(Calendar.DAY_OF_MONTH)==sCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
                    listWeekOrMonth.add(dateFormat.format(sCalendar.getTime()));
                    bool = false;
                }
                sCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            listWeekOrMonth.add(dateFormat.format(eCalendar.getTime()));
            if(listWeekOrMonth.size()%2!=0){
                listWeekOrMonth.add(dateFormat.format(eCalendar.getTime()));
            }
        }

        return listWeekOrMonth;
    }
}
