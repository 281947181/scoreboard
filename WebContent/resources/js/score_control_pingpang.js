var setA = 0, setB = 0, setNum = 0;
var scoreA = 0, scoreB = 0, singleSetPoint = 0;;
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
	$("#chooseServeDiv").hide();
	$("#scoreControlMainDiv").hide();
	$("#createBtn").click(function(){
		if( $("#setNum").val() != "" && $("#singleSetPoint").val() != "" && $("#nameA").val() != "" && $("#nameB").val() != "" )
			$.post("scoreControl_createPingpangMatch.action",
				$("#createMatchForm").serialize(),
				function(data){
					$("#matchId").val(data.matchId);
					$("#createBtn").attr("disabled",true);
					$("#nameADiv").html(data.nameA);
					$("#nameBDiv").html(data.nameB);
					setNum = data.setNum;
					singleSetPoint = data.singleSetPoint;
					$("#matchId").attr("readonly",true);
					$("#createMatchForm>input").attr("readonly",true);
					$("#chooseServeDiv").show();
				});
		else
			alert("请将信息填写完整");
		$(this).blur();
	});
	$("input[id*='Serve']").click(function(){
		//选择发球方
		var $this = $(this);
		var side = $this.attr("id").substr(0,1);
		$.post( "scoreControl_chooseServePingpang.action", { 
			matchId: $("#matchId").val(),
			side: side
		})
		.success(function(data){
			if( data.success ){
				$this.blur();
				$("#chooseServeDiv").hide();
				$("#scoreControlMainDiv").show();
				$("#name"+data.firstServe+"Div").css({"backgroundColor":"red","color":"white"});
				$("#name"+data.notServe+"Div").css({"backgroundColor":"white","color":"black"});
				$("#setInfoDiv").html("局分 0 : 0");
				$("#pointInfoDiv").html("比分 0 : 0");
			}
			else{
				alert("数据错误, 请重新创建比赛");
				location.reload();
			}
		});
		
	});
	$("input[id*='ScoreBtn']").click(function(){
		//得分按钮
		var $this = $(this);
		var side = $this.attr("id").substr(0,1);
		updateScore(side);
		$(this).blur();
	});
	$("#revokeBtn").click(function(){
		//撤销按钮
		var $this = $(this);
		revokeScore();
		$(this).blur();
	});
	$("#nextBtn").click(function(){
		//撤销按钮
		var $this = $(this);
		nextSet();
		$(this).blur();
	});
});
function nextSet(){
	$.post( "scoreControl_nextSetPingpang.action", { 
		matchId: $("#matchId").val()
	})
	.success(function(data){
		if(data.success){
			if( data.matchEnd ){
				$("#pointInfoDiv").html("比赛结束");
				$("input[id*='ScoreBtn']").attr("disabled",true);
				$("#nameADiv").css({"backgroundColor":"white","color":"black"});
				$("#nameBDiv").css({"backgroundColor":"white","color":"black"});
			}
			else{
				$("#pointInfoDiv").html("比分 0 : 0");
				$("input[id*='ScoreBtn']").removeAttr("disabled");
				$("#name"+data.nextServe+"Div").css({"backgroundColor":"red","color":"white"});
				$("#name"+data.notServe+"Div").css({"backgroundColor":"white","color":"black"});
			}
			$("#setInfoDiv").html("局分 "+data.setA+" : "+data.setB);
			$("#nextBtn").attr("disabled",true);
			$("#revokeBtn").attr("disabled",true);
		}
	});
}
function revokeScore(){
	$.post( "scoreControl_revokePingpangScore.action", { 
		matchId: $("#matchId").val()
	})
	.success(function(data){
		if(data.success){
			$("#pointInfoDiv").html("比分 "+data.scoreA+" : "+data.scoreB);
			$("input[id*='ScoreBtn']").removeAttr("disabled");
			$("#nextBtn").attr("disabled",true);
			$("#name"+data.nextServe+"Div").css({"backgroundColor":"red","color":"white"});
			$("#name"+data.notServe+"Div").css({"backgroundColor":"white","color":"black"});
			if( data.scoreA == 0 && data.scoreB == 0 )
				$("#revokeBtn").attr("disabled",true);
			else
				$("#revokeBtn").removeAttr("disabled");
		}
	});
}
function updateScore(side){
	$.post( "scoreControl_updatePingpangScore.action", { 
		matchId: $("#matchId").val(),
		side: side
	})
	.success(function(data){
		if(data.success){
			$("#revokeBtn").removeAttr("disabled");
			$("#pointInfoDiv").html("比分 "+data.scoreA+" : "+data.scoreB);
			if( data.setEnd ){
				$("input[id*='ScoreBtn']").attr("disabled",true);
				$("#nextBtn").removeAttr("disabled");
				$("#nameADiv").css({"backgroundColor":"white","color":"black"});
				$("#nameBDiv").css({"backgroundColor":"white","color":"black"});
			}
			else{
				$("#name"+data.nextServe+"Div").css({"backgroundColor":"red","color":"white"});
				$("#name"+data.notServe+"Div").css({"backgroundColor":"white","color":"black"});
			}
		}
	});
}