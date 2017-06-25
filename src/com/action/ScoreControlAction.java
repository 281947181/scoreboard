package com.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.ScoreControlService;
import com.utils.JsonUtils;

public class ScoreControlAction extends ActionSupport {
	private static final long serialVersionUID = -163020657345416225L;
	private ScoreControlService scoreControlService;
	public ScoreControlService getScoreControlService() {
		return scoreControlService;
	}
	public void setScoreControlService(ScoreControlService scoreControlService) {
		this.scoreControlService = scoreControlService;
	}
	public void createBasketballMatch(){
		ActionContext actionContext = ActionContext.getContext();
		String setNum = ((String[])actionContext.getParameters().get("setNum"))[0];
		String singleSetTime = ((String[])actionContext.getParameters().get("singleSetTime"))[0];
		String nameA = ((String[])actionContext.getParameters().get("nameA"))[0];
		String nameB = ((String[])actionContext.getParameters().get("nameB"))[0];
		new JsonUtils().writeJson(scoreControlService.doCreateBasketballMatch(setNum,singleSetTime,nameA,nameB));
	}
	public void updateBasketballTime(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		String currentScoreA = ((String[])actionContext.getParameters().get("currentScoreA"))[0];
		String currentScoreB = ((String[])actionContext.getParameters().get("currentScoreB"))[0];
		String currentSetNum = ((String[])actionContext.getParameters().get("currentSetNum"))[0];
		String currentRestTimeMinute = ((String[])actionContext.getParameters().get("currentRestTimeMinute"))[0];
		String currentRestTimeSecond = ((String[])actionContext.getParameters().get("currentRestTimeSecond"))[0];
		String currentRestSideTime = ((String[])actionContext.getParameters().get("currentRestSideTime"))[0];
		new JsonUtils().writeJson(scoreControlService.doUpdateBasketballTime(matchId,currentScoreA,currentScoreB,currentSetNum,currentRestTimeMinute,currentRestTimeSecond,currentRestSideTime));
	}
	public void updateBasketballScore(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		String score = ((String[])actionContext.getParameters().get("score"))[0];
		String oldScoreA = ((String[])actionContext.getParameters().get("oldScoreA"))[0];
		String oldScoreB = ((String[])actionContext.getParameters().get("oldScoreB"))[0];
		String side = ((String[])actionContext.getParameters().get("side"))[0];
		String currentSetNum = ((String[])actionContext.getParameters().get("currentSetNum"))[0];
		String currentRestTimeMinute = ((String[])actionContext.getParameters().get("currentRestTimeMinute"))[0];
		String currentRestTimeSecond = ((String[])actionContext.getParameters().get("currentRestTimeSecond"))[0];
		String currentRestSideTime = ((String[])actionContext.getParameters().get("currentRestSideTime"))[0];
		new JsonUtils().writeJson(scoreControlService.doUpdateBasketballScore(matchId,score,oldScoreA,oldScoreB,side,currentSetNum,currentRestTimeMinute,currentRestTimeSecond,currentRestSideTime));
	}
	public void revokeBasketballScore(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		new JsonUtils().writeJson(scoreControlService.doRevokeBasketballScore(matchId));
	}
	public void revokeBasketballTime(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		new JsonUtils().writeJson(scoreControlService.doRevokeBasketballTime(matchId));
	}
	public void resetBasketballTime(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		new JsonUtils().writeJson(scoreControlService.doResetBasketballTime(matchId));
	}
	public void createPingpangMatch(){
		ActionContext actionContext = ActionContext.getContext();
		String setNum = ((String[])actionContext.getParameters().get("setNum"))[0];
		String singleSetPoint = ((String[])actionContext.getParameters().get("singleSetPoint"))[0];
		String nameA = ((String[])actionContext.getParameters().get("nameA"))[0];
		String nameB = ((String[])actionContext.getParameters().get("nameB"))[0];
		new JsonUtils().writeJson(scoreControlService.doCreatePingpangMatch(setNum,singleSetPoint,nameA,nameB));
	}
	public void chooseServePingpang(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		String side = ((String[])actionContext.getParameters().get("side"))[0];
		new JsonUtils().writeJson(scoreControlService.doChooseServePingpang(matchId,side));
	}
	public void updatePingpangScore(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		String side = ((String[])actionContext.getParameters().get("side"))[0];
		new JsonUtils().writeJson(scoreControlService.doUpdatePingpangScore(matchId,side));
	}
	public void revokePingpangScore(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		new JsonUtils().writeJson(scoreControlService.doRevokePingpangScore(matchId));
	}
	public void nextSetPingpang(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		new JsonUtils().writeJson(scoreControlService.doNextSetPingpang(matchId));
	}
	public void createTennisMatch(){
		ActionContext actionContext = ActionContext.getContext();
		String matchName = ((String[])actionContext.getParameters().get("matchName"))[0];
		String matchDate = ((String[])actionContext.getParameters().get("matchDate"))[0];
		String city = ((String[])actionContext.getParameters().get("city"))[0];
		String location = ((String[])actionContext.getParameters().get("location"))[0];
		String judge = ((String[])actionContext.getParameters().get("judge"))[0];
		String matchType = ((String[])actionContext.getParameters().get("matchType"))[0];
		String winType = ((String[])actionContext.getParameters().get("winType"))[0];
		String setType = ((String[])actionContext.getParameters().get("setType"))[0];
		String gameType = ((String[])actionContext.getParameters().get("gameType"))[0];
		String nameA1 = ((String[])actionContext.getParameters().get("nameA1"))[0];
		String nameA2 = ((String[])actionContext.getParameters().get("nameA2"))[0];
		String nameB1 = ((String[])actionContext.getParameters().get("nameB1"))[0];
		String nameB2 = ((String[])actionContext.getParameters().get("nameB2"))[0];
		new JsonUtils().writeJson(scoreControlService.doCreateTennisMatch(
				matchName,city,location,judge,matchType,winType,
				setType,gameType,nameA1,nameA2,nameB1,nameB2,matchDate));
	}
	public void startTennisMatch(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		String firstServe = ((String[])actionContext.getParameters().get("firstServe"))[0];
		new JsonUtils().writeJson(scoreControlService.doStartTennisMatch(matchId,firstServe));
	}
	public void gainServeScoreTennis(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		String pathType = ((String[])actionContext.getParameters().get("pathType"))[0];
		String isOne = ((String[])actionContext.getParameters().get("isOne"))[0];
		String scoreSide = ((String[])actionContext.getParameters().get("scoreSide"))[0];
		new JsonUtils().writeJson(scoreControlService.doGainServeScoreTennis(matchId,pathType,isOne,scoreSide));
	}
	public void queryTennisMatchInfoForInit(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		new JsonUtils().writeJson(scoreControlService.doQueryTennisMatchInfoForInit(matchId));
	}
	public void serveFaultTennis(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		String pathType = ((String[])actionContext.getParameters().get("pathType"))[0];
		String isOne = ((String[])actionContext.getParameters().get("isOne"))[0];
		new JsonUtils().writeJson(scoreControlService.doServeFaultTennis(matchId,pathType,isOne));
	}
	public void gainPlayScoreTennis(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		String pathType = ((String[])actionContext.getParameters().get("pathType"))[0];
		String isOne = ((String[])actionContext.getParameters().get("isOne"))[0];
		String scoreSide = ((String[])actionContext.getParameters().get("scoreSide"))[0];
		new JsonUtils().writeJson(scoreControlService.doGainServeScoreTennis(matchId,pathType,isOne,scoreSide));
	}
	public void revokeTennisScore(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		new JsonUtils().writeJson(scoreControlService.doRevokeTennisScore(matchId));
	}
	public void queryTennisMatchList(){
		new JsonUtils().writeJson(scoreControlService.doQueryTennisMatchList());
	}
	public void queryMatchInfoToContinueTennis(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		new JsonUtils().writeJson(scoreControlService.doQueryMatchInfoToContinueTennis(matchId));
	}
	public void deleteMatchTennis(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		new JsonUtils().writeJson(scoreControlService.doDeleteMatchTennis(matchId));
	}
	public void restartMatchTennis(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		new JsonUtils().writeJson(scoreControlService.doRestartMatchTennis(matchId));
	}
	public void concedePointTennis(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		String pathType = ((String[])actionContext.getParameters().get("pathType"))[0];
		String isOne = ((String[])actionContext.getParameters().get("isOne"))[0];
		String scoreSide = ((String[])actionContext.getParameters().get("scoreSide"))[0];
		new JsonUtils().writeJson(scoreControlService.doGainServeScoreTennis(matchId,pathType,isOne,scoreSide));
	}
	
}
