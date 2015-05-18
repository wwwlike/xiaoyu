package com.bsi.summer.core.util;

/**
 * 字符串工具类。
 * 
 * @author carver.gu
 * @since 1.0, Sep 12, 2009
 */
public abstract class StringUtils {

	private StringUtils() {
	}

	/**
	 * 检查指定的字符串是否为空。
	 * <ul>
	 * <li>SysUtils.isEmpty(null) = true</li>
	 * <li>SysUtils.isEmpty("") = true</li>
	 * <li>SysUtils.isEmpty("   ") = true</li>
	 * <li>SysUtils.isEmpty("abc") = false</li>
	 * </ul>
	 * 
	 * @param value 待检查的字符串
	 * @return true/false
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查对象是否为数字型字符串。
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		String str = obj.toString();
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values) {
		boolean result = true;
		if (values == null || values.length == 0) {
			result = false;
		} else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 把通用字符编码的字符串转化为汉字编码。
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}

	/**
	 * 过滤不可见字符
	 */
	public static String stripNonValidXMLCharacters(String input) {
		if (input == null || ("".equals(input)))
			return "";
		StringBuilder out = new StringBuilder();
		char current;
		for (int i = 0; i < input.length(); i++) {
			current = input.charAt(i);
			if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF))
					|| ((current >= 0xE000) && (current <= 0xFFFD)) || ((current >= 0x10000) && (current <= 0x10FFFF)))
				out.append(current);
		}
		return out.toString();
	}

	/**
	 * 组合单词 首字母小写 
	 * @param word
	 * @return
	 */
	public static String toLowerCaseFirstWord(String word) {
		if (word == null)
			return word;
		else if (word.length() == 1)
			return word.toLowerCase();
		else
			return word.substring(0, 1).toLowerCase() + word.substring(1);
	}

	/**
	 * 组合单词 首字母大写 
	 * @param word
	 * @return
	 */
	public static String toUpperCaseFirstWord(String word) {
		if (word == null)
			return word;
		else if (word.length() == 1)
			return word.toUpperCase();
		else
			return word.substring(0, 1).toUpperCase() + word.substring(1);
	}

	 public static char toLower(char ch){
	  if(ch <= 'Z' && ch >= 'A')
	  {
	   return (char)(ch-'A'+'a');
	  }
	  return ch;
	 }
	 
	 public static char toUpper(char ch)
	 {
	  if(ch <= 'z' && ch >= 'a')
	  {
	   return (char)(ch-32);
	  }
	  return ch;
	 }
	
	/**
	 * _后字母大写
	 * @param word
	 * @return
	 */
	public static String toUpperCaseAftrer_(String word) {
		if(word==null){
			return word;
		}
		char[] charArr=word.toCharArray();
		int i=0;
		String reword="";
		boolean flag=false;
		for(char a :charArr){
			if(a!='_'&&flag){
				reword+=word.substring(i,i+1).toUpperCase();
				flag=false;
			}else if(a=='_'){
				flag=true;
			}else{
				reword+=charArr[i];
			}
			i++;
		}
		return reword;
	}

	
	/**
	 * 字符串转换成  _形式
	 * 例如：JjUeasyIdkdDD 转换成：jj_ueasy_idkd_d_d 形式
	 * @param str
	 * @return
	 */
	public static String addKeyUpperCase(String word) {
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (!Character.isLowerCase(c)) {
				if (i != 0)
					word = word.substring(0, i) + "_" + Character.toLowerCase(c) + word.substring(i + 1);
				else
					word = word.substring(0, i) + Character.toLowerCase(c) + word.substring(i + 1);
			}
		}
		return word;
	}

	/**
	 * 去除关键字 并且接下来首字母大写,并且第一个单词永远大写
	 *  例如：jj_ueasy_idkd_d_d 转换成：JjUeasyIdkdDD 形式
	 * @param word 格式化字符串
	 * @param key 过滤关键字
	 * @return
	 */
	public static String removeKeyandUpperCase(String word, char key) {
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (c == key && word.length() > i + 1) {
				word = word.substring(0, i) + Character.toUpperCase(word.charAt(i + 1)) + word.substring(i + 2);
			} else if (c == key) {
				word = word.substring(0, i);
			}
		}
		return toUpperCaseFirstWord(word);
	}
	
	/**
	 * 首字母小写
	 * @param word
	 * @return
	 */
	public static String uncap_first(String word)
	{
		return Character.toLowerCase(word.charAt(0))+word.substring(1);
	}

	
	public static void main(String[] args) {
		System.out.println(toUpperCaseAftrer_("Form_design"));
	}
}
