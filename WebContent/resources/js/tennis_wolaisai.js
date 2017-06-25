/*;(function(window) {

//	'use strict';

	// taken from mo.js demos
	function isIOSSafari() {
		var userAgent;
		userAgent = window.navigator.userAgent;
		return userAgent.match(/iPad/i) || userAgent.match(/iPhone/i);
	};

	// taken from mo.js demos
	function isTouch() {
		var isIETouch;
		isIETouch = navigator.maxTouchPoints > 0 || navigator.msMaxTouchPoints > 0;
		return [].indexOf.call(window, 'ontouchstart') >= 0 || isIETouch;
	};
	
	// taken from mo.js demos
	var isIOS = isIOSSafari(),
		clickHandler = isIOS || isTouch() ? 'touchstart' : 'click';

	function extend( a, b ) {
		for( var key in b ) { 
			if( b.hasOwnProperty( key ) ) {
				a[key] = b[key];
			}
		}
		return a;
	}

	function Animocon(el, options) {
		this.el = el;
		this.options = extend( {}, this.options );
		extend( this.options, options );

		this.timeline = new mojs.Timeline();
		
		for(var i = 0, len = this.options.tweens.length; i < len; ++i) {
			this.timeline.add(this.options.tweens[i]);
		}

		var self = this;
		$(this.el).bind("start-animation",function(){
			self.timeline.start();
		});
	}

	Animocon.prototype.options = {
		tweens : [
			new mojs.Burst({
				shape : 'circle',
				isRunLess: true
			})
		]
	};
	function createAnimation(targetDivString,radiusArray,strokeWidthArray,strokeWidthValueArray,shiftXArray,shiftYArray){
		var targetDiv = document.querySelector("#"+targetDivString);
		new Animocon(targetDiv, {
			tweens : [
				new mojs.Transit({
					parent: targetDiv,
					duration: 1300,
					delay: 0,
					type: 'circle',
//					radius: {0: radiusArray[1]},
					radius: {0: 100},
					fill: 'transparent',
					stroke: '#F35186',
					strokeWidth: {40: 0},
//					strokeWidth: strokeWidthArray[1],
					opacity: 0.2,
					x: $("#"+targetDivString).width()/2-radiusArray[1]-strokeWidthValueArray[1],     
					y: $("#"+targetDivString).height()/2,
					shiftX : -90,
//					shiftX : shiftXArray[1],
					shiftY : -30,
//					shiftY : shiftYArray[1],
					isRunLess: true,
					easing: mojs.easing.sin.out
				}),
				new mojs.Transit({
					parent: targetDiv,
					duration: 500,
					delay: 550,
					type: 'circle',
					radius: {0: 70},
					fill: 'transparent',
					stroke: '#F35186',
					strokeWidth: {55: 0},
					opacity: 0.2,
					x: $("#"+targetDivString).width()/2-radiusArray[1]-strokeWidthValueArray[1],     
					y: $("#"+targetDivString).height()/2,
					shiftX : 40,
					shiftY : -170,
					isRunLess: true,
					easing: mojs.easing.sin.out
				}),
				new mojs.Transit({
					parent: targetDiv,
					duration: 500,
					delay: 800,
					type: 'circle',
					radius: {0: 100},
					fill: 'transparent',
					stroke: '#F35186',
					strokeWidth: {40: 0},
					opacity: 0.2,
					x: $("#"+targetDivString).width()/2-radiusArray[1]-strokeWidthValueArray[1],     
					y: $("#"+targetDivString).height()/2,
					shiftX : 120,
					shiftY : -30,
					isRunLess: true,
					easing: mojs.easing.sin.out
				}),
				// icon scale animation
				new mojs.Tween({
					duration : 1200,
					easing: mojs.easing.ease.out,
					onUpdate: function(progress) {
						if(progress > 0.3) {
							var elasticOutProgress = mojs.easing.elastic.out(1.43*progress-0.43);
							targetDiv.style.WebkitTransform = targetDiv.style.transform = 'scale3d(' + elasticOutProgress + ',' + elasticOutProgress + ',1)';
						}
						else {
							targetDiv.style.WebkitTransform = targetDiv.style.transform = 'scale3d(0,0,1)';
						}
					}
				})
			],
		});
	}
	function init() {
		var radiusArray = [40,20,30,20,20,45,25];
		var strokeWidthValueArray = [35,15,15,15,15,15,15];
		var maxX = 150;
		var minX = -150;
		var maxY = -50;
		var minY = -200;
		var shiftXArray = [];
		var shiftYArray = [];
		var strokeWidthArray = [];
		for(var i = 0; i < radiusArray.length; i++){
			var strokeWidth = {};
			strokeWidth[strokeWidthValueArray[i]+""] =  0;
			strokeWidthArray[i] = strokeWidth;
			shiftXArray[i] = Math.random()*(maxX-minX)+minX;
			shiftYArray[i] = Math.random()*(maxY-minY)+minY;
		}
		createAnimation("leftPersonPhotoDiv",radiusArray,strokeWidthArray,strokeWidthValueArray,shiftXArray,shiftYArray);
		createAnimation("rightPersonPhotoDiv",radiusArray,strokeWidthArray,strokeWidthValueArray,shiftXArray,shiftYArray);
	}
	
	init();

})(window);*/

