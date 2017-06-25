function getTennisStatistics(){
	$.post( "statistics_tennis.action", {matchId: $("#matchId").val()})
	.success(function(data){
		$("#personNameA").html(data.nameA);
		$("#personNameB").html(data.nameB);
		$("#titleDiv").html(data.title);
		for( var i = 0; i < data.listA.length; i++ ){
			$("#contentA"+i).html(data.listA[i]);
			$("#contentB"+i).html(data.listB[i]);
		}
	})
	.error(function(data){
		location.reload(true);
	});
}
jQuery.fn.statisticsInit = function(){
	$("body").css({"font-family":"微软雅黑"});
	return this.each(function(){
		var $mainDiv = $(this)
		var width = $(window).width();
		var height = $(window).height();
		$mainDiv.css({"color":"white","position":"absolute","left":(width-1350)/2+"px","top":(height-998)/2+"px"})
		.css({"background":"url(resources/images/tennis_statistics.png)","background-repeat":"no-repeat"})
		.css({"width":"1350px","height":"998px","background-position":"0px -2px"})
		.css({"font-family":"微软雅黑"});
		$mainDiv.append($("<div id='titleDiv' style='width:100%;height:147px;text-align:center;font-size:80px;line-height:140px;'></div>"));
		$mainDiv.append($("<div id='personNameDiv' style='width:100%;height:105px;'></div>"));
		$mainDiv.append($("<div id='contentMainDivA' style='float:left;width:35%;height:703px;'></div>"));
		$mainDiv.append($("<div id='contentMainDivTitle' style='float:left;width:30%;height:703px;'></div>"));
		$mainDiv.append($("<div id='contentMainDivB' style='float:left;width:35%;height:703px;'></div>"));
		$("#personNameDiv").append("<div id='personNameA' style='float:left;width:50%;height:105px;text-align:center;font-size:70px;line-height:105px;color:white;'>aaaa</div>");
		$("#personNameDiv").append("<div id='personNameB' style='float:right;width:50%;height:105px;text-align:center;font-size:70px;line-height:105px;color:white;'>bbb</div>");
		$("#contentMainDivA").append("<div id='contentMainDivASub' style='float:right;width:60%;height:703px;'></div>");
		$("#contentMainDivB").append("<div id='contentMainDivBSub' style='float:left;width:59%;height:703px;'></div>");
		var titleArray = ["ACE球","双误","发球直接得分","进攻得分","受迫失误","非受迫失误","一发进球率","一发得分率","二发进球率","二发得分率"];
		
		for( var i = 0; i < titleArray.length; i++ ){
			$("#contentMainDivASub").append("<div id='contentA"+i+"' style='height:71px;text-align:center;font-size:45px;line-height:65px;color:black;'></div>");
			$("#contentMainDivTitle").append("<div id='contentTitle"+i+"' style='height:71px;text-align:center;font-size:45px;line-height:65px;color:black;'>"+titleArray[i]+"</div>");
			$("#contentMainDivBSub").append("<div id='contentB"+i+"' style='height:71px;text-align:center;font-size:45px;line-height:65px;color:black;'></div>");
		}
		getTennisStatistics();
		statisticsIntervalId = window.setInterval(getTennisStatistics, 5000);
	});
};