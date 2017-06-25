package com.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.WoLaiSaiService;

public class WoLaiSaiAction extends ActionSupport {
	private static final long serialVersionUID = 5378351611708311668L;
	private WoLaiSaiService woLaiSaiService;
	public WoLaiSaiService getWoLaiSaiService() {
		return woLaiSaiService;
	}
	public void setWoLaiSaiService(WoLaiSaiService woLaiSaiService) {
		this.woLaiSaiService = woLaiSaiService;
	}
	
	public String startMatch(){
		ActionContext actionContext = ActionContext.getContext();
		String matchId = ((String[])actionContext.getParameters().get("matchId"))[0];
		@SuppressWarnings("unchecked")
		Map<String,String> request = (Map<String,String>) actionContext.get("request"); 
		request.put("matchId", matchId);
		return SUCCESS;
	}
}
