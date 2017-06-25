package com.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.opensymphony.xwork2.ActionSupport;
import com.utils.JsonUtils;

public class TestAction extends ActionSupport {
	private static final long serialVersionUID = -7178554731510658820L;
	public void delayTest(){
		new JsonUtils().writeJson("{\"date\":\""+new SimpleDateFormat("mm:ss.SSS").format(new Date())+"\"}");
	}
}
