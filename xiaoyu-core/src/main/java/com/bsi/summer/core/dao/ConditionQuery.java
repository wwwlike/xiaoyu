package com.bsi.summer.core.dao;


public class ConditionQuery {
//	//3天后
//	public Date After3Day=TimeUtils.stringToDate(TimeUtils.getDateAfter(TimeUtils.getDate(),3),"yyyy-MM-dd");
//	//当天
//	public Date Today=TimeUtils.stringToDate(TimeUtils.getDate(),"yyyy-MM-dd");
//	
//	//今年(data)
//	public Date CurrYearSql=TimeUtils.stringToDate(TimeUtils.getDate(),"yyyy");
//	
//	//本月(data)
//	public Date CurrMonthSql=TimeUtils.stringToDate(TimeUtils.getDate(),"yyyy-MM");
//	
//	//今年
//	public String CurrYear=TimeUtils.getDate().substring(0,4);
//	
//	//30天前
//	public Date Before30Day=TimeUtils.stringToDate(TimeUtils.getDateAfter(TimeUtils.getDate(),-30),"yyyy-MM-dd");
//	//当前用户
//	public String UserId=getUserInfo().getId();
//	
//	//当前医院
//	public String HospitalId=getUserInfo().getUser().getHospital()==null?null:getUserInfo().getUser().getHospital().getId();
//	
//	//等待审核的状态
//	public String  waitAuditStatus=getMystatus();
//	
//	//审核完毕的状态
//	public String  finishStatus=getFinishStatus();
//	
//	
//	public String getFinishStatus()
//	{
//		String opGroupId=getUserInfo().getOperateGroup().getId();
//		if("3".equals(opGroupId))
//			return "22";
//		if("4".equals(opGroupId))
//			return "222";
//		if("5".equals(opGroupId))
//			return "2222";
//		if("2".equals(opGroupId)||"1".equals(opGroupId)) //民营 和一般 二级以上医院
//			return "22";
//		if("7".equals(opGroupId)) //省属二级以上医院
//			return "2222";
//		
//		return "";
//	}
//	//待审核
//	public String getMystatus()
//	{
//		String opGroupId=getUserInfo().getOperateGroup().getId();
//		if("3".equals(opGroupId))
//			return "2";
//		if("4".equals(opGroupId))
//			return "22";
//		if("5".equals(opGroupId))
//			return "222";
//		
//		if("2".equals(opGroupId)||"1".equals(opGroupId)) //民营 和一般 二级以上医院
//			return "2";
//		if("7".equals(opGroupId)) //省属二级以上医院
//			return "222";
//		return "";
//	}
//	
//	public  UserInfo getUserInfo()
//	{
//		SessionContainer container = (SessionContainer) getSession().get(Const.SessionContainer);
//		if(container==null)
//			return null;
//		UserInfo userinfo=container.getUserInfo();
//		return userinfo;
//	}
//
//	// 获取Session
//	public Map<String, Object> getSession() {
//		ActionContext actionContext = ActionContext.getContext();
//		Map<String, Object> session = actionContext.getSession();
//		return session;
//	}
//	
//	 public static void main(String[] args) {
//		System.out.println(TimeUtils.getDate().substring(0,4));
//	}
}
