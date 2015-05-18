package com.bsi.summer.core.util.other.convert;

import java.sql.Timestamp;

/**
 * 
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public class Convert_SqlTimestamp implements Convert {

    public Class getConvertClass() {
        return Timestamp.class;
    }

    public String objToString(Object obj) {
        String value = null;
        try {
            Timestamp date = (Timestamp) obj;
            java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			value = df.format(date) ;
        }catch(Exception e){
            //ignore
        }
        return value;
    }

    public Object stringToObj(String value) {
        Timestamp date = null;
        try {
            if(value.length() == 10) {
                java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
                date = new Timestamp(df.parse(value).getTime());
            } else {
                java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = new Timestamp(df.parse(value).getTime());
            }
        }catch(Exception e){
            //ingore
        }
        return date;
    }
}
