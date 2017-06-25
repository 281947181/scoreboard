package com.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

public class JsonUtils {
	public String writeJson(String jsonString){
		HttpServletResponse response = ServletActionContext.getResponse();
		String result = null;
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(jsonString);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