//数字特效
var options = {
	useEasing : true, 
	useGrouping : true, 
	separator : '', 
	decimal : '.', 
	prefix : '', 
	suffix : ''
};
//爱心浮动扩展
(function ($) {
	$.extend({
		tipsBox: function (options) {
			options = $.extend({
				obj: null,  //jq对象，要在那个html标签上显示
				str: "+1",  //字符串，要显示的内容;也可以传一段html，如: "<b style='font-family:Microsoft YaHei;'>+1</b>"
				startSize: "12px",  //动画开始的文字大小
				endSize: "50px",    //动画结束的文字大小
				interval: 600,  //动画时间间隔
				color: "red",    //文字颜色
				delay: 0,
				left: 0,
				endTop: 0,
				leftRight: null,
				callback: function () { }    //回调函数
			}, options);
			setTimeout(function(){ 
				heartAnimate(options);
			}, options.delay);
		}
	});
})(jQuery);
$(function(){
	var recycle_id = $("#recycle_id").val();
	$.getJSON("http://www.iaasports.com/index.php?g=admin&m=groupMS&a=TestJsonp&game_id="+recycle_id+"&jsoncallback=?",
	function(data){ 
		$("#leftPhotoImg").attr("src",data.player_a1_img.indexOf("/")==0?"http://www.iaasports.com"+data.player_a1_img:data.player_a1_img);
		$("#rightPhotoImg").attr("src",data.player_b1_img.indexOf("/")==0?"http://www.iaasports.com"+data.player_b1_img:data.player_b1_img);
		if( data.play_type == "1" ){
			$("#doubleLeftPhotoDiv").remove();
			$("#doubleRightPhotoDiv").remove();
			$("#leftPersonNameDiv").html(data.player_a1_name);
			$("#rightPersonNameDiv").html(data.player_b1_name);
		}
		else if( data.play_type == "2" ){
			$("#leftPersonNameDiv").html(data.player_a1_name+"/"+data.player_a2_name);
			$("#rightPersonNameDiv").html(data.player_b1_name+"/"+data.player_b2_name);
			$("#leftPhotoDiv").before("<div id=\"doubleLeftPhotoDiv\" style=\"position:absolute;left:190px;top:0px;z-index:1;background-image:url('resources/images/backgroundlh.png');width:140px;height:240px;\">" +
				"<img id=\"doubleLeftPhotoImg\" src=\""+(data.player_a2_img.indexOf("/")==0?"http://www.iaasports.com"+data.player_a2_img:data.player_a2_img)+"\" style=\"width:140px;height:140px;margin-top:67px;margin-left:10px;\"></div>");
			$("#rightPhotoDiv").after("<div id=\"doubleRightPhotoDiv\" style=\"position:absolute;left:1570px;top:0px;z-index:1;background-image:url('resources/images/backgroundrh.png');width:150px;height:240px;\">" +
				"<img id=\"doubleRightPhotoImg\" src=\""+(data.player_b2_img.indexOf("/")==0?"http://www.iaasports.com"+data.player_b2_img:data.player_b2_img)+"\" style=\"width:140px;height:140px;margin-top:69px;margin-left:-1px;\">");
		}
		//能量条,点赞数更新
		if( parseInt(data.a_bet_coins) != 0 || parseInt(data.b_bet_coins) != 0 ){
			var total = parseInt(data.a_bet_coins)+parseInt(data.b_bet_coins);
			var percentage = parseFloat(data.a_bet_coins)/parseFloat(total);
			changeEnergyPercentage(percentage,0.5,data.a_bet_coins,data.b_bet_coins);
			$("#a_bet").val(data.a_bet_coins);
			$("#b_bet").val(data.b_bet_coins);
		}
		if( parseInt(data.like_a) != 0 ){
			heartFloat("left","#leftPersonHeartDiv","red",0,parseInt(data.like_a));
			$("#like_a").val(data.like_a);
		}
		if( parseInt(data.like_b) != 0 ){
			heartFloat("right","#rightPersonHeartDiv","blue",0,parseInt(data.like_b));
			$("#like_b").val(data.like_b);
		}
		showRocketAndMoney("张三","李四",2000);
//		$("#leftPersonPhotoDiv").trigger("start-animation");
//		$("#rightPersonPhotoDiv").trigger("start-animation");
	});
	setInterval("updateData()", 5000);
});
function updateData(){
	var recycle_id = $("#recycle_id").val();
	$.getJSON("http://www.iaasports.com/index.php?g=admin&m=groupMS&a=TestJsonp&game_id="+recycle_id+"&jsoncallback=?",
	function(data){ 
		var old_a_bet = $("#a_bet").val();
		var old_b_bet = $("#b_bet").val();
		var old_like_a = $("#like_a").val();
		var old_like_b = $("#like_b").val();
		//能量条更新
		if(parseInt(data.a_bet_coins) != parseInt(old_a_bet) || parseInt(data.b_bet_coins) != parseInt(old_b_bet) ){
			var oldTotal = parseInt(old_a_bet)+parseInt(old_b_bet);
			var oldPercentage = parseFloat(old_a_bet)/parseFloat(oldTotal);
			var total = parseInt(data.a_bet_coins)+parseInt(data.b_bet_coins);
			var percentage = parseFloat(data.a_bet_coins)/parseFloat(total);
			changeEnergyPercentage(percentage,oldPercentage,data.a_bet_coins,data.b_bet_coins);
			$("#a_bet").val(data.a_bet_coins);
			$("#b_bet").val(data.b_bet_coins);
		}
		if( parseInt(old_like_a) != parseInt(data.like_a) ){
			heartFloat("left","#leftPersonHeartDiv","red",parseInt(old_like_a),parseInt(data.like_a));
			$("#like_a").val(data.like_a);
		}
		if( parseInt(old_like_b) != parseInt(data.like_b) ){
			heartFloat("right","#rightPersonHeartDiv","blue",parseInt(old_like_b),parseInt(data.like_b));
			$("#like_b").val(data.like_b);
		}
	});
}
function showRocketAndMoney(giver,receiver,x){
	$("#moneyImg").fadeIn(1000,function(){
		rocketIn(giver+" 送 "+receiver+" "+x+" 赛豆！");
		setTimeout(function(){ 
			$("#moneyImg").fadeOut(1000);
		}, 10000);
	});
}
function getHeartObj(leftRight,div,color){
	var heartObj = [{
		obj: $(div),
		leftRight: leftRight+"1",
		left: -10,
		interval: 3900,
		endTop: 300,
		str: "<img src='resources/images/"+color+"heart.png' style='width:80px;height:80px;'>"
	},{
		obj: $(div),
		leftRight: leftRight+"2",
		delay: 200,
		left: 10,
		interval: 3600,
		endTop: 360,
		str: "<img src='resources/images/"+color+"heart.png' style='width:90px;height:90px;'>"
	},{
		obj: $(div),
		leftRight: leftRight+"3",
		delay: 400,
		left: -20,
		interval: 3300,
		endTop: 330,
		str: "<img src='resources/images/"+color+"heart.png' style='width:70px;height:70px;'>"
	},{
		obj: $(div),
		leftRight: leftRight+"4",
		delay: 600,
		left: 20,
		interval: 3000,
		endTop: 210,
		str: "<img src='resources/images/"+color+"heart.png' style='width:60px;height:60px;'>"
	},{
		obj: $(div),
		leftRight: leftRight+"5",
		delay: 800,
		left: -30,
		interval: 2700,
		endTop: 150,
		str: "<img src='resources/images/"+color+"heart.png' style='width:80px;height:80px;'>"
	},{
		obj: $(div),
		leftRight: leftRight+"6",
		delay: 1000,
		left: 25,
		interval: 2400,
		endTop: 210,
		str: "<img src='resources/images/"+color+"heart.png' style='width:50px;height:50px;'>"
	},{
		obj: $(div),
		leftRight: leftRight+"7",
		delay: 1200,
		left: -40,
		interval: 2100,
		endTop: 170,
		str: "<img src='resources/images/"+color+"heart.png' style='width:40px;height:40px;'>"
	},{
		obj: $(div),
		leftRight: leftRight+"8",
		delay: 1400,
		left: 35,
		interval: 1800,
		endTop: 200,
		str: "<img src='resources/images/"+color+"heart.png' style='width:80px;height:80px;'>"
	},{
		obj: $(div),
		leftRight: leftRight+"9",
		delay: 1600,
		left: -10,
		interval: 1500,
		endTop: 150,
		str: "<img src='resources/images/"+color+"heart.png' style='width:50px;height:50px;'>"
	},{
		obj: $(div),
		leftRight: leftRight+"10",
		delay: 1800,
		left: 10,
		interval: 1200,
		endTop: 120,
		str: "<img src='resources/images/"+color+"heart.png' style='width:70px;height:70px;'>"
	}];
	return heartObj;
}
function heartFloat(leftRight,div,color,fromNum,toNum){
	//数字跳动
	var personSupportNumDiv = new CountUp(leftRight+"PersonSupportNumDiv", fromNum, toNum, 0, 2.5, options);//el,startNum,endNum,decimalNum,time
	personSupportNumDiv.start();
	var delta = toNum - fromNum;
	var heartObj = getHeartObj(leftRight,div,color);
	if( delta <= 4 ){
		$.tipsBox(heartObj[0]);
	}
	else if( delta <= 10 ){
		for( var i = 0; i < 2; i++ )
			$.tipsBox(heartObj[i]);
	}
	else if( delta <= 15 ){
		for( var i = 0; i < 4; i++ )
			$.tipsBox(heartObj[i]);
	}
	else if( delta <= 20 ){
		for( var i = 0; i < 6; i++ )
			$.tipsBox(heartObj[i]);
	}
	else if( delta <= 30 ){
		for( var i = 0; i < 8; i++ )
			$.tipsBox(heartObj[i]);
	}
	else{
		for( var i = 0; i < 10; i++ )
			$.tipsBox(heartObj[i]);
	}
}
function rocketIn(words){
	$("#rocketMiddleDiv").html(words);
	$("#rocketDiv").show().animate({
		left: "225px"
	}, 1000, 'easeInOutBack', function(){
		setTimeout("rocketOut()", 1500);
	});
}
function rocketOut(){
	$("#rocketDiv").animate({
		left: "-1900px"
	}, 1000, 'easeInOutBack', function(){
		$("#rocketDiv").hide().css({"left":"1900px"});
	});
}
function heartAnimate(options){
	$("body").append("<span class='num"+options.leftRight+"'>" + options.str + "</span>");
	var box = $(".num"+options.leftRight);
	var left = options.obj.offset().left + options.left;
//	alert(left);
	var top = options.obj.offset().top;
	box.css({
		"position": "absolute",
		"left": left + "px",
		"top": top + "px",
		"z-index": 9999,
		"font-size": options.startSize,
		"line-height": options.endSize,
		"color": options.color
	});
	box.animate({
		"font-size": options.endSize,
		"opacity": "0",
//		"top": (top - parseInt(options.endSize) - 50) + "px"
		"top": (top - parseInt(options.endTop) ) + "px"
	}, options.interval, function () {
		box.remove();
		options.callback();
	});
}
function changeEnergyPercentage(leftPercentage,fromPercentage,a_bet_coins,b_bet_coins){
	var maxLength = 380;
	var leftPersonPercentageDiv = new CountUp("leftPersonPercentageDiv", fromPercentage*100, leftPercentage*100, 1, 2.5, {
		useEasing : true, 
		useGrouping : true, 
		separator : '', 
		decimal : '.', 
		prefix : a_bet_coins+'(', 
		suffix : '%)'
	});//el,startNum,endNum,decimalNum,time
	leftPersonPercentageDiv.start();
	var rightPersonPercentageDiv = new CountUp("rightPersonPercentageDiv", 100-100*fromPercentage, 100-100*leftPercentage, 1, 2.5, {
		useEasing : true, 
		useGrouping : true, 
		separator : '', 
		decimal : '.', 
		prefix : b_bet_coins+'(', 
		suffix : '%)'
	});//el,startNum,endNum,decimalNum,time
	rightPersonPercentageDiv.start();
	$("#leftPersonEnergyDiv").animate({
      width: (380*leftPercentage)+'px'
	}, 1000, 'easeInOutBack');  
	$("#rightEnergyEmptyDiv").animate({
		width: (88+380*leftPercentage)+'px'
	}, 1000, 'easeInOutBack');
	$("#rightPersonEnergyDiv").animate({
		width: (380-380*leftPercentage)+'px'
	}, 1000, 'easeInOutBack');
}
