package com.bsi.summer.core.util;

import java.io.*;

/**
 * @author dlc
 * TODO 
 */
public class Logger {
    private static Logger logger = null;
    private static String logPath = SummerGlobals.getProperty("com.summer.log.path");
    private static boolean debug = 
    	"true".equalsIgnoreCase(SummerGlobals.getProperty("com.summer.debug"));
    private String logFile = null;		//指定的路径及文件名
    
    private Logger() {
        File file = new File(logPath);
		if(!file.exists())
			file.mkdirs();
    }
    
    private Logger(String path,String fileName) {
        File file = new File(path);
		if(!file.exists())
			file.mkdirs();
		this.logFile = path+"/"+fileName;
    }
    
    /**
     * 保存至默认路径，默认文件名
     * @return
     */
    public static Logger getInstance() {
        if(logger == null)
            logger = new Logger();
        return logger;
    }
    
    /**
     * 保存至指定的路径和文件名
     * @param path 保存的绝对路径
     * @param fileName 文件名
     * @return
     */
    public static Logger getInstance(String path,String fileName) {
        return new Logger(path,fileName);
    }
    
	public void log(String msg) {
	    if(debug) {
	        System.out.println("[log]\t"+msg);
	    }
		write(msg);
	}
	
	public void log(Throwable e) {
	    if(debug) {
	        System.out.println("[log]\t");
	        e.printStackTrace();
	    }
		write(e);
	}
	
	public void log(String msg,Throwable e) {
	    if(debug) {
	        System.out.println("[log]\t"+msg);
	        e.printStackTrace();
	    }
		write(msg);
		write(e);
	}
	
	public void debug (String msg) {
	    if(debug) {
	        System.out.println("[debug]\t"+msg);
	    }
	}
	
	public void debug(Throwable e) {
	    if(debug) {
	        System.out.println("[debug]\t");
	        e.printStackTrace();
	    }
	}
	
	public void debug(String msg,Throwable e) {
	    if(debug) {
	        System.out.println("[debug]\t"+msg);
	        e.printStackTrace();
	    }
	}
	
	private void write(String msg) {
		File file = null;
		if(logFile!=null)
		    file = new File(logFile);
		else
		    file = new File(logPath+"/"+getTime("yyyy-MM-dd")+".log");
		try{
			PrintWriter out = new PrintWriter( new OutputStreamWriter(new FileOutputStream(file,true),"gb2312"));
			out.println("["+getTime("yyyy-MM-dd HH:mm:ss")+"]:"+msg);
			out.flush();
			out.close();
		}catch(Exception e){
			System.out.println("日志文件创建出错！");
			e.printStackTrace();
		}
	}
	
	private void write(Throwable e) {
	    File file = null;
		if(logFile!=null)
		    file = new File(logFile);
		else
		    file = new File(logPath+"/"+getTime("yyyy-MM-dd")+".log");
		try{
			PrintWriter out = new PrintWriter( new OutputStreamWriter(new FileOutputStream(file,true),"gb2312"));
			e.printStackTrace(out);
			out.flush();
			out.close();
		}catch(Exception ex){
			System.out.println("日志文件创建出错！");
			ex.printStackTrace();
		}
	}
	
	private static String getTime(String text) {
		String datestr =  "" ;
		java.text.DateFormat df = new java.text.SimpleDateFormat(text);
		datestr = df.format(new java.util.Date()) ;
		return datestr;
    }
	
	public static void main(String args[]) {
		Logger.getInstance().log("你好！");
		try{
			int [] a = {1,2,3};
			System.out.println(a[3]);
		}catch(Exception e){
			Logger.getInstance().log("出错拉",e);
		}
		System.out.println(logPath);
	}
}
