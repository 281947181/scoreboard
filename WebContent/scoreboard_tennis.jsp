<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Tennis-Score</title>
	</head>
	<body style="width:1900px;height:1060px;">
		<input id="matchType" type="hidden" value="${matchType}"/>
		<input id="matchId" type="hidden" value="${matchId}"/>
		<div id="scoreBoard"></div>
	</body>
	<script type="text/javascript" src="resources/js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="resources/js/jquery.tennis-scoreboard.js"></script>
	<script type="text/javascript" src="resources/js/scoreboard.js"></script>
</html>