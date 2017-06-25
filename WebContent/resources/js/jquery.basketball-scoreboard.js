var queryTime = 500;
function getBasketballScore(){
	$.post( "queryScore_basketball.action", { 
		matchId: $("#matchId").val(), 
		random: Math.random() 
	})
	.success(function(data){
		$("#nameAShow").html(data.nameA);
		$("#nameBShow").html(data.nameB);
		$("#scoreAShow").html(data.scoreA);
		$("#scoreBShow").html(data.scoreB);
		if( data.status == "stop" ){
			$("#setInfoShow").html("比赛结束");
			clearInterval(scoreBoardIntervalId)
		}
		else if( data.status == "pause" ){
			$("#setInfoShow").html("第"+data.setNum+"节");
			if( queryTime == 500 ){
				queryTime = 1000;
				clearInterval(scoreBoardIntervalId);
				scoreBoardIntervalId = window.setInterval(getBasketballScore, queryTime);
			}
		}
		else{
			$("#setInfoShow").html("第"+data.setNum+"节");
			if( queryTime == 1000 ){
				queryTime = 500;
				clearInterval(scoreBoardIntervalId);
				scoreBoardIntervalId = window.setInterval(getBasketballScore, queryTime);
			}
		}
			
//		$("#totalTimeInfoShow").html(data.totalMinute+":"+(data.totalSecond<10?("0"+data.totalSecond):data.totalSecond));
//		$("#sideTimeInfoShow").html(data.sideTime<10?("0"+data.sideTime):data.sideTime);
	})
	.error(function(data){
		location.reload(true);
	});
}
jQuery.fn.scoreBoardInit = function(){
	return this.each(function(){
		var $mainDiv = $(this)
		var width = $(window).width();
		var height = $(window).height();
		$mainDiv.css({"color":"black","position":"absolute","left":(width-1120)/2+"px","top":(height-55)/2+"px"})
				.css({"background":"url(resources/images/basketball_scoreboard.png)","background-repeat":"no-repeat"})
				.css({"background-position":"-450px -135px","width":"1120px","height":"55px"})
				.css({"font-family":"黑体"});
		$mainDiv.append($("<div id='nameAShow' style='float:left;width:315px;height:52px;font-size:30px;line-height:52px;text-align:center;'></div>"));
		$mainDiv.append($("<div id='scoreAShow' style='color:white;float:left;width:95px;height:52px;font-size:30px;line-height:52px;text-align:center;'></div>"));
		$mainDiv.append($("<div id='nameBShow' style='float:left;width:315px;height:52px;font-size:30px;line-height:52px;text-align:center;'></div>"));
		$mainDiv.append($("<div id='scoreBShow' style='color:white;float:left;width:94px;height:52px;font-size:30px;line-height:52px;text-align:center;'></div>"));
		$mainDiv.append($("<div id='setInfoShow' style='float:left;width:109px;height:52px;font-size:30px;line-height:52px;text-align:center;'></div>"));
//		$mainDiv.append($("<div id='totalTimeInfoShow' style='float:left;width:136px;height:52px;font-size:30px;line-height:52px;text-align:center;'></div>"));
//		$mainDiv.append($("<div id='sideTimeInfoShow' style='color:white;float:left;width:50px;height:52px;font-size:30px;line-height:52px;text-align:center;'></div>"));
		scoreBoardIntervalId = window.setInterval(getBasketballScore, queryTime);
	});
};