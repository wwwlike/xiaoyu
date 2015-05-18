<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/common/include.jsp" %>
<%@ include file="/common/head.jsp" %>
<%@ include file="/common/jquery.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>行政区划-列表</title>
<script>
function goAdd() {
	var url = "area!add.action";
	openWin(url,'',350,105);
}
function goLoad() {
	var ids = SELECT_ID;
	if(ids=="" || ids.split(",").length >1) {
		alert("请选择一条记录!");
		return;
	}
	parent.addTabFun({title : 'aa',
		src :  'cccccc'});
//	var url = "area!edit.action?id="+SELECT_ID;
//	openWin(url);
}
function setupContextMenu() {
	var menucfg = {
		id : "ctxmenu",
		cache : true,
		display_url : true,
		container : $get("GridContainer"),
		items : [
			{enabled : true, js:goAdd, name:"新建"},
			{enabled : true, js:goLoad, name:"编辑"},
			{line : true},
			{enabled : true, js:goDeleteBatch, name:"删除"}
		]
	}
	var menu = new Roly.ContextMenu();
	menu.init(menucfg);
	return false;
}
JsUtil.addEventHandler(window,"load", setupContextMenu);
</script>
</head>
<body class="list">
<div id="listDivTop">
<div id="mtitle">
	<div id="mtitle-row">${r"${stable.id}"}</div><s:if test="fastName!=null"><div id="mtitle-row"><s:property value="fastName"/></div></s:if>
</div>

<s:if test="#request.gridQuery!=null">
<div id="search_div">
<form id="searchForm" name="searchForm" action="area!list.action" method="post">
 
<table width="100%">
	<tr>
		<td>
		<s:property value="#request.gridQuery" escape="false"/></td>
		<td width="15%" ><button type="submit" class="button"  cmd="area_list">搜索</button></td>
	</tr>
</table>
</form>
</div>
</s:if>
<div class="control">
	<button type="button" class="button" id="_btn_add" onClick="goAdd()" cmd="area_add">新增</button>
	<button type="button" class="button" id="_btn_edit" onClick="goLoad()" cmd="area_edit">编辑</button>
	<button type="button" class="button" id="_btn_deleteBatch"  action="area" cmd="area_delete">删除</button>
</div>
<script language="javascript">
<%List list=(List)request.getAttribute("GridList");%>
var myPK = <%= GridUtil.parseBeanValue(list,"id")%>;
var SELECT_ID = "";
var SELECT_BUTTON_MULTI = ["_btn_deleteBatch"];
var SELECT_BUTTON_SINGLE = ["_btn_moveup","_btn_movedown"];

function GridSelectedRowsChanged(value) {
	SELECT_ID = "";
	for(var i=0;i<value.length;i++) {
		var temp = value[i];
		if(i == 0)
			SELECT_ID = myPK[temp];
		else
			SELECT_ID = SELECT_ID+","+myPK[temp];
	}
	changeButtonState(SELECT_BUTTON_MULTI,SELECT_BUTTON_SINGLE,value);
}

function GridRowDoubleClicked(value) {
	SELECT_ID = myPK[value];
	goLoad();
}
</script>
</div>
<div class="body">
<form id="listForm" action="${r"${stable.javaBean}" method="post">
<input type="hidden" name="id" id="id" value="">
<s:iterator value="pager.searchMap" var="searchMap">
  <input type="hidden" name="pager.searchMap['${searchMap.key}']" value="${searchMap.value}">
</s:iterator>
<jsp:include page="/common/grid.jsp" />
<jsp:include page="/common/page.jsp" />
</form>
</div>
</body>
</html>
