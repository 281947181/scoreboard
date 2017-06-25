function getTennisScore(){
	$.post( "queryScore_tennis.action", { 
		matchId: $("#matchId").val(),
		random: Math.random()
	})
	.success(function(data){
		if( data.status == "已结束" ){
			window.clearInterval(scoreBoardIntervalId);
			scoreBoardIntervalId = window.setInterval(getTennisScore, 5000);
		}
		else{
			window.clearInterval(scoreBoardIntervalId);
			scoreBoardIntervalId = window.setInterval(getTennisScore, 1000);
		}
		$("#nameA").html(data.nameA);
		$("#nameB").html(data.nameB);
		$("#councilA").html(data.councilA);
		$("#councilB").html(data.councilB);
		$("#setA").html(data.setA);
		$("#setB").html(data.setB);
		$("#smallA").html(data.smallA);
		$("#smallB").html(data.smallB);
		if( data.nextServe != "NO" ){
			$("#nextServe"+data.nextServe).css({"background":"url(resources/images/max_3.jpg)","background-repeat":"no-repeat","background-position":"0px 10px"});
			$("#nextServe"+data.noNextServe).css({"background":"","background-repeat":"","background-position":""});
		}
		if( data.point != "" ){
			$("#matchPointTextDiv").html(data.point);
			$("#matchPointDiv").show();
		}
		else{
			$("#matchPointTextDiv").html("");
			$("#matchPointDiv").hide();
		}
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
		$mainDiv.css({"color":"white","position":"absolute","left":((width-293)/2)+"px","top":((height-80)/2)+"px"});
		$mainDiv.append($("<div id='matchPointDiv' style='position:absolute;left:29px;top:0px;background:url(resources/images/max_.png);background-repeat:no-repeat;width:246px;height:20px;padding-left:10px;'></div>"));
		$mainDiv.append($("<div style='position:absolute;left:0px;top:20px;background:url(resources/images/max_L.png);width:29px;height:60px;'></div>"));
		$mainDiv.append($("<div id='playerNameMainDiv' style='position:absolute;left:29px;top:20px;background:url(resources/images/max_M.png);background-repeat:repeat-x;width:150px;height:60px;'></div>"));
		$mainDiv.append($("<div id='nextServeMainDiv' style='position:absolute;left:179px;top:20px;background:url(resources/images/max_M.png);background-repeat:repeat-x;width:10px;height:60px;'></div>"));
		$mainDiv.append($("<div id='scoreMainVid' style='position:absolute;left:189px;top:20px;background:url(resources/images/max_R2.png);width:104px;height:60px;'></div>"));
		$("#playerNameMainDiv").append($("<div id='nameA' style='color:black;height:30px;line-height:29px;font-size:22px;text-align:center;padding-top:1px'></div>"));
		$("#playerNameMainDiv").append($("<div id='nameB' style='color:black;height:30px;line-height:24px;font-size:22px;text-align:center;'></div>"));
		$("#nextServeMainDiv").append($("<div id='nextServeA' style='height:30px;'></div>"));
		$("#nextServeMainDiv").append($("<div id='nextServeB' style='height:30px;'></div>"));
		$("#scoreMainVid").append($("<div id='setA' style='float:left;width:23px;height:24px;color:white;line-height:24px;font-size:22px;text-align:center;margin-left:7px;margin-top:3px;background-color:#AA06B2;'></div>"));
		$("#scoreMainVid").append($("<div id='councilA' style='float:left;width:27px;height:24px;color:white;line-height:24px;font-size:22px;text-align:center;margin-top:3px;background-color:#74057A;'></div>"));
		$("#scoreMainVid").append($("<div id='smallA' style='float:left;width:34px;height:24px;color:white;line-height:24px;font-size:22px;text-align:center;margin-top:3px;'></div>"));
		$("#scoreMainVid").append($("<div id='setB' style='float:left;width:23px;height:28px;color:white;line-height:28px;font-size:22px;text-align:center;margin-left:7px;background-color:#AA06B2;'></div>"));
		$("#scoreMainVid").append($("<div id='councilB' style='float:left;width:27px;height:28px;color:white;line-height:28px;font-size:22px;text-align:center;background-color:#74057A;'></div>"));
		$("#scoreMainVid").append($("<div id='smallB' style='float:left;width:34px;height:28px;color:white;line-height:28px;font-size:22px;text-align:center;'></div>"));
		$("#matchPointDiv").append($("<div id='matchPointTextDiv' style='margin-left:70px;'></div>"))
		$("#matchPointDiv").hide();
		getTennisScore();
		scoreBoardIntervalId = window.setInterval(getTennisScore, 1000);
		
	});
};