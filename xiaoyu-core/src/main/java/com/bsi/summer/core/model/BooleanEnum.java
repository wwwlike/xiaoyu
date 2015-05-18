package com.bsi.summer.core.model;
/**
 * 
 * @author Administrator
 * @date   2012-8-13
 * @category 是否 的枚举。防止默认将boolean 规定为false
 */
public enum BooleanEnum  {
		FALSE(0),TRUE(1),NULL(null);
		private Integer clazz;
		
		private BooleanEnum(Integer clazz) {
			this.clazz = clazz;
		}
		public Integer getValue() {
			return clazz;
		}
		
		public String toString() {
			return this.clazz+"";
		}
		
		public static BooleanEnum value(boolean a){
			if(a){
				return BooleanEnum.TRUE;
			}else{
				return BooleanEnum.FALSE;
			}
		}
		
		public static void main(String[] args) {
			System.out.println(BooleanEnum.valueOf("ABC"));
		}
	}