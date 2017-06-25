<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
<script type="text/javascript" src="resources/js/jquery-1.12.4.js"></script>
<script type="text/javascript">
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function intervalFun(){
	$("body").append();
	var startDate = new Date();
	$.post("test_delayTest.action",{},
	function(data){
		var endDate = new Date();
		html = "<div>本地发送:"+startDate.Format("mm:ss.S")+" 服务器返回:"+data.date+" 本地接收:"+endDate.Format("mm:ss.S")+" 时长:"+(endDate-startDate)+"ms</div>";
		$("body").append(html);
	});
}
$(function(){
	setInterval("intervalFun()", 1000);
});
</script>
</html>