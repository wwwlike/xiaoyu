// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileUtil.java

package com.bsi.summer.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class FileUtil
{

	public FileUtil()
	{
	}

	public static byte[] getBytesFromFileURL1(String fileUrl)
	{

		
		try {
			
			File file = new File(fileUrl.replace("\\", "/"));
			InputStream is = new FileInputStream(file);
			long length = file.length();
			byte bytes[] = new byte[(int)length];
			int offset = 0;
			for (int numRead = 0; offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0; offset += numRead);
			is.close();
			return bytes;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**  
     * 文件转化为字节数组  
     *   
     * @param file  
     * @return  
     */  
    public static byte[] getBytesFromFile(File file) {   
        byte[] ret = null;   
        try {           	
            FileInputStream in = new FileInputStream(file);   
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);   
            byte[] b = new byte[4096];   
            int n;   
            while ((n = in.read(b)) != -1) {   
                out.write(b, 0, n);   
            }   
            in.close();   
            out.close();   
            ret = out.toByteArray();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
        return ret;   
    }  
    
    /**
	 * 根据文件路径转换为BYTE[]
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static byte[] getFileByte(String path) throws IOException {
		FileInputStream in = new FileInputStream(path);
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		copy(in, out);
		// copy(in, out, 0, 1024);
		return out.toByteArray();

	}
	
	
	private static void copy(InputStream in, OutputStream out) {

		try {
			byte[] buffer = new byte[1024];
			int nrOfBytes = -1;
			while ((nrOfBytes = in.read(buffer)) != -1) {
				out.write(buffer, 0, nrOfBytes);
			}
			out.flush();
		} catch (IOException e) {

		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ex) {
			}
		}

	}
	
	/**
     * 新建目录
     * @param folderPath String 如 c:/fqf
     * @return boolean
     */
   public static void newFolder(String folderPath) {
       try {
           String filePath = folderPath;
           filePath = filePath.toString();
           java.io.File myFilePath = new java.io.File(filePath);
           if (!myFilePath.exists()) {
               myFilePath.mkdir();
           }
       }
       catch (Exception e) {
           System.out.println("新建目录操作出错");
           e.printStackTrace();
       }
   }

   /**
     * 新建文件
     * @param filePathAndName String 文件路径及名称 如c:/fqf.txt
     * @param fileContent String 文件内容
     * @return boolean
     */
   public static void newFile(String filePathAndName, String fileContent) {

       try {
           String filePath = filePathAndName;
           filePath = filePath.toString();
           File myFilePath = new File(filePath);
           if (!myFilePath.exists()) {
               myFilePath.createNewFile();
           }
           FileWriter resultFile = new FileWriter(myFilePath);
           PrintWriter myFile = new PrintWriter(resultFile);
           String strContent = fileContent;
           myFile.println(strContent);
           resultFile.close();

       }
       catch (Exception e) {
           System.out.println("新建目录操作出错");
           e.printStackTrace();

       }

   }

   /**
     * 删除文件
     * @param filePathAndName String 文件路径及名称 如c:/fqf.txt
     * @param fileContent String
     * @return boolean
     */
   public static void delFile(String filePathAndName) {
       try {
           String filePath = filePathAndName;
           filePath = filePath.toString();
           java.io.File myDelFile = new java.io.File(filePath);
           myDelFile.delete();

       }
       catch (Exception e) {
           System.out.println("删除文件操作出错");
           e.printStackTrace();

       }

   }

   /**
     * 删除文件夹
     * @param filePathAndName String 文件夹路径及名称 如c:/fqf
     * @param fileContent String
     * @return boolean
     */
   public static void delFolder(String folderPath) {
       try {
           delAllFile(folderPath); //删除完里面所有内容
           String filePath = folderPath;
           filePath = filePath.toString();
           java.io.File myFilePath = new java.io.File(filePath);
           myFilePath.delete(); //删除空文件夹

       }
       catch (Exception e) {
           System.out.println("删除文件夹操作出错");
           e.printStackTrace();

       }

   }

	
	/**
     * 删除文件夹里面的所有文件
     * @param path String 文件夹路径 如 c:/fqf
     */
   public static void delAllFile(String path) {
       File file = new File(path);
       if (!file.exists()) {
           return;
       }
       if (!file.isDirectory()) {
           return;
       }
       String[] tempList = file.list();
       File temp = null;
       for (int i = 0; i < tempList.length; i++) {
           if (path.endsWith(File.separator)) {
               temp = new File(path + tempList[i]);
           }
           else {
               temp = new File(path + File.separator + tempList[i]);
           }
           if (temp.isFile()) {
               temp.delete();
           }
           if (temp.isDirectory()) {
               delAllFile(path+"/"+ tempList[i]);//先删除文件夹里面的文件
               delFolder(path+"/"+ tempList[i]);//再删除空文件夹
           }
       }
   }

   /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
   public static void copyFile(String oldPath, String newPath) {
       try {
           int bytesum = 0;
           int byteread = 0;
           File oldfile = new File(oldPath);
           if (oldfile.exists()) { //文件存在时
               InputStream inStream = new FileInputStream(oldPath); //读入原文件
               FileOutputStream fs = new FileOutputStream(newPath);
               byte[] buffer = new byte[1444];
               int length;
               while ( (byteread = inStream.read(buffer)) != -1) {
                   bytesum += byteread; //字节数 文件大小
                   System.out.println(bytesum);
                   fs.write(buffer, 0, byteread);
               }
               inStream.close();
               fs.close();
           }
       }
       catch (Exception e) {
           System.out.println("复制单个文件操作出错");
           e.printStackTrace();

       }

   }

   /**
     * 复制整个文件夹内容
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
   public static void copyFolder(String oldPath, String newPath) {

       try {
           (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
           File a=new File(oldPath);
           String[] file=a.list();
           File temp=null;
           for (int i = 0; i < file.length; i++) {
               if(oldPath.endsWith(File.separator)){
                   temp=new File(oldPath+file[i]);
               }
               else{
                   temp=new File(oldPath+File.separator+file[i]);
               }

               if(temp.isFile()){
                   FileInputStream input = new FileInputStream(temp);
                   FileOutputStream output = new FileOutputStream(newPath + "/" +
                           (temp.getName()).toString());
                   byte[] b = new byte[1024 * 5];
                   int len;
                   while ( (len = input.read(b)) != -1) {
                       output.write(b, 0, len);
                   }
                   output.flush();
                   output.close();
                   input.close();
               }
               if(temp.isDirectory()){//如果是子文件夹
                   copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
               }
           }
       }
       catch (Exception e) {
           System.out.println("复制整个文件夹内容操作出错");
           e.printStackTrace();

       }

   }

   /**
     * 移动文件到指定目录
     * @param oldPath String 如：c:/fqf.txt
     * @param newPath String 如：d:/fqf.txt
     */
   public static void moveFile(String oldPath, String newPath) {
       copyFile(oldPath, newPath);
       delFile(oldPath);

   }

   /**
     * 移动文件到指定目录
     * @param oldPath String 如：c:/fqf.txt
     * @param newPath String 如：d:/fqf.txt
     */
   public static void moveFolder(String oldPath, String newPath) {
       copyFolder(oldPath, newPath);
       delFolder(oldPath);

   }
   
   /** 
    * 判断文件名是否以.zip为后缀 
    * @param fileName        需要判断的文件名 
    * @return 是zip文件返回true,否则返回false 
    */
   public static boolean isEndsWithZip(String fileName) { 
       boolean flag = false; 
       if(fileName != null && !"".equals(fileName.trim())) { 
           if(fileName.endsWith(".ZIP")||fileName.endsWith(".zip")){ 
               flag = true; 
           } 
       } 
       return flag; 
   } 

}