<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Tennis-Statistics</title>
	</head>
	<body style="width:1900px;height:1060px;">
		<input id="matchType" type="hidden" value="${matchType}"/>
		<input id="matchId" type="hidden" value="${matchId}"/>
		<input id="council" type="hidden" value="${council}"/>
		<input id="set" type="hidden" value="${set}"/>
		<div id="statistics"></div>
	</body>
	<script type="text/javascript" src="resources/js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="resources/js/jquery.tennis-statistics.js"></script>
	<script type="text/javascript" src="resources/js/statistics.js"></script>
</html>