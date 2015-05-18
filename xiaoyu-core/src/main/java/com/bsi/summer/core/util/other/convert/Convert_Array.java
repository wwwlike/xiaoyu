package com.bsi.summer.core.util.other.convert;

import com.bsi.summer.core.util.ToolKit;



/**
 * 
 * @author wwwlike
 * @CreateDate 2007-11-9
 */
public class Convert_Array implements Convert {

    public Class getConvertClass() {
        return String[].class;
    }

    public String objToString(Object obj) {
        String value = "";
        try {
            String[] array = (String[]) obj;
            if(array!=null && array.length>0) {
                for(int i=0;i<array.length;i++){
                    if(i==0)
                        value += array[i];
                    else
                        value += ","+array[i];
                }
            }
        }catch(Exception e){
            //ignore
        }
        return value;
    }

    public Object stringToObj(String value) {
        return ToolKit.split(value,",");
    }

}
