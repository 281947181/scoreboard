<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>我来赛</title>
	<link rel="stylesheet" type="text/css" href="css/base.css">
	<style type="text/css">
		body{
			width:1900px;
			height:1060px;
			overflow:hidden; 
		}
		img{
			border-radius: 50%;
			-webkit-border-radius: 50%;
			-moz-border-radius: 50%;
		}
	</style>
	
</head>

<body>
	<div id="topDiv" style="width:1900px;height:300px;"></div>
	<div id="middleDiv" style="width:1900px;height:520px;"></div>
	<div id="bottomDiv" style="width:1900px;height:240px;position:relative;">
		<div style="width:190px;height:240px;position:absolute;left:0px;top:0px;"></div>
		<div id="leftPhotoDiv" style="position:absolute;left:300px;top:0px;z-index:2;background-image:url('resources/images/backgroundlh.png');width:154px;height:240px;">
			<img id="leftPhotoImg" src="" style="width:140px;height:140px;margin-top:67px;margin-left:10px;">
		</div>
		<div id="centerDiv" style="position:absolute;left:454px;top:0px;z-index:2;background-image:url('resources/images/backgroundfinish.png');background-repeat:no-repeat;width:996px;height:240px;">
			<div style="width:498px;height:84px;float:left;">
				<div style="width:150px;height:84px;float:left;">
					<div style="width:150px;height:42px;"></div>
					<div id="leftPersonSupportNumDiv" style="color:red;width:150px;height:42px;font-size:35px;font-weight:bold;line-height:42px;text-align:center;">0</div>
				</div>
				<div style="width:64px;height:84px;float:left;">
					<div style="width:64px;height:42px;"></div>
					<div id="leftPersonHeartDiv" style="width:64px;height:42px;background-image:url('resources/images/redheart.png');background-repeat:no-repeat;"></div>
				</div>
				<div id="leftPersonNameDiv" style="color:red;width:237px;height:50px;float:left;font-size:35px;font-weight:bold;line-height:50px;">张三三/李四四</div>
			</div>
			<div style="width:498px;height:84px;float:left;">
				
				<div style="width:150px;height:84px;float:right;">
					<div style="width:150px;height:42px;"></div>
					<div id="rightPersonSupportNumDiv" style="color:blue;width:150px;height:42px;float:right;font-size:35px;font-weight:bold;line-height:42px;text-align:center;">0</div>
				</div>
				<div style="width:64px;height:84px;float:right;">
					<div style="width:64px;height:42px;"></div>
					<div id="rightPersonHeartDiv" style="width:64px;height:42px;background-image:url('resources/images/blueheart.png');background-repeat:no-repeat;background-position:17px 0px;"></div>
				</div>
				<div id="rightPersonNameDiv" style="color:blue;width:237px;height:50px;float:right;font-size:35px;font-weight:bold;line-height:50px;text-align:right;">张三三/李四四</div>
			</div>
			<div style="width:996px;height:28px;float:left;"></div>
			<div style="width:996px;height:53px;float:left;">
				<div style="width:496px;height:53px;float:left;">
					<div id="leftPersonPercentageDiv" style="position:absolute ;width:405px;height:53px;text-align:center;font-size:40px;line-height:53px;color:white;font-weight:bold;">
						50.00%
					</div>
					<div style="background-image:url('resources/images/hr.png');width:12px;height:53px;float:left;"></div>
					<div id="leftPersonEnergyDiv" style="background-image:url('resources/images/rc.png');background-repeat:repeat-x;width:190px;height:53px;float:left;"></div>
					<div style="background-image:url('resources/images/fr.png');width:8px;height:53px;float:left;"></div>
				</div>
				<div style="width:496px;height:53px;float:left;">
					<div id="rightPersonPercentageDiv" style="position:absolute;left:589px; width:405px;height:53px;text-align:center;font-size:40px;line-height:53px;color:white;font-weight:bold;">
						50.00%
					</div>
					<div id="rightEnergyEmptyDiv" style="width:278px;height:53px;float:left;"></div>
					<div style="background-image:url('resources/images/hb.png');width:13px;height:53px;float:left;"></div>
					<div id="rightPersonEnergyDiv" style="background-image:url('resources/images/cb.png');background-repeat:repeat-x;width:190px;height:53px;float:left;"></div>
					<div style="background-image:url('resources/images/fb.png');width:12px;height:53px;float:left;"></div>
				</div>
			</div>
		</div>
		<div id="rightPhotoDiv" style="position:absolute;left:1450px;top:0px;z-index:2;background-image:url('resources/images/backgroundrh.png');width:150px;height:240px;">
			<img id="rightPhotoImg" src="" style="width:140px;height:140px;margin-top:69px;margin-left:-1px;">
		</div>
		<!-- <div id="doubleRightPhotoDiv" style="position:absolute;left:1570px;top:0px;z-index:1;background-image:url('resources/images/backgroundrh.png');width:150px;height:240px;">
			<img id="doubleRightPhotoImg" src="resources/images/2.jpeg" style="width:140px;height:140px;margin-top:69px;margin-left:-1px;">
		</div> -->
		
		<div style="width:190px;height:240px;position:absolute;left:1720px;top:0px;">
			<img id="moneyImg" src="resources/images/money.png" style="display:none;width:180px;height:180px;margin-top:50px;margin-left:5px;border-radius:0;-webkit-border-radius:0;-moz-border-radius:0;">
		</div>
		
	</div>
	<div id="rocketDiv" style="position:absolute;left:1900px;top:0px;width:1900px;height:160px;display:none;">
		<div style="width:300spx;height:160px;float:left;"></div>
		<div id="rocketHeadDiv" style="width:325px;height:160px;float:left;background-image:url('resources/images/rocket_head.png');background-position:0px -91px;background-repeat:no-repeat;"></div>
		<div id="rocketMiddleDiv" style="text-align:center;font-size:50px;line-height:151px;width:1000px;height:160px;float:left;background-image:url('resources/images/rocket_1.png');background-position:0px 9px;background-repeat:repeat-x;"></div>
		<div id="rocketTailDiv" style="width:27px;height:160px;float:left;background-image:url('resources/images/rocket_tail.png');background-position:0px 8px;background-repeat:repeat-x;"></div>
	</div>
	<div style="display:none;">
		<input id="recycle_id" value="${matchId}"/>
		<input id="a_bet" value="0"/>	<!--投注数，算能量条长度 -->
		<input id="b_bet" value="0"/>	<!--投注数，算能量条长度 -->
		<input id="like_a" value="0"/>	<!--点赞数 -->
		<input id="like_b" value="0"/>	<!--点赞数 -->
	</div>
</body>
<script type="text/javascript" src="resources/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
<script src="js/mo.min.js"></script>
<script src="js/countUp.min.js"></script>
<script src="resources/js/tennis_wolaisai.js"></script>
</html>