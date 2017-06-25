var queryTime = 1000;
function getPingpangScore(){
	$.post( "queryScore_pingpang.action", { 
		matchId: $("#matchId").val(), 
		random: Math.random() 
	})
	.success(function(data){
		$("#nameAShow").html(data.nameA);
		$("#nameBShow").html(data.nameB);
		$("#setAShow").html(data.setA);
		$("#setBShow").html(data.setB);
		$("#scoreAShow").html(data.scoreA);
		$("#scoreBShow").html(data.scoreB);
		if( data.matchEnd ){
			$("#serve"+data.nextServe+"Show").css({"background":"","background-repeat":"","background-position":""});
			$("#serve"+data.notServe+"Show").css({"background":"","background-repeat":"","background-position":""});
			$("#scoreBoard").append($("<br/><div style='float:left;width:364px;height:50px;font-size:35px;line-height:50px;text-align:center;'>比赛结束</div>"));
			clearInterval(scoreBoardIntervalId);
		}
		else{
			$("#serve"+data.nextServe+"Show").css({"background":"url(resources/images/pingpang_scoreboard_ball.png)","background-repeat":"no-repeat","background-position":"-6px -9px"});
			$("#serve"+data.notServe+"Show").css({"background":"","background-repeat":"","background-position":""});
		}
	})
	.error(function(data){
		location.reload(true);
	});;
}
jQuery.fn.scoreBoardInit = function(){
	return this.each(function(){
		var $mainDiv = $(this)
		var width = $(window).width();
		var height = $(window).height();
		$mainDiv.css({"position":"absolute","left":(width-364)/2+"px","top":(height-103)/2+"px"})
				.css({"background":"url(resources/images/pingpang_scoreboard.png)","background-repeat":"no-repeat"})
				.css({"background-position":"-16px -7px","width":"364px","height":"103px"})
				.css({"font-family":"微软雅黑"});
		$mainDiv.append($("<div id='nameAShow' style='text-align:center;float:left;width:168px;height:50px;font-size:20px;line-height:50px;'></div>"));
		$mainDiv.append($("<div id='setAShow' style='text-align:center;float:left;width:65px;height:50px;font-size:35px;line-height:50px;'></div>"));
		$mainDiv.append($("<div id='scoreAShow' style='text-align:center;float:left;width:78px;height:50px;font-size:35px;line-height:50px;'></div>"));
		$mainDiv.append($("<div id='serveAShow' style='float:left;width:35px;height:50px;'></div>"));
		$mainDiv.append($("<div id='nameBShow' style='text-align:center;float:left;width:168px;height:58px;font-size:20px;line-height:58px;'></div>"));
		$mainDiv.append($("<div id='setBShow' style='text-align:center;float:left;width:65px;height:58px;font-size:35px;line-height:58px;'></div>"));
		$mainDiv.append($("<div id='scoreBShow' style='text-align:center;float:left;width:78px;height:58px;font-size:35px;line-height:58px;'></div>"));
		$mainDiv.append($("<div id='serveBShow' style='float:left;width:35px;height:58px;'></div>"));
		scoreBoardIntervalId = window.setInterval(getPingpangScore, queryTime);
	});
};