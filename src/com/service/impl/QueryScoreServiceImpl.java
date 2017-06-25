package com.service.impl;

import java.util.List;

import com.bean.TournamentInfoBasketball;
import com.bean.TournamentInfoPingpang;
import com.bean.TournamentInfoTennis;
import com.dao.BaseDao;
import com.service.QueryScoreService;

public class QueryScoreServiceImpl implements QueryScoreService {
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public String doTennis(String matchId) {
		String hqlForTournamentInfoTennis = "from TournamentInfoTennis where id = ?";
		List<TournamentInfoTennis> tournamentInfos = baseDao.queryBySql(hqlForTournamentInfoTennis, new Integer(matchId));
		if ( tournamentInfos.isEmpty() ) 
			return "{\"success\":true,\"nameA\":\"\",\"nameB\":\"\",\"councilA\":0,\"councilB\":0,\"setA\":0,\"setB\":0,\"smallA\":0,\"smallB\":0}";
		else{
			TournamentInfoTennis tournamentInfo = tournamentInfos.get(0);
			String nameA = tournamentInfo.getHome_1_name() + ("".equals(tournamentInfo.getHome_2_name())?"":("/"+tournamentInfo.getHome_2_name()));
			String nameB = tournamentInfo.getAway_1_name() + ("".equals(tournamentInfo.getAway_2_name())?"":("/"+tournamentInfo.getAway_2_name()));
			String nextServe = null, noNextServe = null;
			if ( tournamentInfo.getNextServe() == null ){
				nextServe = "NO";
				noNextServe = "NO";
			}
			else {
				nextServe = tournamentInfo.getNextServe();
				noNextServe = "A".equals(nextServe)?"B":"A";
			}
			StringBuffer stringBuffer = new StringBuffer("{\"success\":true,")
					.append("\"nameA\":\"").append(nameA).append("\",")
					.append("\"nameB\":\"").append(nameB).append("\",")
					.append("\"councilA\":\"").append(tournamentInfo.getNewestCouncilA()).append("\",")
					.append("\"councilB\":\"").append(tournamentInfo.getNewestCouncilB()).append("\",")
					.append("\"setA\":\"").append(tournamentInfo.getNewestSetA()).append("\",")
					.append("\"setB\":\"").append(tournamentInfo.getNewestSetB()).append("\",")
					.append("\"smallA\":\"").append(tournamentInfo.getNewestSmallA()).append("\",")
					.append("\"smallB\":\"").append(tournamentInfo.getNewestSmallB()).append("\",")
					.append("\"nextServe\":\"").append(nextServe).append("\",")
					.append("\"noNextServe\":\"").append(noNextServe).append("\",")
					.append("\"status\":\"").append(tournamentInfo.getStatus()).append("\",")
					.append("\"point\":\"").append(tournamentInfo.getPointType()).append("\"}");
			return stringBuffer.toString();
		}
	}
	@Override
	public String doBasketball(String matchId) {
		String hqlForTournamentInfoBasketball = "from TournamentInfoBasketball where vid = ?";
		List<TournamentInfoBasketball> tournamentInfoBasketballs = baseDao.queryBySql(hqlForTournamentInfoBasketball,Integer.parseInt(matchId));
		if (tournamentInfoBasketballs.isEmpty()) {
			return "{\"success\":true,\"nameA\":\"\",\"nameB\":\"\",\"scoreA\":0,\"scoreB\":0,\"setNum\":0,\"totalMinute\":0,\"totalSecond\":0,\"sideTime\":0}";
		}
		else{
			TournamentInfoBasketball tournamentInfoBasketball = tournamentInfoBasketballs.get(0);
			StringBuffer stringBuffer = new StringBuffer("{\"success\":true,")
					.append("\"nameA\":\"").append(tournamentInfoBasketball.getNameA()).append("\",")
					.append("\"nameB\":\"").append(tournamentInfoBasketball.getNameB()).append("\",")
					.append("\"scoreA\":").append(tournamentInfoBasketball.getCurrentScoreA()).append(",")
					.append("\"scoreB\":").append(tournamentInfoBasketball.getCurrentScoreB()).append(",")
					.append("\"totalMinute\":").append(tournamentInfoBasketball.getCurrentRestTimeMinute()).append(",")
					.append("\"totalSecond\":").append(tournamentInfoBasketball.getCurrentRestTimeSecond()).append(",")
					.append("\"sideTime\":").append(tournamentInfoBasketball.getCurrentRestSideTime()).append(",");
//					.append("}");
			if ( tournamentInfoBasketball.getCurrentRestTimeMinute() == 0 && tournamentInfoBasketball.getCurrentRestTimeSecond() == 0 &&tournamentInfoBasketball.getCurrentSetNum().equals(tournamentInfoBasketball.getSetNum()) ) 
				stringBuffer.append("\"setNum\":\"比赛结束\",\"status\":\"stop\"}");
			else if ( tournamentInfoBasketball.getCurrentRestTimeMinute() == 0 && tournamentInfoBasketball.getCurrentRestTimeSecond() == 0 ) 
				stringBuffer.append("\"setNum\":").append(tournamentInfoBasketball.getCurrentSetNum()).append(",")
				.append("\"status\":\"pause\"}");
			else
				stringBuffer.append("\"setNum\":").append(tournamentInfoBasketball.getCurrentSetNum()).append(",")
				.append("\"status\":\"start\"}");
			return stringBuffer.toString();
		}
	}
	@Override
	public String doPingpang(String matchId) {
		String hqlForTournamentInfoPingpang = "from TournamentInfoPingpang where vid = ?";
		List<TournamentInfoPingpang> tournamentInfoPingpangs = baseDao.queryBySql(hqlForTournamentInfoPingpang, Integer.parseInt(matchId));
		if ( tournamentInfoPingpangs.isEmpty() ) {
			return "{\"success\":false}";
		}
		else {
			TournamentInfoPingpang tournamentInfoPingpang = tournamentInfoPingpangs.get(0);
			StringBuffer stringBuffer = new StringBuffer("{\"success\":true,")
				.append("\"nameA\":\"").append(tournamentInfoPingpang.getNameA()).append("\",")
				.append("\"nameB\":\"").append(tournamentInfoPingpang.getNameB()).append("\",")
				.append("\"scoreA\":").append(tournamentInfoPingpang.getCurrentScoreA()).append(",")
				.append("\"scoreB\":").append(tournamentInfoPingpang.getCurrentScoreB()).append(",")
				.append("\"setA\":").append(tournamentInfoPingpang.getCurrentSetA()).append(",")
				.append("\"setB\":").append(tournamentInfoPingpang.getCurrentSetB()).append(",")
				.append("\"matchEnd\":").append(tournamentInfoPingpang.getCurrentSetA()==(tournamentInfoPingpang.getSetNum()/2+1) || tournamentInfoPingpang.getCurrentSetB()==(tournamentInfoPingpang.getSetNum()/2+1)).append(",")
				.append("\"nextServe\":\"").append(tournamentInfoPingpang.getCurrentServe()).append("\",")
				.append("\"notServe\":\"").append("A".equals(tournamentInfoPingpang.getCurrentServe())?"B":"A").append("\"}");
			
			return stringBuffer.toString();
		}
	}
}
