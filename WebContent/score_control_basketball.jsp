<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Score Control--Basketball</title>
	</head>
	<body>
		<div id="createMatchDiv" style="margin-bottom:50px;">
			比赛ID：<input id="matchId"/>
			<input type="button" id="queryBtn" value="载入比赛" disabled="disabled"/>
			<form id="createMatchForm">
				节数：<input id="setNum" name="setNum" value=""/>
				单节时长：<input id="singleSetTime" name="singleSetTime" value=""/>
				主队队名 ：<input id="nameA" name="nameA" value=""/>
				客队队名：<input id="nameB" name="nameB" value=""/>
				<input type="button" id="createBtn" value="创建比赛"/>
			</form>
		</div>
		<div id="scoreControlMainDiv">
			<div id="scoreControlADiv" style="float:left;width:40%;text-align:center;">
				<div id="nameADiv" style="float:left;width:60%;height:40px;line-height:40px;font-size:35px;text-align:right;"></div>
				<div id="scoreADiv" style="float:left;width:40%;height:40px;line-height:40px;font-size:35px;text-align:center;">0</div>
				<div id="buttonADiv" style="float:left;width:100%;margin-top:30px;">
					<input type="button" id="A+1Btn" value="+1" style="width:100px;height:60px;font-size:35px;"/>
					<input type="button" id="A+2Btn" value="+2" style="width:100px;height:60px;font-size:35px;"/>
					<input type="button" id="A+3Btn" value="+3" style="width:100px;height:60px;font-size:35px;"/><br/>
					<input type="button" id="ARevokeScoreBtn" value="撤销比分" style="width:155px;height:60px;font-size:35px;margin-top: 10px;"/>
					<input type="button" id="ARevokeTimeBtn" value="撤销时间" style="width:155px;height:60px;font-size:35px;margin-top: 10px;"/>
				</div>
			</div>
			<div id="scoreControlDiv" style="float:left;width:20%;text-align:center;">
				<div id="setInfoDiv" style="float:left;width:100%;height:40px;line-height:40px;font-size:35px;text-align:center;margin-bottom:10px;"></div>
				<div id="totalTimeDiv" style="float:left;width:50%;height:40px;line-height:40px;font-size:35px;text-align:center;"></div>
				<div id="sideTimeDiv" style="float:left;width:50%;height:40px;line-height:40px;font-size:35px;text-align:center;"></div>
				<div id="buttonControlDiv" style="float:left;width:100%;">
					<input type="button" id="startBtn" value="开始" style="width:120px;height:60px;font-size:35px;"/>
					<input type="button" id="changeBtn" value="切换" style="width:120px;height:60px;font-size:35px;"/>
				</div>
			</div>
			<div id="scoreControlBDiv" style="float:left;width:40%;text-align:center;">
				<div id="scoreBDiv" style="float:left;width:40%;height:40px;line-height:40px;font-size:35px;text-align:center;">0</div>
				<div id="nameBDiv" style="float:left;width:60%;height:40px;line-height:40px;font-size:35px;text-align:left;"></div>
				<div id="buttonBDiv" style="float:left;width:100%;margin-top:30px;">
					<input type="button" id="B+1Btn" value="+1" style="width:100px;height:60px;font-size:35px;"/>
					<input type="button" id="B+2Btn" value="+2" style="width:100px;height:60px;font-size:35px;"/>
					<input type="button" id="B+3Btn" value="+3" style="width:100px;height:60px;font-size:35px;"/><br/>
					<input type="button" id="BRevokeScoreBtn" value="撤销比分" style="width:155px;height:60px;font-size:35px;margin-top: 10px;"/>
					<input type="button" id="BRevokeTimeBtn" value="撤销时间" style="width:155px;height:60px;font-size:35px;margin-top: 10px;"/>
				</div>
			</div>
			<div id="scoreBoard"></div>
		</div>
		
	</body>
	<script type="text/javascript" src="resources/js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="resources/js/score_control_basketball.js"></script>
</html>