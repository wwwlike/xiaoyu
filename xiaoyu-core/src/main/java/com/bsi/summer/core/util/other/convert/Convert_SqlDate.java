package com.bsi.summer.core.util.other.convert;

import java.sql.Date;

/**
 * 将各种合适的类型转为String对象
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public class Convert_SqlDate implements Convert {

    public Class getConvertClass() {
        return Date.class;
    }
   
    public String objToString(Object obj) {
        String value = null;
        try {
            Date date = (Date) obj;
            java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
			value = df.format(date) ;
        }catch(Exception e){
            //ignore
        }
        return value;
    }

    public Object stringToObj(String value) {
        Date date = null;
        try {
            java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
            date = new Date(df.parse(value).getTime());
        }catch(Exception e){
            //ingore
        }
        return date;
    }
}
