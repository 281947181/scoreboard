var totalMinute = 0, totalSecond = 0, sideSecond = 0, setNum = 0;
var totalTimeId = 0;
var scoreBoardIntervalId = 0;
function createShortCut(){
	$("body").keypress(function(event){
		switch(event.keyCode){
			case 13://回车，开始/暂停
				$("#startBtn").click();
				break;
			case 43://+，切换
				$("#changeBtn").click();
				break;
			case 49://1,A+1
				$("input[id='A+1Btn']").click();
				break;
			case 52://4,A+2
				$("input[id='A+2Btn']").click();
				break;
			case 55://7,A+3
				$("input[id='A+3Btn']").click();
				break;
			case 51://3,B+1
				$("input[id='B+1Btn']").click();
				break;
			case 54://6,B+2
				$("input[id='B+2Btn']").click();
				break;
			case 57://9,B+3
				$("input[id='B+3Btn']").click();
				break;
			default: 
				break;
		}
	});
}
$(function(){
	$("#scoreControlMainDiv").hide();
	$("#createBtn").click(function(){
		if( $("#setNum").val() != "" && $("#singleSetTime").val() != "" && $("#nameA").val() != "" && $("#nameB").val() != "" )
			$.post("scoreControl_createBasketballMatch.action",
				$("#createMatchForm").serialize(),
				function(data){
					$("#matchId").val(data.matchId);
					$("#createBtn").attr("disabled",true);
					$("#nameADiv").html(data.nameA);
					$("#nameBDiv").html(data.nameB);
					setNum = data.setNum;
					$("#setInfoDiv").html("第"+setNum+"节");
					totalMinute = data.totalMinute;
					$("#totalTimeDiv").html(totalMinute+":00");
					sideSecond = data.sideTime;
					$("#sideTimeDiv").html(sideSecond);
					$("#matchId").attr("readonly",true);
					$("#createMatchForm>input").attr("readonly",true);
					$("#scoreControlMainDiv").show();
				});
		else
			alert("请将信息填写完整");
		$(this).blur();
	});
	$("#startBtn").click(function(){
		//开始/暂停按钮
		if( $(this).val() == "开始" ){
			totalTimeId = setInterval("countDown1Sceond()",1000);
			$(this).val("暂停");
		}
		else if( $(this).val() == "暂停" ){
			clearInterval(totalTimeId);
			$(this).val("开始");
		}
		else if( $(this).val() == "复位" ){
			resetTime();
			$(this).val("开始");
		}
		$(this).blur();
	});
	$("#changeBtn").click(function(){
		//切换按钮
		if( totalMinute > 0 )
			sideSecond = 24;
		else if( totalMinute == 0 && totalSecond > 24 )
			sideSecond = 24;
		else if( totalMinute == 0 && totalSecond <= 24 )
			sideSecond = totalSecond;
		updateTime();
		$("#sideTimeDiv").html(sideSecond<10?("0"+sideSecond):sideSecond);
		$(this).blur();
	});
	$("input[id*='+']").click(function(){
		//+1+2+3按钮
		clearInterval(totalTimeId);
		$("#startBtn").val("开始");
		var $this = $(this);
		var score = parseInt($this.val().substr(1,2),10);
		var side = $this.attr("id").substr(0,1);
		$("#changeBtn").click();
		updateScore(score,side);
		$(this).blur();
	});
	$("input[id*='RevokeScoreBtn']").click(function(){
		//撤销比分按钮
		clearInterval(totalTimeId);
		$("#startBtn").val("开始");
		var $this = $(this);
		$.post( "scoreControl_revokeBasketballScore.action", { 
			matchId: $("#matchId").val(),
		})
		.success(function(data){
			//修改双方比分
			$("#scoreADiv").html(data.scoreA);
			$("#scoreBDiv").html(data.scoreB);
		});
		$(this).blur();
	});
	$("input[id*='RevokeTimeBtn']").click(function(){
		//撤销比分按钮
		clearInterval(totalTimeId);
		$("#startBtn").val("开始");
		var $this = $(this);
		$.post( "scoreControl_revokeBasketballTime.action", { 
			matchId: $("#matchId").val(),
		})
		.success(function(data){
			//修改双方比分
			//修改节数
			//修改时间
			$("#scoreADiv").html(data.scoreA);
			$("#scoreBDiv").html(data.scoreB);
			if( data.nextSet != undefined ){
				setNum = data.nextSet;
				$("#setInfoDiv").html("第"+setNum+"节");
				totalMinute = data.totalMinute;
				totalSecond = data.totalSecond;
				$("#totalTimeDiv").html(totalMinute+":"+(totalSecond<10?("0"+totalSecond):totalSecond));
				sideSecond = data.sideTime;
				$("#sideTimeDiv").html(sideSecond<10?("0"+sideSecond):sideSecond);
			}
		});
		$(this).blur();
	});
});
function countDown1Sceond(){
	if( totalMinute > 0 && totalSecond > 0 ){
		//时间>1分钟,秒数大于1，直接减
		if( sideSecond > 1 ){
			totalSecond--;
			sideSecond--;
		}
		else if( sideSecond == 1 ){
			totalSecond--;
			sideSecond--;
			clearInterval(totalTimeId);
			$("#startBtn").val("开始");
		}
		else if( sideSecond == 0 ){
			sideSecond = 23;
			totalSecond--;
		}
	}
	else if( totalMinute > 0 && totalSecond == 0 ){
		//借位后再减
		if( sideSecond > 1 ){
			totalSecond = 59;
			totalMinute--;
			sideSecond--;
		}
		else if( sideSecond == 1 ){
			totalSecond = 59;
			totalMinute--;
			sideSecond--;
			clearInterval(totalTimeId);
			$("#startBtn").val("开始");
		}
		else if( sideSecond == 0 ){
			totalSecond = 59;
			totalMinute--;
			sideSecond = 23;
		}
	}
	else if( totalMinute == 0 && totalSecond > 24 ){
		//直接减
		if( sideSecond > 1 ){
			totalSecond--;
			sideSecond--;
		}
		else if( sideSecond == 1 ){
			totalSecond--;
			sideSecond--;
			clearInterval(totalTimeId);
			$("#startBtn").val("开始");
		}
		else if( sideSecond == 0 ){
			sideSecond = 23;
			totalSecond--;
		}
	}
	else if( totalMinute == 0 && totalSecond <= 24 ){
		if( sideSecond > 1 ){
			totalSecond--;
			sideSecond--;
		}
		else if( sideSecond == 1 ){
			if( totalSecond == 1 )
				$("#startBtn").val("复位");
			else
				$("#startBtn").val("开始");
			totalSecond--;
			sideSecond--;
			clearInterval(totalTimeId);
		}
		else if( sideSecond == 0 ){
			totalSecond--;
			sideSecond = totalSecond;
		}
	}
	$("#totalTimeDiv").html(totalMinute+":"+(totalSecond<10?("0"+totalSecond):totalSecond));
	$("#sideTimeDiv").html(sideSecond<10?("0"+sideSecond):sideSecond);
	updateTime();
}
function resetTime(){
	$.post( "scoreControl_resetBasketballTime.action", { 
		matchId: $("#matchId").val(),
	})
	.success(function(data){
		if( data.msg == "比赛结束" ){
			$("#setInfoDiv").html(data.msg);
			$("#startBtn").attr("disabled",true);
			$("#changeBtn").attr("disabled",true);
		}
		else if( data.msg == "数据异常" ){
			alert("数据异常");
		}
		else{
			setNum = data.setNum;
			$("#setInfoDiv").html("第"+setNum+"节");
			totalMinute = data.totalMinute;
			totalSecond = 0;
			$("#totalTimeDiv").html(totalMinute+":00");
			sideSecond = data.sideTime;
			$("#sideTimeDiv").html(sideSecond<10?("0"+sideSecond):sideSecond);
		}
	});
}
function updateTime(){
	$.post( "scoreControl_updateBasketballTime.action", { 
		matchId: $("#matchId").val(),
		currentScoreA: $("#scoreADiv").html(),
		currentScoreB: $("#scoreBDiv").html(),
		currentSetNum: setNum,
		currentRestTimeMinute: totalMinute,
		currentRestTimeSecond: totalSecond,
		currentRestSideTime: sideSecond
	})
	.success(function(data){
		if(data.setEnd){
			//一局结束
			clearInterval(totalTimeId);
			$("#startBtn").val("复位");
		}
	});
}
function updateScore(score,side){
	$.post( "scoreControl_updateBasketballScore.action", { 
		matchId: $("#matchId").val(),
		score: score,
		side: side,
		oldScoreA: $("#scoreADiv").html(),
		oldScoreB: $("#scoreBDiv").html(),
		currentSetNum: setNum,
		currentRestTimeMinute: totalMinute,
		currentRestTimeSecond: totalSecond,
		currentRestSideTime: sideSecond
	})
	.success(function(data){
		if(data.success){
			$("#score"+side+"Div").html(data.newScore);
		}
	});
}