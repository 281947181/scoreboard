package com.service.impl;

import java.util.Date;
import java.util.List;

import com.bean.BasketballScoreRecord;
import com.bean.MatchPathTennis;
import com.bean.PingpangScoreRecord;
import com.bean.TournamentInfoBasketball;
import com.bean.TournamentInfoPingpang;
import com.bean.TournamentInfoTennis;
import com.dao.BaseDao;
import com.service.ScoreControlService;

public class ScoreControlServiceImpl implements ScoreControlService {
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public String doCreateBasketballMatch(String setNum, String singleSetTime, String nameA, String nameB) {
		TournamentInfoBasketball tournamentInfoBasketball = new TournamentInfoBasketball();
		tournamentInfoBasketball.setCreateTime(new Date());
		tournamentInfoBasketball.setSetNum(Integer.parseInt(setNum));
		tournamentInfoBasketball.setSingleSetTime(Integer.parseInt(singleSetTime));
		tournamentInfoBasketball.setNameA(nameA);
		tournamentInfoBasketball.setNameB(nameB);
		tournamentInfoBasketball.setCurrentSetNum(1);
		tournamentInfoBasketball.setCurrentScoreA(0);
		tournamentInfoBasketball.setCurrentScoreB(0);
		tournamentInfoBasketball.setCurrentRestTimeMinute(Integer.parseInt(singleSetTime));
		tournamentInfoBasketball.setCurrentRestTimeSecond(0);
		tournamentInfoBasketball.setCurrentRestSideTime(24);
		baseDao.save(tournamentInfoBasketball);
		
		return "{\"success\":true,"
				+ "\"matchId\":"+tournamentInfoBasketball.getVid()+","
				+ "\"nameA\":\""+nameA+"\","
				+ "\"nameB\":\""+nameB+"\","
				+ "\"setNum\":"+1+","
				+ "\"totalMinute\":"+tournamentInfoBasketball.getCurrentRestTimeMinute()+","
				+ "\"sideTime\":"+24+"}";
	}
	@Override
	public String doUpdateBasketballTime(String matchId, String currentScoreA, String currentScoreB,
			String currentSetNum, String currentRestTimeMinute, String currentRestTimeSecond, String currentRestSideTime) {
		String hqlForTournamentInfoBasketball = "from TournamentInfoBasketball where vid = ?";
		List<TournamentInfoBasketball> tournamentInfoBasketballs = baseDao.queryBySql(hqlForTournamentInfoBasketball, Integer.parseInt(matchId));
		TournamentInfoBasketball tournamentInfoBasketball = tournamentInfoBasketballs.get(0);
		tournamentInfoBasketball.setCurrentScoreA(Integer.parseInt(currentScoreA));
		tournamentInfoBasketball.setCurrentScoreB(Integer.parseInt(currentScoreB));
		tournamentInfoBasketball.setCurrentSetNum(Integer.parseInt(currentSetNum));
		tournamentInfoBasketball.setCurrentRestTimeMinute(Integer.parseInt(currentRestTimeMinute));
		tournamentInfoBasketball.setCurrentRestTimeSecond(Integer.parseInt(currentRestTimeSecond));
		tournamentInfoBasketball.setCurrentRestSideTime(Integer.parseInt(currentRestSideTime));
		baseDao.update(tournamentInfoBasketball);
		if ( tournamentInfoBasketball.getCurrentRestTimeMinute() == 0 && tournamentInfoBasketball.getCurrentRestTimeSecond() == 0 ) {
			//计时到0
			return "{\"success\":true,\"setEnd\":true}";
		}
		else
			return "{\"success\":true,\"setEnd\":false}";
	}
	@Override
	public String doUpdateBasketballScore(String matchId, String score, String oldScoreA, String oldScoreB, String side, String currentSetNum,
			String currentRestTimeMinute, String currentRestTimeSecond, String currentRestSideTime) {
		BasketballScoreRecord basketballScoreRecord = new BasketballScoreRecord();
		basketballScoreRecord.setMatchId(Integer.parseInt(matchId));
		basketballScoreRecord.setScoreSide(side);
		basketballScoreRecord.setScoreIncreased(Integer.parseInt(score));
		basketballScoreRecord.setOldScoreA(Integer.parseInt(oldScoreA));
		basketballScoreRecord.setOldScoreB(Integer.parseInt(oldScoreB));
		int newScore = 0;
		switch( side ){
			case "A":{
				basketballScoreRecord.setNewScoreA(basketballScoreRecord.getOldScoreA()+basketballScoreRecord.getScoreIncreased());
				basketballScoreRecord.setNewScoreB(basketballScoreRecord.getOldScoreB());
				newScore = basketballScoreRecord.getNewScoreA();
				break;
			}
			case "B":{
				basketballScoreRecord.setNewScoreB(basketballScoreRecord.getOldScoreB()+basketballScoreRecord.getScoreIncreased());
				basketballScoreRecord.setNewScoreA(basketballScoreRecord.getOldScoreA());
				newScore = basketballScoreRecord.getNewScoreB();
				break;
			}
			default:
				break;
		}
		basketballScoreRecord.setSetNum(Integer.parseInt(currentSetNum));
		basketballScoreRecord.setMinute(Integer.parseInt(currentRestTimeMinute));
		basketballScoreRecord.setSecond(Integer.parseInt(currentRestTimeSecond));
		basketballScoreRecord.setSideTime(Integer.parseInt(currentRestSideTime));
		basketballScoreRecord.setCreateTime(new Date());
		String hqlForTournamentInfoBasketball = "from TournamentInfoBasketball where vid = ?";
		List<TournamentInfoBasketball> tournamentInfoBasketballs = baseDao.queryBySql(hqlForTournamentInfoBasketball, Integer.parseInt(matchId));
		tournamentInfoBasketballs.get(0).setCurrentScoreA(basketballScoreRecord.getNewScoreA());
		tournamentInfoBasketballs.get(0).setCurrentScoreB(basketballScoreRecord.getNewScoreB());
		tournamentInfoBasketballs.get(0).setCurrentSetNum(Integer.parseInt(currentSetNum));
		tournamentInfoBasketballs.get(0).setCurrentRestTimeMinute(Integer.parseInt(currentRestTimeMinute));
		tournamentInfoBasketballs.get(0).setCurrentRestTimeSecond(Integer.parseInt(currentRestTimeSecond));
		tournamentInfoBasketballs.get(0).setCurrentRestSideTime(Integer.parseInt(currentRestSideTime));
		baseDao.save(basketballScoreRecord);
		baseDao.update(tournamentInfoBasketballs.get(0));
		return "{\"success\":true,\"newScore\":"+newScore+"}";
	}
	@Override
	public String doRevokeBasketballScore(String matchId) {
		String hqlForBasketballScoreRecord = "from BasketballScoreRecord where matchId = ? order by vid desc";
		List<BasketballScoreRecord> basketballScoreRecords = baseDao.queryBySqlLimit(hqlForBasketballScoreRecord, Integer.parseInt(matchId), 0, 1);
		if ( basketballScoreRecords.isEmpty() ) {
			return "{\"success\":true,\"scoreA\":0,\"scoreB\":0}";
		}
		else{
			BasketballScoreRecord basketballScoreRecord = basketballScoreRecords.get(0);
			String hqlForTournamentInfoBasketball = "from TournamentInfoBasketball where vid = ?";
			List<TournamentInfoBasketball> tournamentInfoBasketballs = baseDao.queryBySql(hqlForTournamentInfoBasketball, Integer.parseInt(matchId));
			tournamentInfoBasketballs.get(0).setCurrentScoreA(basketballScoreRecord.getOldScoreA());
			tournamentInfoBasketballs.get(0).setCurrentScoreB(basketballScoreRecord.getOldScoreB());
			baseDao.update(tournamentInfoBasketballs.get(0));
			baseDao.delete(basketballScoreRecord);
			return "{\"success\":true,\"scoreA\":"+basketballScoreRecord.getOldScoreA()+",\"scoreB\":"+basketballScoreRecord.getOldScoreB()+"}";
		}
	}
	@Override
	public String doRevokeBasketballTime(String matchId) {
		String hqlForBasketballScoreRecord = "from BasketballScoreRecord where matchId = ? order by vid desc";
		List<BasketballScoreRecord> basketballScoreRecords = baseDao.queryBySqlLimit(hqlForBasketballScoreRecord, Integer.parseInt(matchId), 0, 1);
		if ( basketballScoreRecords.isEmpty() ) {
			return "{\"success\":true,\"scoreA\":0,\"scoreB\":0}";
		}
		else{
			BasketballScoreRecord basketballScoreRecord = basketballScoreRecords.get(0);
			String hqlForTournamentInfoBasketball = "from TournamentInfoBasketball where vid = ?";
			List<TournamentInfoBasketball> tournamentInfoBasketballs = baseDao.queryBySql(hqlForTournamentInfoBasketball, Integer.parseInt(matchId));
			tournamentInfoBasketballs.get(0).setCurrentScoreA(basketballScoreRecord.getOldScoreA());
			tournamentInfoBasketballs.get(0).setCurrentScoreB(basketballScoreRecord.getOldScoreB());
			tournamentInfoBasketballs.get(0).setCurrentSetNum(basketballScoreRecord.getSetNum());
			tournamentInfoBasketballs.get(0).setCurrentRestTimeMinute(basketballScoreRecord.getMinute());
			tournamentInfoBasketballs.get(0).setCurrentRestTimeSecond(basketballScoreRecord.getSecond());
			tournamentInfoBasketballs.get(0).setCurrentRestSideTime(basketballScoreRecord.getSideTime());
			baseDao.update(tournamentInfoBasketballs.get(0));
			baseDao.delete(basketballScoreRecord);
			return "{\"success\":true,\"scoreA\":"+basketballScoreRecord.getOldScoreA()
			+",\"scoreB\":"+basketballScoreRecord.getOldScoreB()
			+",\"nextSet\":"+basketballScoreRecord.getSetNum()
			+",\"totalMinute\":"+basketballScoreRecord.getMinute()
			+",\"totalSecond\":"+basketballScoreRecord.getSecond()
			+",\"sideTime\":"+basketballScoreRecord.getSideTime()
			+"}";
		}
	}
	@Override
	public String doResetBasketballTime(String matchId) {
		String hqlForTournamentInfoBasketball = "from TournamentInfoBasketball where vid = ?";
		List<TournamentInfoBasketball> tournamentInfoBasketballs = baseDao.queryBySql(hqlForTournamentInfoBasketball, Integer.parseInt(matchId));
		TournamentInfoBasketball tournamentInfoBasketball = tournamentInfoBasketballs.get(0);
		if ( tournamentInfoBasketball.getCurrentRestTimeMinute() == 0 && tournamentInfoBasketball.getCurrentRestTimeSecond() == 0 ) {
			//计时到0
			if ( tournamentInfoBasketball.getCurrentSetNum() < tournamentInfoBasketball.getSetNum() ) {
				tournamentInfoBasketball.setCurrentSetNum(tournamentInfoBasketball.getCurrentSetNum()+1);
				tournamentInfoBasketball.setCurrentRestTimeMinute(tournamentInfoBasketball.getSingleSetTime());
				tournamentInfoBasketball.setCurrentRestSideTime(24);
				baseDao.update(tournamentInfoBasketball);
				return "{\"success\":true,"
					+ "\"setNum\":"+tournamentInfoBasketball.getCurrentSetNum()+","
					+ "\"totalMinute\":"+tournamentInfoBasketball.getCurrentRestTimeMinute()+","
					+ "\"sideTime\":"+24+"}";
			}
			else
				return "{\"success\":true,\"msg\":\"比赛结束\"}";
		}
		else
			return "{\"success\":true,\"msg\":\"数据异常\"}";
	}
	@Override
	public String doCreatePingpangMatch(String setNum, String singleSetPoint, String nameA, String nameB) {
		TournamentInfoPingpang tournamentInfoPingpang = new TournamentInfoPingpang();
		tournamentInfoPingpang.setCreateTime(new Date());
		tournamentInfoPingpang.setSetNum(Integer.parseInt(setNum));
		tournamentInfoPingpang.setSingleSetPoint(Integer.parseInt(singleSetPoint));
		tournamentInfoPingpang.setNameA(nameA);
		tournamentInfoPingpang.setNameB(nameB);
		tournamentInfoPingpang.setCurrentSetNum(0);
		tournamentInfoPingpang.setCurrentScoreA(0);
		tournamentInfoPingpang.setCurrentScoreB(0);
		tournamentInfoPingpang.setCurrentSetA(0);
		tournamentInfoPingpang.setCurrentSetB(0);
		tournamentInfoPingpang.setTotalServeNum(0);
		baseDao.save(tournamentInfoPingpang);
		return "{\"success\":true,"
			+ "\"matchId\":"+tournamentInfoPingpang.getVid()+","
			+ "\"nameA\":\""+nameA+"\","
			+ "\"nameB\":\""+nameB+"\","
			+ "\"singleSetPoint\":"+singleSetPoint+","
			+ "\"setNum\":"+setNum+"}";
	}
	@Override
	public String doChooseServePingpang(String matchId, String side) {
		String hqlForTournamentInfoPingpang = "from TournamentInfoPingpang where vid = ?";
		List<TournamentInfoPingpang> tournamentInfoPingpangs = baseDao.queryBySql(hqlForTournamentInfoPingpang, Integer.parseInt(matchId));
		if ( tournamentInfoPingpangs.isEmpty() ) {
			return "{\"success\":false}";
		}
		else{
			tournamentInfoPingpangs.get(0).setFirstServe(side);
			tournamentInfoPingpangs.get(0).setCurrentServe(side);
			baseDao.update(tournamentInfoPingpangs.get(0));
			return "{\"success\":true,\"firstServe\":\""+side+"\",\"notServe\":\""+("A".equals(side)?"B":"A")+"\"}";
		}
	}
	@Override
	public String doUpdatePingpangScore(String matchId, String side) {
		String hqlForTournamentInfoPingpang = "from TournamentInfoPingpang where vid = ?";
		List<TournamentInfoPingpang> tournamentInfoPingpangs = baseDao.queryBySql(hqlForTournamentInfoPingpang, Integer.parseInt(matchId));
		if ( tournamentInfoPingpangs.isEmpty() ) {
			return "{\"success\":false}";
		}
		else {
			int maxScore = tournamentInfoPingpangs.get(0).getSingleSetPoint();
			int currentScoreA = tournamentInfoPingpangs.get(0).getCurrentScoreA();
			int currentScoreB = tournamentInfoPingpangs.get(0).getCurrentScoreB();
			int totalServeNum = tournamentInfoPingpangs.get(0).getTotalServeNum();
			boolean setEnd = false;
			String nextServe = tournamentInfoPingpangs.get(0).getCurrentServe();
			PingpangScoreRecord pingpangScoreRecord = new PingpangScoreRecord();
			pingpangScoreRecord.setMatchId(Integer.parseInt(matchId));
			pingpangScoreRecord.setCreateTime(new Date());
			pingpangScoreRecord.setOldScoreA(currentScoreA);
			pingpangScoreRecord.setOldScoreB(currentScoreB);
			pingpangScoreRecord.setScoreSide(side);
			pingpangScoreRecord.setSetA(tournamentInfoPingpangs.get(0).getCurrentSetA());
			pingpangScoreRecord.setSetB(tournamentInfoPingpangs.get(0).getCurrentSetB());
			pingpangScoreRecord.setSetNum(tournamentInfoPingpangs.get(0).getCurrentSetNum());
			pingpangScoreRecord.setOldServeSide(nextServe);
			pingpangScoreRecord.setOldTotalServeNum(totalServeNum);
			if ( "A".equals(side) ) {
				//A得分
				if ( currentScoreA >= currentScoreB && currentScoreB >= maxScore - 1 ) {
					//都大于最后一份且A领先或平分,每球交换发球,比赛结束
					currentScoreA++;
					totalServeNum++;
					nextServe = "A".equals(nextServe)?"B":"A";
					setEnd = (currentScoreA == currentScoreB + 2);
				}
				else if ( currentScoreB >= currentScoreA && currentScoreA >= maxScore - 1 ) {
					//都大于最后一份且B领先或平分,每球交换发球,比赛未结束
					currentScoreA++;
					totalServeNum++;
					nextServe = "A".equals(nextServe)?"B":"A";
					setEnd = false;
				}
				else {
					//B领先或平分, 不大于最后一分, 每2分换发球
					currentScoreA++;
					totalServeNum++;
					if ( totalServeNum%2 == 0 )//每2球换发
						nextServe = "A".equals(nextServe)?"B":"A";
					setEnd = (currentScoreA == maxScore);
				} 
				tournamentInfoPingpangs.get(0).setCurrentServe(nextServe);
				tournamentInfoPingpangs.get(0).setCurrentScoreA(currentScoreA);
				tournamentInfoPingpangs.get(0).setTotalServeNum(totalServeNum);
				baseDao.update(tournamentInfoPingpangs.get(0));
			}
			else if ( "B".equals(side) ) {
				//B得分
				if ( currentScoreB >= currentScoreA && currentScoreA >= maxScore - 1 ) {
					//都大于最后一份且B领先或平分,每球交换发球,比赛未结束
					currentScoreB++;
					totalServeNum++;
					nextServe = "A".equals(nextServe)?"B":"A";
					setEnd = (currentScoreB == currentScoreA + 2);
				}
				else if ( currentScoreA >= currentScoreB && currentScoreB >= maxScore - 1 ) {
					//都大于最后一份且A领先或平分,每球交换发球,比赛结束
					currentScoreB++;
					totalServeNum++;
					nextServe = "A".equals(nextServe)?"B":"A";
					setEnd = false;
				}
				else {
					//B领先或平分, 不大于最后一分, 每2分换发球
					currentScoreB++;
					totalServeNum++;
					if ( totalServeNum%2 == 0 )//每2球换发
						nextServe = "A".equals(nextServe)?"B":"A";
					setEnd = (currentScoreB == maxScore);
				} 
				tournamentInfoPingpangs.get(0).setCurrentServe(nextServe);
				tournamentInfoPingpangs.get(0).setCurrentScoreB(currentScoreB);
				tournamentInfoPingpangs.get(0).setTotalServeNum(totalServeNum);
				baseDao.update(tournamentInfoPingpangs.get(0));
			}
			pingpangScoreRecord.setNewScoreA(currentScoreA);
			pingpangScoreRecord.setNewScoreB(currentScoreB);
			baseDao.save(pingpangScoreRecord);
			return "{\"success\":true,\"setEnd\":"+setEnd+",\"scoreA\":"+currentScoreA+",\"scoreB\":"+currentScoreB+",\"nextServe\":\""+nextServe+"\",\"notServe\":\""+("A".equals(nextServe)?"B":"A")+"\"}";
		}
	}
	@Override
	public String doRevokePingpangScore(String matchId) {
		String hqlForTournamentInfoPingpang = "from TournamentInfoPingpang where vid = ?";
		List<TournamentInfoPingpang> tournamentInfoPingpangs = baseDao.queryBySql(hqlForTournamentInfoPingpang, Integer.parseInt(matchId));
		if ( tournamentInfoPingpangs.isEmpty() ) {
			return "{\"success\":false}";
		}
		else {
			String hqlForPingpangScoreRecord = "from PingpangScoreRecord where matchId = ? order by vid desc";
			List<PingpangScoreRecord> pingpangScoreRecords = baseDao.queryBySqlLimit(hqlForPingpangScoreRecord, Integer.parseInt(matchId), 0, 1);
			if ( pingpangScoreRecords.isEmpty() ) {
				//无得分纪录, 退回到比赛开始
				return "{\"success\":false}";
			}
			else{
				PingpangScoreRecord pingpangScoreRecord = pingpangScoreRecords.get(0);
				int currentScoreA = pingpangScoreRecord.getOldScoreA();
				int currentScoreB = pingpangScoreRecord.getOldScoreB();
				int totalServeNum = pingpangScoreRecord.getOldTotalServeNum();
				String currentServe = pingpangScoreRecord.getOldServeSide();
				tournamentInfoPingpangs.get(0).setCurrentScoreA(currentScoreA);
				tournamentInfoPingpangs.get(0).setCurrentScoreB(currentScoreB);
				tournamentInfoPingpangs.get(0).setCurrentServe(currentServe);
				tournamentInfoPingpangs.get(0).setTotalServeNum(totalServeNum);
				baseDao.update(tournamentInfoPingpangs.get(0));
				baseDao.delete(pingpangScoreRecord);
				return "{\"success\":true,\"scoreA\":"+currentScoreA+",\"scoreB\":"+currentScoreB+",\"nextServe\":\""+currentServe+"\",\"notServe\":\""+("A".equals(currentServe)?"B":"A")+"\"}";
			}
		}
	}
	@Override
	public String doNextSetPingpang(String matchId) {
		String hqlForTournamentInfoPingpang = "from TournamentInfoPingpang where vid = ?";
		List<TournamentInfoPingpang> tournamentInfoPingpangs = baseDao.queryBySql(hqlForTournamentInfoPingpang, Integer.parseInt(matchId));
		if ( tournamentInfoPingpangs.isEmpty() ) {
			return "{\"success\":false}";
		}
		else {
			TournamentInfoPingpang tournamentInfoPingpang = tournamentInfoPingpangs.get(0);
			int currentScoreA = tournamentInfoPingpang.getCurrentScoreA();
			int currentScoreB = tournamentInfoPingpang.getCurrentScoreB();
			int currentSetA = tournamentInfoPingpang.getCurrentSetA();
			int currentSetB = tournamentInfoPingpang.getCurrentSetB();
			int currentSetNum = tournamentInfoPingpang.getCurrentSetNum();
			currentSetNum++;
			boolean matchEnd = false;
			if ( currentScoreA > currentScoreB ){
				currentSetA++;
				matchEnd = (currentSetA == tournamentInfoPingpang.getSetNum()/2+1);
			}
			else{
				currentSetB++;
				matchEnd = (currentSetB == tournamentInfoPingpang.getSetNum()/2+1);
			}
			String firstServe = tournamentInfoPingpang.getFirstServe();
			String notFirstServe = "A".equals(firstServe)?"B":"A";
			String nextServe = ( currentSetNum%2 == 0?firstServe:notFirstServe);
			String notServe = "A".equals(nextServe)?"B":"A";
			tournamentInfoPingpang.setCurrentSetNum(currentSetNum);
			tournamentInfoPingpang.setCurrentScoreA(0);
			tournamentInfoPingpang.setCurrentScoreB(0);
			tournamentInfoPingpang.setCurrentSetA(currentSetA);
			tournamentInfoPingpang.setCurrentSetB(currentSetB);
			tournamentInfoPingpang.setTotalServeNum(0);
			baseDao.update(tournamentInfoPingpang);
			return "{\"success\":true,"
				+ "\"matchEnd\":"+matchEnd+","
				+ "\"setA\":"+currentSetA+","
				+ "\"setB\":"+currentSetB+","
				+ "\"nextServe\":\""+nextServe+"\","
				+ "\"notServe\":\""+notServe+"\","
				+ "\"setNum\":"+currentSetNum+"}";
		}
	}
	@Override
	public String doCreateTennisMatch(String matchName, String city, String location, String judge, String matchType,
			String winType, String setType, String gameType, String nameA1, String nameA2, String nameB1,
			String nameB2, String matchDate) {
		TournamentInfoTennis tournamentInfoTennis = new TournamentInfoTennis();
		tournamentInfoTennis.setTournamentName(matchName);
		tournamentInfoTennis.setTournamentDate(matchDate);
		tournamentInfoTennis.setCreateTime(new Date());
		tournamentInfoTennis.setCity(city);
		tournamentInfoTennis.setLocation(location);
		tournamentInfoTennis.setMatchType(matchType);
		tournamentInfoTennis.setCompetitionSystemGame(winType);
		tournamentInfoTennis.setCompetitionSystemCouncil(setType);
		tournamentInfoTennis.setCompetitionSystemSmall(gameType);
		tournamentInfoTennis.setHome_1_id(1);
		tournamentInfoTennis.setAway_1_id(2);
		tournamentInfoTennis.setHome_1_name(nameA1);
		tournamentInfoTennis.setAway_1_name(nameB1);
		if ( matchType.indexOf("双") >= 0 ) {
			tournamentInfoTennis.setHome_2_id(3);
			tournamentInfoTennis.setAway_2_id(4);
			tournamentInfoTennis.setHome_2_name(nameA2);
			tournamentInfoTennis.setAway_2_name(nameB2);
		}
		else{
			tournamentInfoTennis.setHome_2_name("");
			tournamentInfoTennis.setAway_2_name("");
		}
		tournamentInfoTennis.setStatus("未开始");
		tournamentInfoTennis.setPointType("");
		tournamentInfoTennis.setNewestSetA(0);
		tournamentInfoTennis.setNewestSetB(0);
		tournamentInfoTennis.setNewestCouncilA(0);
		tournamentInfoTennis.setNewestCouncilB(0);
		tournamentInfoTennis.setNewestSmallA("0");
		tournamentInfoTennis.setNewestSmallB("0");
//		tournamentInfoTennis.setNextServe(nextServe);
//		tournamentInfoTennis.setStartTime(startTime);
//		tournamentInfoTennis.setEndTime(endTime);
//		tournamentInfoTennis.setWinner(winner);
//		tournamentInfoTennis.setWithdraw(withdraw);
		tournamentInfoTennis.setJudgeName(judge);
//		...其他辅助信息, 未在表单体现
		tournamentInfoTennis.setCreateUserId(1);
		baseDao.save(tournamentInfoTennis);
		return "{\"success\":true,\"matchId\":"+tournamentInfoTennis.getVid()+"}";
	}
	@Override
	public String doStartTennisMatch(String matchId, String firstServe) {
		String hqlForTournamentInfoTennis = "from TournamentInfoTennis where vid = ?";
		List<TournamentInfoTennis> tournamentInfoTennis = baseDao.queryBySql(hqlForTournamentInfoTennis,Integer.parseInt(matchId));
		if ( tournamentInfoTennis.isEmpty() ) 
			return "{\"success\":false,\"msg\":\"赛事信息有误, 请重新创建比赛!\"}";
		else {
			if ( "未开始".equals(tournamentInfoTennis.get(0).getStatus()) ) {
				tournamentInfoTennis.get(0).setNextServe(firstServe);
				tournamentInfoTennis.get(0).setFirstServe(firstServe);
				tournamentInfoTennis.get(0).setStatus("进行中");
				tournamentInfoTennis.get(0).setStartTime(new Date());
				tournamentInfoTennis.get(0).setIsOne(1);
				baseDao.update(tournamentInfoTennis.get(0));
				return "{\"success\":true}";
			}
			else
				return "{\"success\":false,\"msg\":\"赛事信息有误, 请重新创建比赛!\"}";
		}
	}
	private int isOnlySevenOr11Set(TournamentInfoTennis tournamentInfoTennis){
		String competitionSystemCouncil = tournamentInfoTennis.getCompetitionSystemCouncil();
		if( "1局抢7".equals(competitionSystemCouncil) )
			return 7;
		else if ( "1局抢11".equals(competitionSystemCouncil) ) 
			return 11;
		else 
			return 0;
	}
	private boolean isSevenSet(TournamentInfoTennis tournamentInfoTennis){
		int newestCouncilA = tournamentInfoTennis.getNewestCouncilA();
		int newestCouncilB = tournamentInfoTennis.getNewestCouncilB();
		String competitionSystemCouncil = tournamentInfoTennis.getCompetitionSystemCouncil();
		if ( (newestCouncilA+"").equals(competitionSystemCouncil.substring(0,1)) && (newestCouncilB+"").equals(competitionSystemCouncil.substring(0,1)) )
			//A和B局分都为6,5,4,3,则当前局为抢七局, 比分一次加1分
			return true;
		else
			return false;
	}
	private TournamentInfoTennis gainScore(TournamentInfoTennis tournamentInfoTennis, String scoreSide){
		String smallA = tournamentInfoTennis.getNewestSmallA();
		String smallB = tournamentInfoTennis.getNewestSmallB();
		String nextServe = tournamentInfoTennis.getNextServe();
		int councilA = tournamentInfoTennis.getNewestCouncilA();
		int councilB = tournamentInfoTennis.getNewestCouncilB();
		int setA = tournamentInfoTennis.getNewestSetA();
		int setB = tournamentInfoTennis.getNewestSetB();
		int scoreA = "Ad".equals(smallA)?60:Integer.parseInt(smallA);
		int scoreB = "Ad".equals(smallB)?60:Integer.parseInt(smallB);
		int winSet = Integer.parseInt(tournamentInfoTennis.getCompetitionSystemGame().substring(0,1))/2+1;
		String competitionSystemSmall = tournamentInfoTennis.getCompetitionSystemSmall();
		int winCouncil = Integer.parseInt(tournamentInfoTennis.getCompetitionSystemCouncil().substring(0,1));
		if( isOnlySevenOr11Set(tournamentInfoTennis) > 0 ){
			//1局抢7或1局抢11
			int sevenOr11 = isOnlySevenOr11Set(tournamentInfoTennis);
			//7,11
			if ( "A".equals(scoreSide) ) {
				//A得分
				scoreA++;
				if ( competitionSystemSmall.indexOf("有") >= 0 && scoreA >= sevenOr11 && ( scoreA - scoreB >= 2 )  ) {
					//本发球局结束, A抢7胜, set+1, council清零, small清零, 换发球
					tournamentInfoTennis.setNewestCouncilA(0);
					tournamentInfoTennis.setNewestCouncilB(0);
					tournamentInfoTennis.setNewestSmallA("0");
					tournamentInfoTennis.setNewestSmallB("0");
					setA++;
					tournamentInfoTennis.setNewestSetA(setA);
					if ( setA == winSet ) {
						//A赢过半数, 比赛结束, A获胜
						tournamentInfoTennis.setStatus("已结束");
						tournamentInfoTennis.setNextServe(null);
						tournamentInfoTennis.setEndTime(new Date());
						tournamentInfoTennis.setWinner("A");
						tournamentInfoTennis.setWithdraw("B");
						tournamentInfoTennis.setNextServe(null);
					}
				}
				else if ( competitionSystemSmall.indexOf("无") >= 0 && scoreA == sevenOr11 ) {
					//本发球局结束, A抢7胜, set+1, council清零, small清零, 换发球
					tournamentInfoTennis.setNewestCouncilA(0);
					tournamentInfoTennis.setNewestCouncilB(0);
					tournamentInfoTennis.setNewestSmallA("0");
					tournamentInfoTennis.setNewestSmallB("0");
					setA++;
					tournamentInfoTennis.setNewestSetA(setA);
					if ( setA == winSet ) {
						//A赢过半数, 比赛结束, A获胜
						tournamentInfoTennis.setStatus("已结束");
						tournamentInfoTennis.setNextServe(null);
						tournamentInfoTennis.setEndTime(new Date());
						tournamentInfoTennis.setWinner("A");
						tournamentInfoTennis.setWithdraw("B");
						tournamentInfoTennis.setNextServe(null);
					}
				}
				else{
					tournamentInfoTennis.setNewestSmallA(scoreA+"");
					if ( scoreA+scoreB == 1 ) 
						tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
					else if ( (scoreA+scoreB) >= 2 ) {
						if ( (scoreA+scoreB-1)%2 == 0 ) 
							tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
					}
					
				}
			}
			else{
				//B得分
				scoreB++;
				if ( competitionSystemSmall.indexOf("有") >= 0 && scoreB >= sevenOr11 && ( scoreB - scoreA >= 2 ) ) {
					//本发球局结束, B抢7胜, set+1, council清零, small清零
					tournamentInfoTennis.setNewestCouncilA(0);
					tournamentInfoTennis.setNewestCouncilB(0);
					tournamentInfoTennis.setNewestSmallA("0");
					tournamentInfoTennis.setNewestSmallB("0");
					setB++;
					tournamentInfoTennis.setNewestSetB(setB);
					if ( setB == winSet ) {
						//B赢过半数, 比赛结束, A获胜
						tournamentInfoTennis.setStatus("已结束");
						tournamentInfoTennis.setNextServe(null);
						tournamentInfoTennis.setEndTime(new Date());
						tournamentInfoTennis.setWinner("B");
						tournamentInfoTennis.setWithdraw("A");
						tournamentInfoTennis.setNextServe(null);
					}
				}
				else if ( competitionSystemSmall.indexOf("无") >= 0 && scoreB == sevenOr11 ) {
					//本发球局结束, B抢7胜, set+1, council清零, small清零, 换发球
					tournamentInfoTennis.setNewestCouncilA(0);
					tournamentInfoTennis.setNewestCouncilB(0);
					tournamentInfoTennis.setNewestSmallA("0");
					tournamentInfoTennis.setNewestSmallB("0");
					setB++;
					tournamentInfoTennis.setNewestSetB(setB);
					if ( setB == winSet ) {
						//B赢过半数, 比赛结束, A获胜
						tournamentInfoTennis.setStatus("已结束");
						tournamentInfoTennis.setNextServe(null);
						tournamentInfoTennis.setEndTime(new Date());
						tournamentInfoTennis.setWinner("B");
						tournamentInfoTennis.setWithdraw("A");
						tournamentInfoTennis.setNextServe(null);
					}
				}
				else{
					tournamentInfoTennis.setNewestSmallB(scoreB+"");
					if ( scoreA+scoreB == 1 ) 
						tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
					else if ( (scoreA+scoreB) >= 2 ) {
						if ( (scoreA+scoreB-1)%2 == 0 ) 
							tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
					}
				}
			}
		}
		else if ( isSevenSet(tournamentInfoTennis) ) {
			//当前局为抢七局, 比分一次加1分, 且councilA和councilB均为最高局分6,5,4,3,视competitionSystemSmall第一位而定, 得分后set+1, council清零, small清零
			if ( "A".equals(scoreSide) ) {
				//A得分
				scoreA++;
				if ( scoreA >= 7 && ( scoreA - scoreB >= 2 ) ) {
					//本发球局结束, A抢7胜, set+1, council清零, small清零, 换发球
					tournamentInfoTennis.setNewestCouncilA(0);
					tournamentInfoTennis.setNewestCouncilB(0);
					tournamentInfoTennis.setNewestSmallA("0");
					tournamentInfoTennis.setNewestSmallB("0");
					setA++;
					tournamentInfoTennis.setNewestSetA(setA);
					if ( setA == winSet ) {
						//A赢过半数, 比赛结束, A获胜
						tournamentInfoTennis.setStatus("已结束");
						tournamentInfoTennis.setNextServe(null);
						tournamentInfoTennis.setEndTime(new Date());
						tournamentInfoTennis.setWinner("A");
						tournamentInfoTennis.setWithdraw("B");
						tournamentInfoTennis.setNextServe(null);
					}
				}
				else{
					tournamentInfoTennis.setNewestSmallA(scoreA+"");
					if ( scoreA+scoreB == 1 ) 
						tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
					else if ( (scoreA+scoreB) >= 2 ) {
						if ( (scoreA+scoreB-1)%2 == 0 ) 
							tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
					}
					
				}
			}
			else{
				//B得分
				scoreB++;
				if ( scoreB >= 7 && ( scoreB - scoreA >= 2 ) ) {
					//本发球局结束, B抢7胜, set+1, council清零, small清零
					tournamentInfoTennis.setNewestCouncilA(0);
					tournamentInfoTennis.setNewestCouncilB(0);
					tournamentInfoTennis.setNewestSmallA("0");
					tournamentInfoTennis.setNewestSmallB("0");
					setB++;
					tournamentInfoTennis.setNewestSetB(setB);
					if ( setB == winSet ) {
						//A赢过半数, 比赛结束, A获胜
						tournamentInfoTennis.setStatus("已结束");
						tournamentInfoTennis.setNextServe(null);
						tournamentInfoTennis.setEndTime(new Date());
						tournamentInfoTennis.setWinner("B");
						tournamentInfoTennis.setWithdraw("A");
						tournamentInfoTennis.setNextServe(null);
					}
				}
				else{
					tournamentInfoTennis.setNewestSmallB(scoreB+"");
					if ( scoreA+scoreB == 1 ) 
						tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
					else if ( (scoreA+scoreB) >= 2 ) {
						if ( (scoreA+scoreB-1)%2 == 0 ) 
							tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
					}
				}
			}
			
		}
		else{
			//非抢7盘
			if ( "A".equals(scoreSide) ) {
				//A得分
				switch (scoreA) {
					case 0:
						tournamentInfoTennis.setNewestSmallA("15");
						break;
					case 15:
						tournamentInfoTennis.setNewestSmallA("30");
						break;
					case 30:
						tournamentInfoTennis.setNewestSmallA("40");
						break;
					case 40: {
						if ( "平分无占先".equals(competitionSystemSmall) ) {
							//本局结束, small清零, councilA+1
							tournamentInfoTennis.setNewestSmallA("0");
							tournamentInfoTennis.setNewestSmallB("0");
							tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
							councilA++;
							if ( (councilA == winCouncil && councilB <= winCouncil - 2) || (councilA == winCouncil+1) ) {
								tournamentInfoTennis.setNewestCouncilA(0);
								tournamentInfoTennis.setNewestCouncilB(0);
								setA++;
								tournamentInfoTennis.setNewestSetA(setA);
								if ( setA == winSet ) {
									//A赢过半数, 比赛结束, A获胜
									tournamentInfoTennis.setStatus("已结束");
									tournamentInfoTennis.setNextServe(null);
									tournamentInfoTennis.setEndTime(new Date());
									tournamentInfoTennis.setWinner("A");
									tournamentInfoTennis.setWithdraw("B");
									tournamentInfoTennis.setNextServe(null);
								}
							}
							else{
								tournamentInfoTennis.setNewestCouncilA(councilA);
							}
						}
						else{
							//平分有占先
							if ( scoreB == 60 ) {
								//A40, B Ad, A得分
								tournamentInfoTennis.setNewestSmallB("40");
							}
							else if ( scoreB == 40 ) {
								//A40, B40, A得分
								tournamentInfoTennis.setNewestSmallA("Ad");
							}
							else {
								////A40, B<=30, A得分, 本局结束
								tournamentInfoTennis.setNewestSmallA("0");
								tournamentInfoTennis.setNewestSmallB("0");
								tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
								councilA++;
								if ( (councilA == winCouncil && councilB <= winCouncil - 2) || (councilA == winCouncil+1) ) {
									tournamentInfoTennis.setNewestCouncilA(0);
									tournamentInfoTennis.setNewestCouncilB(0);
									setA++;
									tournamentInfoTennis.setNewestSetA(setA);
									if ( setA == winSet ) {
										//A赢过半数, 比赛结束, A获胜
										tournamentInfoTennis.setStatus("已结束");
										tournamentInfoTennis.setNextServe(null);
										tournamentInfoTennis.setEndTime(new Date());
										tournamentInfoTennis.setWinner("A");
										tournamentInfoTennis.setWithdraw("B");
										tournamentInfoTennis.setNextServe(null);
									}
								}
								else{
									tournamentInfoTennis.setNewestCouncilA(councilA);
								}
							}
						}
						break;
					}
					case 60:{
						//A Ad A得分, 本局结束
						tournamentInfoTennis.setNewestSmallA("0");
						tournamentInfoTennis.setNewestSmallB("0");
						tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
						councilA++;
						if ( (councilA == winCouncil && councilB <= winCouncil - 2) || (councilA == winCouncil+1) ) {
							tournamentInfoTennis.setNewestCouncilA(0);
							tournamentInfoTennis.setNewestCouncilB(0);
							setA++;
							tournamentInfoTennis.setNewestSetA(setA);
							if ( setA == winSet ) {
								//A赢过半数, 比赛结束, A获胜
								tournamentInfoTennis.setStatus("已结束");
								tournamentInfoTennis.setNextServe(null);
								tournamentInfoTennis.setEndTime(new Date());
								tournamentInfoTennis.setWinner("A");
								tournamentInfoTennis.setWithdraw("B");
								tournamentInfoTennis.setNextServe(null);
							}
						}
						else{
							tournamentInfoTennis.setNewestCouncilA(councilA);
						}
						break;
					}
					default:
						break;
				}
			}
			else {
				//B得分
				switch (scoreB) {
					case 0:
						tournamentInfoTennis.setNewestSmallB("15");
						break;
					case 15:
						tournamentInfoTennis.setNewestSmallB("30");
						break;
					case 30:
						tournamentInfoTennis.setNewestSmallB("40");
						break;
					case 40: {
						if ( "平分无占先".equals(competitionSystemSmall) ) {
							//本局结束, small清零, councilB+1
							tournamentInfoTennis.setNewestSmallA("0");
							tournamentInfoTennis.setNewestSmallB("0");
							tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
							councilB++;
							if ( (councilB == winCouncil && councilA <= winCouncil - 2) || councilB == winCouncil + 1 ) {
								tournamentInfoTennis.setNewestCouncilA(0);
								tournamentInfoTennis.setNewestCouncilB(0);
								setB++;
								tournamentInfoTennis.setNewestSetB(setB);
								if ( setB == winSet ) {
									//B赢过半数, 比赛结束, B获胜
									tournamentInfoTennis.setStatus("已结束");
									tournamentInfoTennis.setNextServe(null);
									tournamentInfoTennis.setEndTime(new Date());
									tournamentInfoTennis.setWinner("B");
									tournamentInfoTennis.setWithdraw("A");
									tournamentInfoTennis.setNextServe(null);
								}
							}
							else{
								tournamentInfoTennis.setNewestCouncilB(councilB);
							}
						}
						else{
							//平分有占先
							if ( scoreA == 60 ) {
								//B40, A Ad, B得分
								tournamentInfoTennis.setNewestSmallA("40");
							}
							else if ( scoreA == 40 ) {
								//B40, A40, B得分
								tournamentInfoTennis.setNewestSmallB("Ad");
							}
							else {
								////B40, A<=30, B得分, 本局结束
								tournamentInfoTennis.setNewestSmallA("0");
								tournamentInfoTennis.setNewestSmallB("0");
								tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
								councilB++;
								if ( (councilB == winCouncil && councilA <= winCouncil - 2) || councilB == winCouncil + 1 ) {
									tournamentInfoTennis.setNewestCouncilA(0);
									tournamentInfoTennis.setNewestCouncilB(0);
									setB++;
									tournamentInfoTennis.setNewestSetB(setB);
									if ( setB == winSet ) {
										//B赢过半数, 比赛结束, B获胜
										tournamentInfoTennis.setStatus("已结束");
										tournamentInfoTennis.setNextServe(null);
										tournamentInfoTennis.setEndTime(new Date());
										tournamentInfoTennis.setWinner("B");
										tournamentInfoTennis.setWithdraw("A");
										tournamentInfoTennis.setNextServe(null);
									}
								}
								else{
									tournamentInfoTennis.setNewestCouncilB(councilB);
								}
							}
						}
						break;
					}
					case 60:{
						//B Ad B得分, 本局结束
						tournamentInfoTennis.setNewestSmallA("0");
						tournamentInfoTennis.setNewestSmallB("0");
						tournamentInfoTennis.setNextServe("A".equals(nextServe)?"B":"A");
						councilB++;
						if ( (councilB == winCouncil && councilA <= winCouncil - 2) || councilB == winCouncil + 1 ) {
							tournamentInfoTennis.setNewestCouncilA(0);
							tournamentInfoTennis.setNewestCouncilB(0);
							setB++;
							tournamentInfoTennis.setNewestSetB(setB);
							if ( setB == winSet ) {
								//A赢过半数, 比赛结束, A获胜
								tournamentInfoTennis.setStatus("已结束");
								tournamentInfoTennis.setNextServe(null);
								tournamentInfoTennis.setEndTime(new Date());
								tournamentInfoTennis.setWinner("B");
								tournamentInfoTennis.setWithdraw("A");
								tournamentInfoTennis.setNextServe(null);
							}
						}
						else{
							tournamentInfoTennis.setNewestCouncilB(councilB);
						}
						break;
					}
					default:
						break;
				}
			}
		}
		if ( !"已结束".equals(tournamentInfoTennis.getStatus()) ) {
			String point = "";
			String game = tournamentInfoTennis.getCompetitionSystemGame();
			String council = tournamentInfoTennis.getCompetitionSystemCouncil();
			String small = tournamentInfoTennis.getCompetitionSystemSmall();
			nextServe = tournamentInfoTennis.getNextServe();
//			String noNextServe = "A".equals(nextServe)?"B":"A";
			scoreA = "Ad".equals(tournamentInfoTennis.getNewestSmallA())?60:Integer.parseInt(tournamentInfoTennis.getNewestSmallA());
			scoreB = "Ad".equals(tournamentInfoTennis.getNewestSmallB())?60:Integer.parseInt(tournamentInfoTennis.getNewestSmallB());
			if ( "平分有占先".equals(small) ) 
				point = getPointForMultiSet(tournamentInfoTennis, scoreA, scoreB, nextServe, council, Integer.parseInt(game.substring(0,1))/2);
			else if ( "平分无占先".equals(small) ) 
				point = getPointForMultiSetNoAD(tournamentInfoTennis, scoreA, scoreB, nextServe, council, Integer.parseInt(game.substring(0,1))/2);
			tournamentInfoTennis.setPointType(point);
		}
		else
			tournamentInfoTennis.setPointType("");
		return tournamentInfoTennis;
	}
	@Override
	public String doGainServeScoreTennis(String matchId, String pathType, String isOne, String scoreSide) {
		String hqlForTournamentInfoTennis = "from TournamentInfoTennis where vid = ?";
		List<TournamentInfoTennis> tournamentInfoTenniss = baseDao.queryBySql(hqlForTournamentInfoTennis,Integer.parseInt(matchId));
		if ( tournamentInfoTenniss.isEmpty() ) 
			return "{\"success\":false,\"msg\":\"赛事信息有误, 请重新创建比赛!\"}";
		else{
			if ( "进行中".equals(tournamentInfoTenniss.get(0).getStatus()) ) {
				MatchPathTennis matchPathTennis = new MatchPathTennis();
				matchPathTennis.setTournamentVid(tournamentInfoTenniss.get(0).getVid());
				matchPathTennis.setServeSide(tournamentInfoTenniss.get(0).getNextServe());
				matchPathTennis.setScoreSide(scoreSide);
				matchPathTennis.setIsOne(Integer.parseInt(isOne));
				matchPathTennis.setPathType(Integer.parseInt(pathType));
				matchPathTennis.setCreateTime(new Date());
				matchPathTennis.setOldSetA(tournamentInfoTenniss.get(0).getNewestSetA());
				matchPathTennis.setOldSetB(tournamentInfoTenniss.get(0).getNewestSetB());
				matchPathTennis.setOldCouncilA(tournamentInfoTenniss.get(0).getNewestCouncilA());
				matchPathTennis.setOldCouncilB(tournamentInfoTenniss.get(0).getNewestCouncilB());
				matchPathTennis.setOldSmallA(tournamentInfoTenniss.get(0).getNewestSmallA());
				matchPathTennis.setOldSmallB(tournamentInfoTenniss.get(0).getNewestSmallB());
				matchPathTennis.setPointType(tournamentInfoTenniss.get(0).getPointType());
				TournamentInfoTennis tournamentInfoTennis = gainScore(tournamentInfoTenniss.get(0),scoreSide);
				tournamentInfoTennis.setIsOne(1);
				matchPathTennis.setNewSetA(tournamentInfoTennis.getNewestSetA());
				matchPathTennis.setNewSetB(tournamentInfoTennis.getNewestSetB());
				matchPathTennis.setNewCouncilA(tournamentInfoTennis.getNewestCouncilA());
				matchPathTennis.setNewCouncilB(tournamentInfoTennis.getNewestCouncilB());
				matchPathTennis.setNewSmallA(tournamentInfoTennis.getNewestSmallA());
				matchPathTennis.setNewSmallB(tournamentInfoTennis.getNewestSmallB());
				matchPathTennis.setNextServe(tournamentInfoTennis.getNextServe());
				baseDao.save(matchPathTennis);
				baseDao.update(tournamentInfoTennis);
				StringBuffer stringBuffer = new StringBuffer("{\"success\":true").append(",")
						.append("\"status\":\"").append(tournamentInfoTennis.getStatus()).append("\",")
						.append("\"nextServe\":\"").append(tournamentInfoTennis.getNextServe()).append("\",")
						.append("\"nextServeIsOne\":").append(tournamentInfoTennis.getIsOne()).append(",")
						.append("\"smallA\":\"").append(tournamentInfoTennis.getNewestSmallA()).append("\",")
						.append("\"smallB\":\"").append(tournamentInfoTennis.getNewestSmallB()).append("\",")
						.append("\"councilA\":\"").append(tournamentInfoTennis.getNewestCouncilA()).append("\",")
						.append("\"councilB\":\"").append(tournamentInfoTennis.getNewestCouncilB()).append("\",")
						.append("\"setA\":\"").append(tournamentInfoTennis.getNewestSetA()).append("\",")
						.append("\"setB\":\"").append(tournamentInfoTennis.getNewestSetB()).append("\"}");
				return stringBuffer.toString();
			}
			else
				return "{\"success\":false,\"msg\":\"赛事数据有误, 请联系管理员!\"}";
		}
	}
	@Override
	public String doQueryTennisMatchInfoForInit(String matchId) {
		String hqlForTournamentInfoTennis = "from TournamentInfoTennis where vid = ?";
		List<TournamentInfoTennis> tournamentInfoTenniss = baseDao.queryBySql(hqlForTournamentInfoTennis,Integer.parseInt(matchId));
		if ( tournamentInfoTenniss.isEmpty() ) 
			return "{\"success\":false,\"msg\":\"赛事信息有误, 请重新创建比赛!\"}";
		else{
			//返回:matchId,nextServe,isOne,nameA,nameB
			TournamentInfoTennis tournamentInfoTennis = tournamentInfoTenniss.get(0);
			StringBuffer stringBuffer = new StringBuffer("{\"success\":true,")
					.append("\"matchId\":\"").append(matchId).append("\",")
					.append("\"nextServe\":\"").append(tournamentInfoTennis.getNextServe()).append("\",")
					.append("\"isOne\":\"").append(tournamentInfoTennis.getIsOne()).append("\",")
					.append("\"smallA\":\"").append(tournamentInfoTennis.getNewestSmallA()).append("\",")
					.append("\"smallB\":\"").append(tournamentInfoTennis.getNewestSmallB()).append("\",")
					.append("\"councilA\":\"").append(tournamentInfoTennis.getNewestCouncilA()).append("\",")
					.append("\"councilB\":\"").append(tournamentInfoTennis.getNewestCouncilB()).append("\",")
					.append("\"setA\":\"").append(tournamentInfoTennis.getNewestSetA()).append("\",")
					.append("\"setB\":\"").append(tournamentInfoTennis.getNewestSetB()).append("\",")
					.append("\"nameA\":\"").append(tournamentInfoTennis.getHome_1_name()+" "+tournamentInfoTennis.getHome_2_name()).append("\",")
					.append("\"nameB\":\"").append(tournamentInfoTennis.getAway_1_name()+" "+tournamentInfoTennis.getAway_2_name()).append("\"}");
			return stringBuffer.toString();
		}
	}
	@Override
	public String doServeFaultTennis(String matchId, String pathType, String isOne) {
		String hqlForTournamentInfoTennis = "from TournamentInfoTennis where vid = ?";
		List<TournamentInfoTennis> tournamentInfoTenniss = baseDao.queryBySql(hqlForTournamentInfoTennis,Integer.parseInt(matchId));
		if ( tournamentInfoTenniss.isEmpty() ) 
			return "{\"success\":false,\"msg\":\"赛事信息有误, 请重新创建比赛!\"}";
		else{
			TournamentInfoTennis tournamentInfoTennis = tournamentInfoTenniss.get(0);
			
			MatchPathTennis matchPathTennis = new MatchPathTennis();
			matchPathTennis.setTournamentVid(tournamentInfoTennis.getVid());
			matchPathTennis.setServeSide(tournamentInfoTennis.getNextServe());
			matchPathTennis.setIsOne(Integer.parseInt(isOne));
			matchPathTennis.setPathType(Integer.parseInt(pathType));
			matchPathTennis.setCreateTime(new Date());
			matchPathTennis.setOldSetA(tournamentInfoTennis.getNewestSetA());
			matchPathTennis.setOldSetB(tournamentInfoTennis.getNewestSetB());
			matchPathTennis.setOldCouncilA(tournamentInfoTennis.getNewestCouncilA());
			matchPathTennis.setOldCouncilB(tournamentInfoTennis.getNewestCouncilB());
			matchPathTennis.setOldSmallA(tournamentInfoTennis.getNewestSmallA());
			matchPathTennis.setOldSmallB(tournamentInfoTennis.getNewestSmallB());
			matchPathTennis.setPointType(tournamentInfoTennis.getPointType());
			if ( "1".equals(isOne) ) {
				//一发失误
				String scoreSide = "";
				matchPathTennis.setScoreSide(scoreSide);
				matchPathTennis.setNewSetA(tournamentInfoTennis.getNewestSetA());
				matchPathTennis.setNewSetB(tournamentInfoTennis.getNewestSetB());
				matchPathTennis.setNewCouncilA(tournamentInfoTennis.getNewestCouncilA());
				matchPathTennis.setNewCouncilB(tournamentInfoTennis.getNewestCouncilB());
				matchPathTennis.setNewSmallA(tournamentInfoTennis.getNewestSmallA());
				matchPathTennis.setNewSmallB(tournamentInfoTennis.getNewestSmallB());
				matchPathTennis.setNextServe(tournamentInfoTennis.getNextServe());
				tournamentInfoTennis.setIsOne(0);
			}
			else if( "0".equals(isOne) ){
				//双误
				String scoreSide = "A".equals(tournamentInfoTennis.getNextServe())?"B":"A";
				matchPathTennis.setScoreSide(scoreSide);
				tournamentInfoTennis = gainScore(tournamentInfoTennis, scoreSide);
				matchPathTennis.setNewSetA(tournamentInfoTennis.getNewestSetA());
				matchPathTennis.setNewSetB(tournamentInfoTennis.getNewestSetB());
				matchPathTennis.setNewCouncilA(tournamentInfoTennis.getNewestCouncilA());
				matchPathTennis.setNewCouncilB(tournamentInfoTennis.getNewestCouncilB());
				matchPathTennis.setNewSmallA(tournamentInfoTennis.getNewestSmallA());
				matchPathTennis.setNewSmallB(tournamentInfoTennis.getNewestSmallB());
				matchPathTennis.setNextServe(tournamentInfoTennis.getNextServe());
				tournamentInfoTennis.setIsOne(1);
			}
			baseDao.save(matchPathTennis);
			baseDao.update(tournamentInfoTennis);
			StringBuffer stringBuffer = new StringBuffer("{\"success\":true").append(",")
				.append("\"status\":\"").append(tournamentInfoTennis.getStatus()).append("\",")
				.append("\"nextServe\":\"").append(tournamentInfoTennis.getNextServe()).append("\",")
				.append("\"nextServeIsOne\":").append(tournamentInfoTennis.getIsOne()).append(",")
				.append("\"smallA\":\"").append(tournamentInfoTennis.getNewestSmallA()).append("\",")
				.append("\"smallB\":\"").append(tournamentInfoTennis.getNewestSmallB()).append("\",")
				.append("\"councilA\":\"").append(tournamentInfoTennis.getNewestCouncilA()).append("\",")
				.append("\"councilB\":\"").append(tournamentInfoTennis.getNewestCouncilB()).append("\",")
				.append("\"setA\":\"").append(tournamentInfoTennis.getNewestSetA()).append("\",")
				.append("\"setB\":\"").append(tournamentInfoTennis.getNewestSetB()).append("\"}");
			return stringBuffer.toString();
		}
	}
	@Override
	public String doRevokeTennisScore(String matchId) {
		String hqlForTournamentInfoTennis = "from TournamentInfoTennis where vid = ? and status != '未开始'";
		List<TournamentInfoTennis> tournamentInfoTenniss = baseDao.queryBySql(hqlForTournamentInfoTennis,Integer.parseInt(matchId));
		if ( tournamentInfoTenniss.isEmpty() ) 
			return "{\"success\":false,\"msg\":\"赛事信息有误, 请重新创建比赛!\"}";
		else{
			TournamentInfoTennis tournamentInfoTennis = tournamentInfoTenniss.get(0);
			tournamentInfoTennis.setStatus("进行中");
			String hqlForMatchPathTennis = "from MatchPathTennis where tournamentVid = ? order by vid desc";
			List<MatchPathTennis> matchPathTenniss = baseDao.queryBySqlLimit(hqlForMatchPathTennis, Integer.parseInt(matchId), 0, 1);
			if ( !matchPathTenniss.isEmpty() ) {
				MatchPathTennis matchPathTennis = matchPathTenniss.get(0);
				tournamentInfoTennis.setNewestSetA(matchPathTennis.getOldSetA());
				tournamentInfoTennis.setNewestSetB(matchPathTennis.getOldSetB());
				tournamentInfoTennis.setNewestCouncilA(matchPathTennis.getOldCouncilA());
				tournamentInfoTennis.setNewestCouncilB(matchPathTennis.getOldCouncilB());
				tournamentInfoTennis.setNewestSmallA(matchPathTennis.getOldSmallA());
				tournamentInfoTennis.setNewestSmallB(matchPathTennis.getOldSmallB());
				tournamentInfoTennis.setNextServe(matchPathTennis.getServeSide());
				tournamentInfoTennis.setIsOne(matchPathTennis.getIsOne());
				tournamentInfoTennis.setPointType(matchPathTennis.getPointType());
				baseDao.update(tournamentInfoTennis);
				baseDao.delete(matchPathTennis);
			}
			StringBuffer stringBuffer = new StringBuffer("{\"success\":true").append(",")
				.append("\"status\":\"").append(tournamentInfoTennis.getStatus()).append("\",")
				.append("\"nextServe\":\"").append(tournamentInfoTennis.getNextServe()).append("\",")
				.append("\"nextServeIsOne\":").append(tournamentInfoTennis.getIsOne()).append(",")
				.append("\"smallA\":\"").append(tournamentInfoTennis.getNewestSmallA()).append("\",")
				.append("\"smallB\":\"").append(tournamentInfoTennis.getNewestSmallB()).append("\",")
				.append("\"councilA\":\"").append(tournamentInfoTennis.getNewestCouncilA()).append("\",")
				.append("\"councilB\":\"").append(tournamentInfoTennis.getNewestCouncilB()).append("\",")
				.append("\"setA\":\"").append(tournamentInfoTennis.getNewestSetA()).append("\",")
				.append("\"setB\":\"").append(tournamentInfoTennis.getNewestSetB()).append("\"}");
			return stringBuffer.toString();
		}
	}
	private String getPointForDiffCouncil(TournamentInfoTennis tournamentInfo, int gameNum, int smallA, int smallB, String nextServe){
		String point = "";
		if ( isOnlySevenOr11Set(tournamentInfo) > 0 ) {
			int sevenOr11 = isOnlySevenOr11Set(tournamentInfo)-1;
			if ( (smallA == sevenOr11 && smallB < sevenOr11) || (smallA > sevenOr11 && (smallA - smallB == 1)) ) {
				point = (smallA-smallB) + " match point";
			}
			else if ( (smallB == sevenOr11 && smallA < sevenOr11) || (smallB > sevenOr11 && (smallB - smallA == 1)) ) {
				point = (smallB-smallA) + " match point";
			}
			else {
				point = "";
			}
		}
		else if ( tournamentInfo.getNewestCouncilA() == gameNum && tournamentInfo.getNewestCouncilB() == gameNum ) {
			//抢7局
			if ( (smallA == 6 && smallB < 6) || (smallA > 6 && (smallA - smallB == 1)) ) {
				point = (smallA-smallB) + " match point";
			}
			else if ( (smallB == 6 && smallA <6) || (smallB > 6 && (smallB - smallA == 1)) ) {
				point = (smallB-smallA) + " match point";
			}
			else {
				point = "";
			}
		}
		else {
			//非抢七局
			if ( tournamentInfo.getNewestCouncilA() >= gameNum-1  && tournamentInfo.getNewestCouncilA()-tournamentInfo.getNewestCouncilB() >=1 ){
				//A局分领先
				if ( smallA == 60 || ( smallA == 40 && smallB < 40 ) ) {
					//A或B发球，A领先
					switch( smallB ){
						case 0:
							point = "3 match point";
							break;
						case 15:
							point = "2 match point";
							break;
						case 30:
							point = "1 match point";
							break;
						case 40:
							point = "1 match point";
							break;
					}
					
				}
				else if ( "A".equals(nextServe) && (smallB == 60 || ( smallB == 40 && smallA < 40 )) ) {
					//A发球，B领先
					switch( smallA ){
						case 0:
							point = "3 break point";
							break;
						case 15:
							point = "2 break point";
							break;
						case 30:
							point = "1 break point";
							break;
						case 40:
							point = "1 break point";
							break;
					}
				}
				else if ( "B".equals(nextServe) && (smallB == 60 || ( smallB == 40 && smallA < 40 )) ) {
					//B发球，B领先
					switch( smallA ){
						case 0:
							point = "3 game point";
							break;
						case 15:
							point = "2 game point";
							break;
						case 30:
							point = "1 game point";
							break;
						case 40:
							point = "1 game point";
							break;
					}
				}
				else {
					point = "";
				}
			}
			else if ( tournamentInfo.getNewestCouncilB() >= gameNum-1 && tournamentInfo.getNewestCouncilB() - tournamentInfo.getNewestCouncilA() >=1 ) {
				//B局分领先
				if ( "A".equals(nextServe) && (smallA == 60 || ( smallA == 40 && smallB < 40 )) ) {
					//A发球，A领先
					switch( smallB ){
						case 0:
							point = "3 game point";
							break;
						case 15:
							point = "2 game point";
							break;
						case 30:
							point = "1 game point";
							break;
						case 40:
							point = "1 game point";
							break;
					}
				}
				else if ( "B".equals(nextServe) && (smallA == 60 || ( smallA == 40 && smallB < 40 )) ) {
					//B发球，A领先
					switch( smallB ){
						case 0:
							point = "3 break point";
							break;
						case 15:
							point = "2 break point";
							break;
						case 30:
							point = "1 break point";
							break;
						case 40:
							point = "1 break point";
							break;
					}
				}
				else if ( smallB == 60 || ( smallB == 40 && smallA < 40 ) ) {
					//A或B发球，B领先
					switch( smallA ){
						case 0:
							point = "3 match point";
							break;
						case 15:
							point = "2 match point";
							break;
						case 30:
							point = "1 match point";
							break;
						case 40:
							point = "1 match point";
							break;
					}
				}
				else {
					point = "";
				}
			}
			else {
				if ( "A".equals(nextServe) && (smallA == 60 || ( smallA == 40 && smallB < 40 )) ) {
					//A发球，A领先
					switch( smallB ){
						case 0:
							point = "3 game point";
							break;
						case 15:
							point = "2 game point";
							break;
						case 30:
							point = "1 game point";
							break;
						case 40:
							point = "1 game point";
							break;
					}
				}
				else if ( "B".equals(nextServe) && (smallA == 60 || ( smallA == 40 && smallB < 40 )) ) {
					//B发球，A领先
					switch( smallB ){
						case 0:
							point = "3 break point";
							break;
						case 15:
							point = "2 break point";
							break;
						case 30:
							point = "1 break point";
							break;
						case 40:
							point = "1 break point";
							break;
					}
				}
				else if ( "A".equals(nextServe) && (smallB == 60 || ( smallB == 40 && smallA < 40 )) ) {
					//A发球，B领先
					switch( smallA ){
						case 0:
							point = "3 break point";
							break;
						case 15:
							point = "2 break point";
							break;
						case 30:
							point = "1 break point";
							break;
						case 40:
							point = "1 break point";
							break;
					}
				}
				else if ( "B".equals(nextServe) && (smallB == 60 || ( smallB == 40 && smallA < 40 )) ) {
					//B发球，B领先
					switch( smallA ){
						case 0:
							point = "3 game point";
							break;
						case 15:
							point = "2 game point";
							break;
						case 30:
							point = "1 game point";
							break;
						case 40:
							point = "1 game point";
							break;
					}
				}
				else {
					point = "";
				}
			}
		}
		return point;
	}
	private String getPointForDiffCouncilNoAD(TournamentInfoTennis tournamentInfo, int gameNum, int smallA, int smallB, String nextServe){
		String point = "";
		if ( isOnlySevenOr11Set(tournamentInfo) > 0 ) {
			int sevenOr11 = isOnlySevenOr11Set(tournamentInfo)-1;
			if ( smallA == sevenOr11 ) {
				point = (smallA-smallB+1) + " match point";
			}
			else if (  smallB == sevenOr11 ) {
				point = (smallB-smallA+1) + " match point";
			}
			else {
				point = "";
			}
		}
		else if ( tournamentInfo.getNewestCouncilA() == gameNum && tournamentInfo.getNewestCouncilB() == gameNum ) {
			//抢7局
			if ( (smallA == 6 && smallB < 6) || (smallA > 6 && (smallA - smallB == 1)) ) {
				point = (smallA-smallB) + " match point";
			}
			else if ( (smallB == 6 && smallA <6) || (smallB > 6 && (smallB - smallA == 1)) ) {
				point = (smallB-smallA) + " match point";
			}
			else {
				point = "";
			}
		}
		else {
			//非抢七局
			if ( tournamentInfo.getNewestCouncilA() >= gameNum-1 && tournamentInfo.getNewestCouncilA()-tournamentInfo.getNewestCouncilB() >=1 ) {
				//A局分领先
				if ( "A".equals(nextServe) ) {
					//A发球
					if ( smallB == 40 ) {
						//A局分领先,A发球,B小分领先,破发
						switch( smallA ){
							case 0:
								point = "4 break point";
								break;
							case 15:
								point = "3 break point";
								break;
							case 30:
								point = "2 break point";
								break;
							default:
								point = "1 break point";
								break;
						}
					}
					else if ( smallA == 40 ) {
						//A局分领先,A发球,A小分领先,赛点
						switch( smallB ){
							case 0:
								point = "4 match point";
								break;
							case 15:
								point = "3 match point";
								break;
							case 30:
								point = "2 match point";
								break;
							default:
								point = "1 match point";
								break;
						}
					}
					else {
						//谁小分都不领先,无点
						point = "";
					}
				}
				else if ( "B".equals(nextServe) ) {
					//B发球
					if ( smallA == 40 ) {
						//A局分领先,B发球,A小分领先,破发
						switch( smallB ){
							case 0:
								point = "4 match point";
								break;
							case 15:
								point = "3 match point";
								break;
							case 30:
								point = "2 match point";
								break;
							default:
								point = "1 match point";
								break;
						}
					}
					else if ( smallB == 40 ) {
						//A局分领先,B发球,B小分领先
						switch( smallA ){
							case 0:
								point = "4 game point";
								break;
							case 15:
								point = "3 game point";
								break;
							case 30:
								point = "2 game point";
								break;
							default:
								point = "1 game point";
								break;
						}
					}
					else {
						point = "";
					}
				}
			}
			else if ( tournamentInfo.getNewestCouncilB() >= gameNum-1 && tournamentInfo.getNewestCouncilB()-tournamentInfo.getNewestCouncilA() >= 1 ) {
				//B局分领先
				if ( "A".equals(nextServe) ) {
					//A发球
					if ( smallB == 40 ) {
						//B局分领先,A发球,B小分领先,赛点
						switch( smallA ){
							case 0:
								point = "4 match point";
								break;
							case 15:
								point = "3 match point";
								break;
							case 30:
								point = "2 match point";
								break;
							default:
								point = "1 match point";
								break;
						}
					}
					else if ( smallA == 40 ) {
						//B局分领先,A发球,A小分领先,盘点
						switch( smallB ){
							case 0:
								point = "4 game point";
								break;
							case 15:
								point = "3 game point";
								break;
							case 30:
								point = "2 game point";
								break;
							default:
								point = "1 game point";
								break;
						}
					}
					else {
						//谁小分都不领先,无点
						point = "";
					}
				}
				else if ( "B".equals(nextServe) ) {
					//B发球
					if ( smallA == 40 ) {
						//B局分领先,B发球,A小分领先,破发
						switch( smallB ){
							case 0:
								point = "4 break point";
								break;
							case 15:
								point = "3 break point";
								break;
							case 30:
								point = "2 break point";
								break;
							default:
								point = "1 break point";
								break;
						}
					}
					else if ( smallB == 40 ) {
						//B局分领先,B发球,B小分领先,赛点
						switch( smallA ){
							case 0:
								point = "4 match point";
								break;
							case 15:
								point = "3 match point";
								break;
							case 30:
								point = "2 match point";
								break;
							default:
								point = "1 match point";
								break;
						}
					}
					else {
						point = "";
					}
				}
			}
			else {
				if ( "A".equals(nextServe) ) {
					//A发球
					if ( smallB == 40 ) {
						//A发球,B小分领先,破发
						switch( smallA ){
							case 0:
								point = "4 break point";
								break;
							case 15:
								point = "3 break point";
								break;
							case 30:
								point = "2 break point";
								break;
							default:
								point = "1 break point";
								break;
						}
					}
					else if ( smallA == 40 ) {
						//A发球,A小分领先,盘点
						switch( smallB ){
							case 0:
								point = "4 game point";
								break;
							case 15:
								point = "3 game point";
								break;
							case 30:
								point = "2 game point";
								break;
							default:
								point = "1 game point";
								break;
						}
					}
					else {
						//谁小分都不领先,无点
						point = "";
					}
				}
				else if ( "B".equals(nextServe) ) {
					//B发球
					if ( smallA == 40 ) {
						//B发球,A小分领先,破发
						switch( smallB ){
							case 0:
								point = "4 break point";
								break;
							case 15:
								point = "3 break point";
								break;
							case 30:
								point = "2 break point";
								break;
							default:
								point = "1 break point";
								break;
						}
					}
					else if ( smallB == 40 ) {
						//B发球,B小分领先,盘点
						switch( smallA ){
							case 0:
								point = "4 game point";
								break;
							case 15:
								point = "3 game point";
								break;
							case 30:
								point = "2 game point";
								break;
							default:
								point = "1 game point";
								break;
						}
					}
					else {
						point = "";
					}
				}
			}
		}
		return point;
	}
	private String getPointForDiffCouncil(TournamentInfoTennis tournamentInfo, int gameNum, int smallA, int smallB, String nextServe, String AdSide){
		String point = "";
		if ( tournamentInfo.getNewestCouncilA() == gameNum && tournamentInfo.getNewestCouncilB() == gameNum ) {
			//抢7局
			if ( (smallA == 6 && smallB < 6) || (smallA > 6 && (smallA - smallB == 1)) ) {
				point = "A".equals(AdSide)?((smallA-smallB)+" match point"):((smallA-smallB)+" set point");
			}
			else if ( (smallB == 6 && smallA <6) || (smallB > 6 && (smallB - smallA == 1)) ) {
				point = "B".equals(AdSide)?((smallB-smallA)+" match point"):((smallB-smallA)+" set point");
			}
			else {
				point = "";
			}
		}
		else {
			//非抢七局
			if ( tournamentInfo.getNewestCouncilA() >= gameNum-1 && tournamentInfo.getNewestCouncilA()-tournamentInfo.getNewestCouncilB() >= 1 ){
				//A局分领先
				if ( smallA == 60 || ( smallA == 40 && smallB < 40 ) ) {
					//A或B发球，A领先
					switch( smallB ){
						case 0:
							point = "A".equals(AdSide)?"3 match point":"3 set point";
							break;
						case 15:
							point = "A".equals(AdSide)?"2 match point":"2 set point";
							break;
						case 30:
							point = "A".equals(AdSide)?"1 match point":"1 set point";
							break;
						case 40:
							point = "A".equals(AdSide)?"1 match point":"1 set point";
							break;
					}
				}
				else if ( "A".equals(nextServe) && (smallB == 60 || ( smallB == 40 && smallA < 40 )) ) {
					//A发球，B领先
					switch( smallA ){
						case 0:
							point = "3 break point";
							break;
						case 15:
							point = "2 break point";
							break;
						case 30:
							point = "1 break point";
							break;
						case 40:
							point = "1 break point";
							break;
					}
				}
				else if ( "B".equals(nextServe) && (smallB == 60 || ( smallB == 40 && smallA < 40 )) ) {
					//B发球，B领先
					switch( smallA ){
						case 0:
							point = "3 game point";
							break;
						case 15:
							point = "2 game point";
							break;
						case 30:
							point = "1 game point";
							break;
						case 40:
							point = "1 game point";
							break;
					}
				}
				else {
					point = "";
				}
			}
			else if ( tournamentInfo.getNewestCouncilB() >= gameNum-1 && tournamentInfo.getNewestCouncilB()-tournamentInfo.getNewestCouncilA() >= 1 ) {
				//B局分领先
				if ( smallB == 60 || ( smallB == 40 && smallA < 40 ) ) {
					//A或B发球，B领先
					switch( smallA ){
						case 0:
							point = "A".equals(AdSide)?"3 match point":"3 set point";
							break;
						case 15:
							point = "A".equals(AdSide)?"2 match point":"2 set point";
							break;
						case 30:
							point = "A".equals(AdSide)?"1 match point":"1 set point";
							break;
						case 40:
							point = "A".equals(AdSide)?"1 match point":"1 set point";
							break;
					}
				}
				else if ( "B".equals(nextServe) && (smallA == 60 || ( smallA == 40 && smallB < 40 )) ) {
					//B发球，A领先
					switch( smallB ){
						case 0:
							point = "3 break point";
							break;
						case 15:
							point = "2 break point";
							break;
						case 30:
							point = "1 break point";
							break;
						case 40:
							point = "1 break point";
							break;
					}
				}
				else if ( "A".equals(nextServe) && (smallA == 60 || ( smallA == 40 && smallB < 40 )) ) {
					//A发球，A领先
					switch( smallB ){
						case 0:
							point = "3 game point";
							break;
						case 15:
							point = "2 game point";
							break;
						case 30:
							point = "1 game point";
							break;
						case 40:
							point = "1 game point";
							break;
					}
				}
				else {
					point = "";
				}
			}
			else {
				if ( "A".equals(nextServe) && (smallA == 60 || ( smallA == 40 && smallB < 40 )) ) {
					//A发球，A领先
					switch( smallB ){
						case 0:
							point = "3 game point";
							break;
						case 15:
							point = "2 game point";
							break;
						case 30:
							point = "1 game point";
							break;
						case 40:
							point = "1 game point";
							break;
					}
				}
				else if ( "B".equals(nextServe) && (smallA == 60 || ( smallA == 40 && smallB < 40 )) ) {
					//B发球，A领先
					switch( smallB ){
						case 0:
							point = "3 break point";
							break;
						case 15:
							point = "2 break point";
							break;
						case 30:
							point = "1 break point";
							break;
						case 40:
							point = "1 break point";
							break;
					}
				}
				else if ( "A".equals(nextServe) && (smallB == 60 || ( smallB == 40 && smallA < 40 )) ) {
					//A发球，B领先
					switch( smallA ){
						case 0:
							point = "3 break point";
							break;
						case 15:
							point = "2 break point";
							break;
						case 30:
							point = "1 break point";
							break;
						case 40:
							point = "1 break point";
							break;
					}
				}
				else if ( "B".equals(nextServe) && (smallB == 60 || ( smallB == 40 && smallA < 40 )) ) {
					//B发球，B领先
					switch( smallA ){
						case 0:
							point = "3 game point";
							break;
						case 15:
							point = "2 game point";
							break;
						case 30:
							point = "1 game point";
							break;
						case 40:
							point = "1 game point";
							break;
					}
				}
				else {
					point = "";
				}
			}
		}
		return point;
	}
	private String getPointForDiffCouncilNoAD(TournamentInfoTennis tournamentInfo, int gameNum, int smallA, int smallB, String nextServe, String AdSide){
		String point = "";
		if ( tournamentInfo.getNewestCouncilA() == gameNum && tournamentInfo.getNewestCouncilB() == gameNum ) {
			//抢7局
			if ( (smallA == 6 && smallB < 6) || (smallA > 6 && (smallA - smallB == 1)) ) {
				point = "A".equals(AdSide)?((smallA-smallB)+" match point"):((smallA-smallB)+" set point");
			}
			else if ( (smallB == 6 && smallA <6) || (smallB > 6 && (smallB - smallA == 1)) ) {
				point = "B".equals(AdSide)?((smallB-smallA)+" match point"):((smallB-smallA)+" set point");
			}
			else {
				point = "";
			}
		}
		else {
			//非抢七局
			if ( tournamentInfo.getNewestCouncilA() >= gameNum-1 && tournamentInfo.getNewestCouncilA()-tournamentInfo.getNewestCouncilB() >= 1 ){
				//A局分领先
				if ( "A".equals(nextServe) ) {
					//A发球
					if ( smallB == 40 ) {
						//A局分领先,A发球,B小分领先,破发
						switch( smallA ){
							case 0:
								point = "4 break point";
								break;
							case 15:
								point = "3 break point";
								break;
							case 30:
								point = "2 break point";
								break;
							default:
								point = "1 break point";
								break;
						}
					}
					else if ( smallA == 40 ) {
						//A局分领先,A发球,A小分领先,赛点
						switch( smallB ){
							case 0:
								point = "A".equals(AdSide)?"4 match point":"4 set point";
								break;
							case 15:
								point = "A".equals(AdSide)?"3 match point":"3 set point";
								break;
							case 30:
								point = "A".equals(AdSide)?"2 match point":"2 set point";
								break;
							default:
								point = "A".equals(AdSide)?"1 match point":"1 set point";
								break;
						}
					}
					else {
						//谁小分都不领先,无点
						point = "";
					}
				}
				else if ( "B".equals(nextServe) ) {
					//B发球
					if ( smallA == 40 ) {
						//A局分领先,B发球,A小分领先,破发
						switch( smallA ){
							case 0:
								point = "A".equals(AdSide)?"4 match point":"4 set point";
								break;
							case 15:
								point = "A".equals(AdSide)?"3 match point":"3 set point";
								break;
							case 30:
								point = "A".equals(AdSide)?"2 match point":"2 set point";
								break;
							default:
								point = "A".equals(AdSide)?"1 match point":"1 set point";
								break;
						}
					}
					else if ( smallB == 40 ) {
						//A局分领先,B发球,B小分领先,盘点
						switch( smallA ){
							case 0:
								point = "4 game point";
								break;
							case 15:
								point = "3 game point";
								break;
							case 30:
								point = "2 game point";
								break;
							default:
								point = "1 game point";
								break;
						}
					}
					else {
						//谁小分都不领先,无点
						point = "";
					}
				}
			}
			else if ( tournamentInfo.getNewestCouncilB() >= gameNum-1 && tournamentInfo.getNewestCouncilB()-tournamentInfo.getNewestCouncilA() >= 1 ) {
				//B局分领先
				if ( "A".equals(nextServe) ) {
					//A发球
					if ( smallB == 40 ) {
						//B局分领先,A发球,B小分领先,破发
						switch( smallA ){
							case 0:
								point = "A".equals(AdSide)?"4 set point":"4 match point";
								break;
							case 15:
								point = "A".equals(AdSide)?"3 set point":"3 match point";
								break;
							case 30:
								point = "A".equals(AdSide)?"2 set point":"2 match point";
								break;
							default:
								point = "A".equals(AdSide)?"1 set point":"1 match point";
								break;
						}
					}
					else if ( smallA == 40 ) {
						//B局分领先,A发球,A小分领先,盘点
						switch( smallB ){
							case 0:
								point = "4 game point";
								break;
							case 15:
								point = "3 game point";
								break;
							case 30:
								point = "2 game point";
								break;
							default:
								point = "1 game point";
								break;
						}
					}
					else {
						//谁小分都不领先,无点
						point = "";
					}
				}
				else if ( "B".equals(nextServe) ) {
					//B发球
					if ( smallA == 40 ) {
						//B局分领先,B发球,A小分领先,破发
						switch( smallA ){
							case 0:
								point = "4 break point";
								break;
							case 15:
								point = "3 break point";
								break;
							case 30:
								point = "2 break point";
								break;
							default:
								point = "1 break point";
								break;
						}
					}
					else if ( smallB == 40 ) {
						//B局分领先,B发球,B小分领先,盘点
						switch( smallA ){
							case 0:
								point = "A".equals(AdSide)?"4 set point":"4 match point";
								break;
							case 15:
								point = "A".equals(AdSide)?"3 set point":"3 match point";
								break;
							case 30:
								point = "A".equals(AdSide)?"2 set point":"2 match point";
								break;
							default:
								point = "A".equals(AdSide)?"1 set point":"1 match point";
								break;
						}
					}
					else {
						//谁小分都不领先,无点
						point = "";
					}
				}
			}
			else {
				if ( "A".equals(nextServe) ) {
					//A发球
					if ( smallB == 40 ) {
						//A发球,B小分领先,破发
						switch( smallA ){
							case 0:
								point = "4 break point";
								break;
							case 15:
								point = "3 break point";
								break;
							case 30:
								point = "2 break point";
								break;
							default:
								point = "1 break point";
								break;
						}
					}
					else if ( smallA == 40 ) {
						//A发球,A小分领先,盘点
						switch( smallB ){
							case 0:
								point = "4 game point";
								break;
							case 15:
								point = "3 game point";
								break;
							case 30:
								point = "2 game point";
								break;
							default:
								point = "1 game point";
								break;
						}
					}
					else {
						//谁小分都不领先,无点
						point = "";
					}
				}
				else if ( "B".equals(nextServe) ) {
					//B发球
					if ( smallA == 40 ) {
						//B发球,A小分领先,破发
						switch( smallA ){
							case 0:
								point = "4 break point";
								break;
							case 15:
								point = "3 break point";
								break;
							case 30:
								point = "2 break point";
								break;
							default:
								point = "1 break point";
								break;
						}
					}
					else if ( smallB == 40 ) {
						//B发球,B小分领先,盘点
						switch( smallA ){
							case 0:
								point = "4 game point";
								break;
							case 15:
								point = "3 game point";
								break;
							case 30:
								point = "2 game point";
								break;
							default:
								point = "1 game point";
								break;
						}
					}
					else {
						//谁小分都不领先,无点
						point = "";
					}
				}
			}
		}
		return point;
	}
	private String getPointForLast1Set(TournamentInfoTennis tournamentInfo, int smallA, int smallB, String nextServe, String council){
		return getPointForDiffCouncil(tournamentInfo, Integer.parseInt(council.substring(0,1)), smallA, smallB, nextServe);
	}
	private String getPointForLast1SetNoAD(TournamentInfoTennis tournamentInfo, int smallA, int smallB, String nextServe, String council){
		return getPointForDiffCouncilNoAD(tournamentInfo, Integer.parseInt(council.substring(0,1)), smallA, smallB, nextServe);
	}
	private String getPointFor1SetAd(TournamentInfoTennis tournamentInfo, int smallA, int smallB, String nextServe, String council, String AdSide){
		return getPointForDiffCouncil(tournamentInfo, Integer.parseInt(council.substring(0,1)), smallA, smallB, nextServe, AdSide);
	}
	private String getPointFor1SetAdNoAD(TournamentInfoTennis tournamentInfo, int smallA, int smallB, String nextServe, String council, String AdSide){
		return getPointForDiffCouncilNoAD(tournamentInfo, Integer.parseInt(council.substring(0,1)), smallA, smallB, nextServe, AdSide);
	}
	private String getPointForMultiSet(TournamentInfoTennis tournamentInfo, int smallA, int smallB, String nextServe, String council, Integer winPoint){
		String point = "";
		if ( tournamentInfo.getNewestSetA().equals(winPoint) && tournamentInfo.getNewestSetB().equals(winPoint) ) {
			//都赢一盘 , 退化为1盘制
			point = getPointForLast1Set(tournamentInfo, smallA, smallB, nextServe, council);
		}
		else if ( tournamentInfo.getNewestSetA().equals(winPoint) ) {
			//A局分领先（一方领先）
			point = getPointFor1SetAd(tournamentInfo,smallA,smallB,nextServe,council,"A");
		}
		else if ( tournamentInfo.getNewestSetB().equals(winPoint) ) {
			//B领先（一方领先）
			point = getPointFor1SetAd(tournamentInfo,smallA,smallB,nextServe,council,"B");
		}
		else {
			//都为0，一盘制，match point 替换为set point
			point = getPointForLast1Set(tournamentInfo, smallA, smallB, nextServe, council).replace("match", "set");
		}
		return point;
	}
	private String getPointForMultiSetNoAD(TournamentInfoTennis tournamentInfo, int smallA, int smallB, String nextServe, String council, Integer winPoint){
		String point = "";
		if ( tournamentInfo.getNewestSetA().equals(winPoint) && tournamentInfo.getNewestSetB().equals(winPoint) ) {
			//都赢一盘 , 退化为1盘制
			point = getPointForLast1SetNoAD(tournamentInfo, smallA, smallB, nextServe, council);
		}
		else if ( tournamentInfo.getNewestSetA().equals(winPoint) ) {
			//A局分领先（一方领先）
			point = getPointFor1SetAdNoAD(tournamentInfo,smallA,smallB,nextServe,council,"A");
		}
		else if ( tournamentInfo.getNewestSetB().equals(winPoint) ) {
			//B领先（一方领先）
			point = getPointFor1SetAdNoAD(tournamentInfo,smallA,smallB,nextServe,council,"B");
		}
		else {
			//都为0，一盘制，match point 替换为set point
			point = getPointForLast1SetNoAD(tournamentInfo, smallA, smallB, nextServe, council).replace("match", "set");
		}
		return point;
	}
	@Override
	public String doQueryTennisMatchList() {
		String hqlForTournamentInfoTennis = "from TournamentInfoTennis order by createTime desc";
		List<TournamentInfoTennis> tournamentInfoTenniss = baseDao.queryBySqlLimit(hqlForTournamentInfoTennis, 0, 10);
		StringBuffer stringBuffer = new StringBuffer("{\"success\":true,\"list\":[");
		for (int i = 0; i < tournamentInfoTenniss.size(); i++) {
			TournamentInfoTennis tournamentInfoTennis = tournamentInfoTenniss.get(i);
			stringBuffer.append(i==0?"":",")
			.append("{\"nameA\":\"").append(tournamentInfoTennis.getHome_1_name()).append("".equals(tournamentInfoTennis.getHome_2_name())?"":("/"+tournamentInfoTennis.getHome_2_name())).append("\"")
			.append(",\"nameB\":\"").append(tournamentInfoTennis.getAway_1_name()).append("".equals(tournamentInfoTennis.getAway_2_name())?"":("/"+tournamentInfoTennis.getAway_2_name())).append("\"")
			.append(",\"status\":\"").append(tournamentInfoTennis.getStatus()).append("\"")
			.append(",\"matchId\":").append(tournamentInfoTennis.getVid())
			.append(",\"setA\":").append(tournamentInfoTennis.getNewestSetA())
			.append(",\"setB\":").append(tournamentInfoTennis.getNewestSetB())
			.append(",\"tournamentName\":\"").append(tournamentInfoTennis.getTournamentName()).append("\"")
			.append(",\"tournamentDate\":\"").append(tournamentInfoTennis.getTournamentDate()).append("\"}");
		}
		stringBuffer.append("]}");
		return stringBuffer.toString();
	}
	@Override
	public String doQueryMatchInfoToContinueTennis(String matchId) {
		String hqlForTournamentInfoTennis = "from TournamentInfoTennis where vid = ?";
		List<TournamentInfoTennis> tournamentInfoTenniss = baseDao.queryBySql(hqlForTournamentInfoTennis,Integer.parseInt(matchId));
		if ( tournamentInfoTenniss.isEmpty() ) 
			return "{\"success\":false,\"msg\":\"赛事信息有误, 请重新创建比赛!\"}";
		else{
			TournamentInfoTennis tournamentInfoTennis = tournamentInfoTenniss.get(0);
			StringBuffer stringBuffer = new StringBuffer("{\"matchId\":").append(tournamentInfoTennis.getVid()).append(",")
			.append("\"status\":\"").append(tournamentInfoTennis.getStatus()).append("\"");
			if ( "未开始".equals(tournamentInfoTennis.getStatus()) ) {
				stringBuffer.append(",\"matchName\":\"").append(tournamentInfoTennis.getTournamentName()).append("\"")
				.append(",\"matchType\":\"").append(tournamentInfoTennis.getMatchType()).append("\"")
				.append(",\"winType\":\"").append(tournamentInfoTennis.getCompetitionSystemGame()).append("\"")
				.append(",\"setType\":\"").append(tournamentInfoTennis.getCompetitionSystemCouncil()).append("\"")
				.append(",\"gameType\":\"").append(tournamentInfoTennis.getCompetitionSystemSmall()).append("\"")
				.append(",\"nameA1\":\"").append(tournamentInfoTennis.getHome_1_name()).append("\"")
				.append(",\"nameA2\":\"").append(tournamentInfoTennis.getHome_2_name()).append("\"")
				.append(",\"nameB1\":\"").append(tournamentInfoTennis.getAway_1_name()).append("\"")
				.append(",\"nameB2\":\"").append(tournamentInfoTennis.getAway_2_name()).append("\"}");
			}
			else
				stringBuffer.append("}");
			return stringBuffer.toString();
		}
		
	}
	@Override
	public String doDeleteMatchTennis(String matchId) {
		String hqlForTournamentInfoTennis = "from TournamentInfoTennis where vid = ?";
		List<TournamentInfoTennis> tournamentInfoTenniss = baseDao.queryBySql(hqlForTournamentInfoTennis,Integer.parseInt(matchId));
		if ( tournamentInfoTenniss.isEmpty() ) 
			return "{\"success\":false,\"msg\":\"赛事信息有误, 请重新创建比赛!\"}";
		else{
			String hqlForMatchPathTennis = "from MatchPathTennis where tournamentVid = ?";
			List<MatchPathTennis> matchPathTennis = baseDao.queryBySql(hqlForMatchPathTennis, Integer.parseInt(matchId));
			for (int i = 0; i < matchPathTennis.size(); i++) 
				baseDao.delete(matchPathTennis.get(i));
			baseDao.delete(tournamentInfoTenniss.get(0));
			return "{\"success\":true,\"msg\":\"删除成功!\"}";
		}
	}
	@Override
	public String doRestartMatchTennis(String matchId) {
		String hqlForTournamentInfoTennis = "from TournamentInfoTennis where vid = ?";
		List<TournamentInfoTennis> tournamentInfoTenniss = baseDao.queryBySql(hqlForTournamentInfoTennis,Integer.parseInt(matchId));
		if ( tournamentInfoTenniss.isEmpty() ) 
			return "{\"success\":false,\"msg\":\"赛事信息有误, 请重新创建比赛!\"}";
		else{
			TournamentInfoTennis tournamentInfoTennis = tournamentInfoTenniss.get(0);
			tournamentInfoTennis.setNewestSetA(0);
			tournamentInfoTennis.setNewestSetB(0);
			tournamentInfoTennis.setNewestCouncilA(0);
			tournamentInfoTennis.setNewestCouncilB(0);
			tournamentInfoTennis.setNewestSmallA("0");
			tournamentInfoTennis.setNewestSmallB("0");
			tournamentInfoTennis.setNextServe(tournamentInfoTennis.getFirstServe());
			tournamentInfoTennis.setIsOne(1);
			tournamentInfoTennis.setPointType("");
			tournamentInfoTennis.setStartTime(new Date());
			tournamentInfoTennis.setEndTime(null);
			tournamentInfoTennis.setWinner(null);
			tournamentInfoTennis.setWithdraw(null);
			tournamentInfoTennis.setStatus("进行中");
			String hqlForMatchPathTennis = "from MatchPathTennis where tournamentVid = ?";
			List<MatchPathTennis> matchPathTennis = baseDao.queryBySql(hqlForMatchPathTennis, Integer.parseInt(matchId));
			for (int i = 0; i < matchPathTennis.size(); i++) 
				baseDao.delete(matchPathTennis.get(i));
			baseDao.update(tournamentInfoTennis);
			return "{\"success\":true,\"matchId\":\""+matchId+"\",\"msg\":\"比赛重新开始!\"}";
		}
	}
}
