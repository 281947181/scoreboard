package com.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.bean.MatchPath;
import com.bean.MatchPathTennis;
import com.bean.TournamentInfoTennis;
import com.dao.BaseDao;
import com.service.StatisticsService;

public class StatisticsServiceImpl implements StatisticsService {
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	private List<String> getJinQiuLv(List<MatchPathTennis> matchPaths, List<MatchPath> matchPathsScore, boolean is_one){
		List<String> result = new ArrayList<String>();
		int size = matchPaths.size(), fault = 0;
		for (int i = 0; i < size; i++) {
			if ( is_one && matchPaths.get(i).getPathType() == 12 ) {
				fault++;
			}
			else if ( !is_one && matchPaths.get(i).getPathType() == 4 ) {
				fault++;
			}
		}
		if ( size > 0 ) {
			result.add(new DecimalFormat("##%").format(
					new BigDecimal(size+".00").subtract(new BigDecimal(fault+".00"))
					.divide(new BigDecimal(size+".00"),BigDecimal.ROUND_HALF_UP))
					+"("+(size-fault)+"/"+size+")"
			);
			result.add(new DecimalFormat("##%").format(
					new BigDecimal(matchPathsScore.size()+".00")
//					.divide(new BigDecimal(size+".00").subtract(new BigDecimal(fault+".00")),BigDecimal.ROUND_HALF_UP))
					.divide(new BigDecimal(size+".00"),BigDecimal.ROUND_HALF_UP))
//					+"("+matchPathsScore.size()+"/"+(size-fault)+")"
					+"("+matchPathsScore.size()+"/"+(size)+")"
			);
		}
		else if ( size == 0 || size == fault ) {
			result.add("0(0/0)");
			result.add("0(0/0)");
		}
			
		return result;
	}
	@Override
	public String doTennis(String matchId, String council, String set) {
		String hqlForTournamentInfoTennis = "from TournamentInfoTennis where vid = ?";
		List<TournamentInfoTennis> tournamentInfos = baseDao.queryBySql(hqlForTournamentInfoTennis, new Integer(matchId));
		if ( tournamentInfos.isEmpty() ) {
			return "{\"success\":true,\"nameA\":\"\",\"nameB\":\"\",\"listA\":[],\"listB\":[]}";
		}
		TournamentInfoTennis tournamentInfo = tournamentInfos.get(0);
		String nameA = tournamentInfo.getHome_1_name() + ("".equals(tournamentInfo.getHome_2_name())?"":("/"+tournamentInfo.getHome_2_name()));
		String nameB = tournamentInfo.getAway_1_name() + ("".equals(tournamentInfo.getAway_2_name())?"":("/"+tournamentInfo.getAway_2_name()));
		String title = "".equals(tournamentInfo.getTournamentName())?"":tournamentInfo.getTournamentName();
//		BigInteger idA1 = tournamentInfos.get(0).getHome_1_id();
//		BigInteger idA2 = tournamentInfos.get(0).getHome_2_id();
//		BigInteger idB1 = tournamentInfos.get(0).getAway_1_id();
//		BigInteger idB2 = tournamentInfos.get(0).getAway_2_id();
		int[] listA = {0,0,0,0,0,0};
		int[] listB = {0,0,0,0,0,0};
		List<String> jinQiuLvA = null;
		List<String> jinQiuLvB = null;
		List<String> erJinQiuLvA = null;
		List<String> erJinQiuLvB = null;
//		if ( set.isEmpty() ) {
			//盘分为空，默认为全部
			//ACE球
			String hqlForMatchPathTennis = "from MatchPathTennis where tournamentVid = ? and ( pathType = '0' or pathType = '1' ) order by vid asc";
			List<MatchPathTennis> matchPaths = baseDao.queryBySql(hqlForMatchPathTennis, new Integer(matchId));
			for (int i = 0; i < matchPaths.size(); i++) {
				if ( "A".equals(matchPaths.get(i).getServeSide()) ) 
					listA[0]++;
				else
					listB[0]++;
			}
			//双误
			hqlForMatchPathTennis = "from MatchPathTennis where tournamentVid = ? and ( pathType = '4' ) order by vid asc";
			matchPaths = baseDao.queryBySql(hqlForMatchPathTennis, new Integer(matchId));
			for (int i = 0; i < matchPaths.size(); i++) {
				if ( "A".equals(matchPaths.get(i).getServeSide()) ) 
					listA[1]++;
				else
					listB[1]++;
			}
			//发球直接得分
			hqlForMatchPathTennis = "from MatchPathTennis where tournamentVid = ? and ( pathType = '2' or pathType = '3' ) order by vid asc";
			matchPaths = baseDao.queryBySql(hqlForMatchPathTennis, new Integer(matchId));
			for (int i = 0; i < matchPaths.size(); i++) {
				if ( "A".equals(matchPaths.get(i).getServeSide()) ) 
					listA[2]++;
				else
					listB[2]++;
			}
			//进攻得分
			hqlForMatchPathTennis = "from MatchPathTennis where tournamentVid = ? and ( pathType = '8' or pathType = '9' ) order by vid asc";
			matchPaths = baseDao.queryBySql(hqlForMatchPathTennis, new Integer(matchId));
			for (int i = 0; i < matchPaths.size(); i++) {
				if ( "A".equals(matchPaths.get(i).getScoreSide()) ) 
					listA[3]++;
				else
					listB[3]++;
			}
			//受迫失误
			hqlForMatchPathTennis = "from MatchPathTennis where tournamentVid = ? and ( pathType = '11' ) order by vid asc";
			matchPaths = baseDao.queryBySql(hqlForMatchPathTennis, new Integer(matchId));
			for (int i = 0; i < matchPaths.size(); i++) {
				if ( "A".equals(matchPaths.get(i).getScoreSide()) ) 
					listB[4]++;
				else
					listA[4]++;
			}
			//非受迫失误
			hqlForMatchPathTennis = "from MatchPathTennis where tournamentVid = ? and ( pathType = '10' ) order by vid asc";
			matchPaths = baseDao.queryBySql(hqlForMatchPathTennis, new Integer(matchId));
			for (int i = 0; i < matchPaths.size(); i++) {
				if ( "A".equals(matchPaths.get(i).getScoreSide()) ) 
					listB[5]++;
				else
					listA[5]++;
			}
			//一发
			hqlForMatchPathTennis = "from MatchPathTennis where tournamentVid = ? and serveSide = ? and isOne = '1' and pathType != '20'";
			String hqlForMatchPathTennis2 = "from MatchPathTennis where tournamentVid = ? and serveSide = ? and isOne = '1' and scoreSide = ? and ( pathType = '0' or pathType = '2' or pathType = '8' or pathType = '10' or pathType = '11')";
			//A一发总数
			matchPaths = baseDao.queryBySql(hqlForMatchPathTennis, new Object[]{new Integer(matchId),"A"});
			//A一发得分数
			List<MatchPath> matchPathsScore = baseDao.queryBySql(hqlForMatchPathTennis2, new Object[]{new Integer(matchId),"A","A"});
			//计算A一发进球率和得分率
			jinQiuLvA = getJinQiuLv(matchPaths, matchPathsScore, true);
			//B一发总数
			matchPaths = baseDao.queryBySql(hqlForMatchPathTennis, new Object[]{new Integer(matchId),"B"});
			//B一发得分数
			matchPathsScore = baseDao.queryBySql(hqlForMatchPathTennis2,  new Object[]{new Integer(matchId),"B","B"});
			//计算B一发进球率和得分率
			jinQiuLvB = getJinQiuLv(matchPaths, matchPathsScore, true);
			
			//二发
			hqlForMatchPathTennis = "from MatchPathTennis where tournamentVid = ? and serveSide = ? and isOne = '0' and pathType != '20'";
			hqlForMatchPathTennis2 = "from MatchPathTennis where tournamentVid = ? and serveSide = ? and isOne = '0' and scoreSide = ? and ( pathType = '1' or pathType = '3' or pathType = '9' or pathType = '10' or pathType = '11')";
			matchPaths = baseDao.queryBySql(hqlForMatchPathTennis, new Object[]{new Integer(matchId),"A"});
			matchPathsScore = baseDao.queryBySql(hqlForMatchPathTennis2,  new Object[]{new Integer(matchId),"A","A"});
			erJinQiuLvA = getJinQiuLv(matchPaths, matchPathsScore, false);
			matchPaths = baseDao.queryBySql(hqlForMatchPathTennis, new Object[]{new Integer(matchId),"B"});
			matchPathsScore = baseDao.queryBySql(hqlForMatchPathTennis2,  new Object[]{new Integer(matchId),"B","B"});
			erJinQiuLvB = getJinQiuLv(matchPaths, matchPathsScore, false);
			
//		}
//		else{
//			//盘分不为空，分两种情况，1.局分为空，2.局分不为空
//			if ( council.isEmpty() ) {
//				//局分为空，则统计单盘总情况
//				String hqlForMatchPath = "from MatchPath where tournament_id = ? and (winner = ? or winner = ?) and score_set_a + score_set_b = ? order by id asc";
//				List<MatchPath> matchPaths = baseDao.queryBySql(hqlForMatchPath, new Object[]{new BigInteger(matchId),idA1,idA2,Integer.parseInt(set)-1});
//				getListAandB(matchPaths,listA,listB,"A");
//				matchPaths = baseDao.queryBySql(hqlForMatchPath, new Object[]{new BigInteger(matchId),idB1,idB2,Integer.parseInt(set)-1});
//				getListAandB(matchPaths,listA,listB,"B");
//				
//				//一发
//				hqlForMatchPath = "from MatchPath where tournament_id = ? and ( serve_player_id = ? or serve_player_id = ? ) and score_set_a + score_set_b = ? and is_one = '1'";
//				String hqlForMatchPath2 = "from MatchPath where tournament_id = ? and ( serve_player_id = ? or serve_player_id = ? ) and ( winner = ? or winner = ? ) and is_one = '1' and score_set_a + score_set_b = ? and ( path_type = '0' or path_type = '2' or path_type = '8' or path_type = '10' or path_type = '11')";
//				//A一发总数
//				matchPaths = baseDao.queryBySql(hqlForMatchPath, new Object[]{new BigInteger(matchId),idA1,idA2,Integer.parseInt(set)-1});
//				//A一发得分数
//				List<MatchPath> matchPathsScore = baseDao.queryBySql(hqlForMatchPath2,  new Object[]{new BigInteger(matchId),idA1,idA2,idA1,idA2,Integer.parseInt(set)-1});
//				//计算A一发进球率和得分率
//				jinQiuLvA = getJinQiuLv(matchPaths, matchPathsScore, true);
//				//B一发总数
//				matchPaths = baseDao.queryBySql(hqlForMatchPath, new Object[]{new BigInteger(matchId),idB1,idB2,Integer.parseInt(set)-1});
//				//B一发得分数
//				matchPathsScore = baseDao.queryBySql(hqlForMatchPath2,  new Object[]{new BigInteger(matchId),idB1,idB2,idB1,idB2,Integer.parseInt(set)-1});
//				//计算B一发进球率和得分率
//				jinQiuLvB = getJinQiuLv(matchPaths, matchPathsScore, true);
//				
//				//二发
//				hqlForMatchPath = "from MatchPath where tournament_id = ? and ( serve_player_id = ? or serve_player_id = ? ) and score_set_a + score_set_b = ? and is_one = '0'";
//				hqlForMatchPath2 = "from MatchPath where tournament_id = ? and ( serve_player_id = ? or serve_player_id = ? ) and ( winner = ? or winner = ? ) and is_one = '0' and score_set_a + score_set_b = ? and ( path_type = '1' or path_type = '3' or path_type = '9' or path_type = '10' or path_type = '11')";
//				matchPaths = baseDao.queryBySql(hqlForMatchPath, new Object[]{new BigInteger(matchId),idA1,idA2,Integer.parseInt(set)-1});
//				matchPathsScore = baseDao.queryBySql(hqlForMatchPath2,  new Object[]{new BigInteger(matchId),idA1,idA2,idA1,idA2,Integer.parseInt(set)-1});
//				erJinQiuLvA = getJinQiuLv(matchPaths, matchPathsScore, false);
//				matchPaths = baseDao.queryBySql(hqlForMatchPath, new Object[]{new BigInteger(matchId),idB1,idB2,Integer.parseInt(set)-1});
//				matchPathsScore = baseDao.queryBySql(hqlForMatchPath2,  new Object[]{new BigInteger(matchId),idB1,idB2,idB1,idB2,Integer.parseInt(set)-1});
//				erJinQiuLvB = getJinQiuLv(matchPaths, matchPathsScore, false);
//			}
//			else{
//				//局分不为空，则统计单盘，单局情况
//				String hqlForMatchPath = "from MatchPath where tournament_id = ? and (winner = ? or winner = ?) and score_set_a + score_set_b = ? and score_council_a + score_council_b = ? order by id asc";
//				List<MatchPath> matchPaths = baseDao.queryBySql(hqlForMatchPath, new Object[]{new BigInteger(matchId),idA1,idA2,Integer.parseInt(set)-1,Integer.parseInt(council)-1});
//				getListAandB(matchPaths,listA,listB,"A");
//				matchPaths  = baseDao.queryBySql(hqlForMatchPath, new Object[]{new BigInteger(matchId),idB1,idB2,Integer.parseInt(set)-1,Integer.parseInt(council)-1});
//				getListAandB(matchPaths,listA,listB,"B");
//				
//				//一发
//				hqlForMatchPath = "from MatchPath where tournament_id = ? and ( serve_player_id = ? or serve_player_id = ? ) and score_set_a + score_set_b = ? and score_council_a + score_council_b = ? and is_one = '1'";
//				String hqlForMatchPath2 = "from MatchPath where tournament_id = ? and ( serve_player_id = ? or serve_player_id = ? ) and ( winner = ? or winner = ? ) and score_set_a + score_set_b = ? and is_one = '1' and score_council_a + score_council_b = ? and ( path_type = '0' or path_type = '2' or path_type = '8' or path_type = '10' or path_type = '11')";
//				//A一发总数
//				matchPaths = baseDao.queryBySql(hqlForMatchPath, new Object[]{new BigInteger(matchId),idA1,idA2,Integer.parseInt(set)-1,Integer.parseInt(council)-1});
//				//A一发得分数
//				List<MatchPath> matchPathsScore = baseDao.queryBySql(hqlForMatchPath2,  new Object[]{new BigInteger(matchId),idA1,idA2,idA1,idA2,Integer.parseInt(set)-1,Integer.parseInt(council)-1});
//				//计算A一发进球率和得分率
//				jinQiuLvA = getJinQiuLv(matchPaths, matchPathsScore, true);
//				//B一发总数
//				matchPaths = baseDao.queryBySql(hqlForMatchPath, new Object[]{new BigInteger(matchId),idB1,idB2,Integer.parseInt(set)-1,Integer.parseInt(council)-1});
//				//B一发得分数
//				matchPathsScore = baseDao.queryBySql(hqlForMatchPath2,  new Object[]{new BigInteger(matchId),idB1,idB2,idB1,idB2,Integer.parseInt(set)-1,Integer.parseInt(council)-1});
//				//计算B一发进球率和得分率
//				jinQiuLvB = getJinQiuLv(matchPaths, matchPathsScore, true);
//				
//				//二发
//				hqlForMatchPath = "from MatchPath where tournament_id = ? and ( serve_player_id = ? or serve_player_id = ? ) and score_set_a + score_set_b = ? and score_council_a + score_council_b = ? and is_one = '0'";
//				hqlForMatchPath2 = "from MatchPath where tournament_id = ? and ( serve_player_id = ? or serve_player_id = ? ) and ( winner = ? or winner = ? ) and score_set_a + score_set_b = ? and is_one = '0' and score_council_a + score_council_b = ? and ( path_type = '1' or path_type = '3' or path_type = '9' or path_type = '10' or path_type = '11')";
//				matchPaths = baseDao.queryBySql(hqlForMatchPath, new Object[]{new BigInteger(matchId),idA1,idA2,Integer.parseInt(set)-1,Integer.parseInt(council)-1});
//				matchPathsScore = baseDao.queryBySql(hqlForMatchPath2,  new Object[]{new BigInteger(matchId),idA1,idA2,idA1,idA2,Integer.parseInt(set)-1,Integer.parseInt(council)-1});
//				erJinQiuLvA = getJinQiuLv(matchPaths, matchPathsScore, false);
//				matchPaths = baseDao.queryBySql(hqlForMatchPath, new Object[]{new BigInteger(matchId),idB1,idB2,Integer.parseInt(set)-1,Integer.parseInt(council)-1});
//				matchPathsScore = baseDao.queryBySql(hqlForMatchPath2,  new Object[]{new BigInteger(matchId),idB1,idB2,idB1,idB2,Integer.parseInt(set)-1,Integer.parseInt(council)-1});
//				erJinQiuLvB = getJinQiuLv(matchPaths, matchPathsScore, false);
//			}
//		}
		StringBuffer stringBuffer = new StringBuffer("{\"success\":true,\"nameA\":\"").append(nameA).append("\",\"nameB\":\"").append(nameB).append("\",\"title\":\"").append(title).append("\"");
		stringBuffer.append(",\"listA\":[");
		for (int i = 0; i < listA.length; i++) {
			stringBuffer.append(i==0?"":",")
			.append("\"").append(listA[i]).append("\"");
		}
		stringBuffer.append(",\"").append(jinQiuLvA.get(0))
		.append("\",\"").append(jinQiuLvA.get(1)).append("\"")
		.append(",\"").append(erJinQiuLvA.get(0))
		.append("\",\"").append(erJinQiuLvA.get(1)).append("\"")
		.append("],\"listB\":[");
		for (int i = 0; i < listB.length; i++) {
			stringBuffer.append(i==0?"":",")
			.append("\"").append(listB[i]).append("\"");
		}
		stringBuffer.append(",\"").append(jinQiuLvB.get(0))
		.append("\",\"").append(jinQiuLvB.get(1)).append("\"")
		.append(",\"").append(erJinQiuLvB.get(0))
		.append("\",\"").append(erJinQiuLvB.get(1)).append("\"")
		.append("]}");
		return stringBuffer.toString();
	}
}
