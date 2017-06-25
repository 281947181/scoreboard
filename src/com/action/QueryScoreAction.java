package com.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.QueryScoreService;
import com.utils.JsonUtils;

public class QueryScoreAction extends ActionSupport {
	private static final long serialVersionUID = 3697922400386039393L;
	private QueryScoreService queryScoreService;
	public QueryScoreService getQueryScoreService() {
		return queryScoreService;
	}
	public void setQueryScoreService(QueryScoreService queryScoreService) {
		this.queryScoreService = queryScoreService;
	}
	public String chooseMatchType(){
		ActionContext actionContext = ActionContext.getContext();
		String matchType = ((String[])actionContext.getParameters().get("matchType"))[0];
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		String result = null;
		@SuppressWarnings("unchecked")
		Map<String,String> request = (Map<String,String>) actionContext.get("request"); 
		request.put("matchId", matchId);
		switch( matchType ){
			case "tennis":
				result = "tennis";
				request.put("matchType", "tennis");
				break;
			case "basketball":
				result = "basketball";
				request.put("matchType", "basketball");
				break;
			case "pingpang":
				result = "pingpang";
				request.put("matchType", "pingpang");
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
		new JsonUtils().writeJson(queryScoreService.doTennis(matchId));
	}
	public void basketball(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		new JsonUtils().writeJson(queryScoreService.doBasketball(matchId));
	}
	public void pingpang(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		new JsonUtils().writeJson(queryScoreService.doPingpang(matchId));
	}
	
}
