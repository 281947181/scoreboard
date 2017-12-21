package com.service;

public interface ScoreControlService {
	String doCreateBasketballMatch(String setNum, String singleSetTime, String nameA, String nameB);
	String doUpdateBasketballTime(String matchId, String currentScoreA, String currentScoreB, String currentSetNum,
			String currentRestTimeMinute, String currentRestTimeSecond, String currentRestSideTime);
	String doUpdateBasketballScore(String matchId, String score, String oldScoreA, String oldScoreB, String side, String currentSetNum,
			String currentRestTimeMinute, String currentRestTimeSecond, String currentRestSideTime);
	String doRevokeBasketballScore(String matchId);
	String doRevokeBasketballTime(String matchId);
	String doResetBasketballTime(String matchId);
	String doCreatePingpangMatch(String setNum, String singleSetPoint, String nameA, String nameB);
	String doChooseServePingpang(String matchId, String side);
	String doUpdatePingpangScore(String matchId, String side);
	String doRevokePingpangScore(String matchId);
	String doNextSetPingpang(String matchId);
	String doStartTennisMatch(String matchId, String firstServe);
	String doCreateTennisMatch(String matchName, String city, String location, String judge, String matchType,
			String winType, String setType, String gameType, String nameA1, String nameA2, String nameB1, String nameB2, String matchDate, String lastType);
	String doGainServeScoreTennis(String matchId, String pathType, String isOne, String scoreSide);
	String doQueryTennisMatchInfoForInit(String matchId);
	String doServeFaultTennis(String matchId, String pathType, String isOne);
	String doRevokeTennisScore(String matchId);
	String doQueryTennisMatchList();
	String doQueryMatchInfoToContinueTennis(String matchId);
	String doDeleteMatchTennis(String matchId);
	String doRestartMatchTennis(String matchId);
}
