<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Score Control--PingPang</title>
	</head>
	<body>
		<div id="createMatchDiv" style="margin-bottom:50px;">
			比赛ID：<input id="matchId"/>
			<input type="button" id="queryBtn" value="载入比赛" disabled="disabled"/>
			<form id="createMatchForm">
				局数：<input id="setNum" name="setNum" value=""/>
				单局比分：<input id="singleSetPoint" name="singleSetPoint" value=""/>
				主队队员 ：<input id="nameA" name="nameA" value=""/>
				客队队员：<input id="nameB" name="nameB" value=""/>
				<input type="button" id="createBtn" value="创建比赛"/>
			</form>
		</div>
		<div id="chooseServeDiv" style="text-align: center;">
			最先发球：
			<input type="button" id="AServe" value="主队发球" style="width:150px;height:60px;font-size:35px;"/>
			<input type="button" id="BServe" value="客队发球" style="width:150px;height:60px;font-size:35px;"/>
		</div>
		<div id="scoreControlMainDiv" style="margin-top:100px;">
			<div id="scoreControlADiv" style="float:left;width:35%;text-align:center;margin-top:60px;">
				<div id="buttonADiv" style="float:right;width:25%;">
					<input type="button" id="AScoreBtn" value="得分" style="width:100px;height:50px;font-size:35px;"/>
				</div>
				<div id="nameADiv" style="float:right;width:50%;height:40px;line-height:40px;font-size:30px;text-align:center;"></div>
			</div>
			<div id="scoreControlDiv" style="float:left;width:30%;text-align:center;">
				<div id="setInfoDiv" style="float:left;width:100%;height:35px;line-height:35px;font-size:30px;text-align:center;"></div>
				<div id="pointInfoDiv" style="float:left;width:100%;height:50px;line-height:50px;font-size:45px;text-align:center;margin-top:20px;"></div>
				<div id="buttonControlDiv" style="float:left;width:100%;margin-top:20px;">
					<input type="button" id="nextBtn" value="下一局" disabled="disabled" style="width:120px;height:60px;font-size:35px;"/><br/><br/>
					<input type="button" id="revokeBtn" value="撤销" disabled="disabled" style="width:120px;height:60px;font-size:35px;"/>
				</div>
			</div>
			<div id="scoreControlBDiv" style="float:left;width:35%;text-align:center;margin-top:60px;">
				<div id="buttonBDiv" style="float:left;width:25%;">
					<input type="button" id="BScoreBtn" value="得分" style="width:100px;height:50px;font-size:35px;"/>
				</div>
				<div id="nameBDiv" style="float:left;width:50%;height:40px;line-height:40px;font-size:30px;text-align:center;"></div>
			</div>
			<div id="scoreBoard"></div>
		</div>
		
	</body>
	<script type="text/javascript" src="resources/js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="resources/js/score_control_pingpang.js"></script>
</html>