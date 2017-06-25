package com.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.StatisticsService;
import com.utils.JsonUtils;

public class StatisticsAction extends ActionSupport {
	private static final long serialVersionUID = 9202832622462067442L;
	private StatisticsService statisticsService;
	public StatisticsService getStatisticsService() {
		return statisticsService;
	}
	public void setStatisticsService(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}
	public String chooseMatchType(){
		ActionContext actionContext = ActionContext.getContext();
		String matchType = ((String[])actionContext.getParameters().get("matchType"))[0];
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		String council = actionContext.getParameters().get("council")==null?"":((String[])actionContext.getParameters().get("council"))[0];
		String set = actionContext.getParameters().get("set")==null?"":((String[])actionContext.getParameters().get("set"))[0];
		String result = null;
		switch( matchType ){
			case "tennis":
				result = "tennis";
				@SuppressWarnings("unchecked")
				Map<String,String> request = (Map<String,String>) actionContext.get("request"); 
				request.put("matchType", matchType);
				request.put("matchId", matchId);
				request.put("council", council);
				request.put("set", set);
				break;
			default:
				result = "wrongType";
				break;
		}
		return result;
	}
	public void tennis(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		String council = actionContext.getParameters().get("council")==null?"":((String[])actionContext.getParameters().get("council"))[0];
		String set = actionContext.getParameters().get("set")==null?"":((String[])actionContext.getParameters().get("set"))[0];
		new JsonUtils().writeJson(statisticsService.doTennis(matchId,council,set));
	}
}
