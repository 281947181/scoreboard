package com.bean;

import java.util.Date;

public class TournamentInfoPingpang {
	private Integer vid;
	private Date createTime;
	private Integer setNum;
	private Integer singleSetPoint;
	private String nameA;
	private String nameB;
	private Integer currentScoreA;
	private Integer currentScoreB;
	private Integer currentSetNum;
	private Integer currentSetA;
	private Integer currentSetB;
	private String firstServe;
	private String currentServe;
	private Integer totalServeNum;
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getSetNum() {
		return setNum;
	}
	public void setSetNum(Integer setNum) {
		this.setNum = setNum;
	}
	public Integer getSingleSetPoint() {
		return singleSetPoint;
	}
	public void setSingleSetPoint(Integer singleSetPoint) {
		this.singleSetPoint = singleSetPoint;
	}
	public String getNameA() {
		return nameA;
	}
	public void setNameA(String nameA) {
		this.nameA = nameA;
	}
	public String getNameB() {
		return nameB;
	}
	public void setNameB(String nameB) {
		this.nameB = nameB;
	}
	public Integer getCurrentScoreA() {
		return currentScoreA;
	}
	public void setCurrentScoreA(Integer currentScoreA) {
		this.currentScoreA = currentScoreA;
	}
	public Integer getCurrentScoreB() {
		return currentScoreB;
	}
	public void setCurrentScoreB(Integer currentScoreB) {
		this.currentScoreB = currentScoreB;
	}
	public Integer getCurrentSetNum() {
		return currentSetNum;
	}
	public void setCurrentSetNum(Integer currentSetNum) {
		this.currentSetNum = currentSetNum;
	}
	public Integer getCurrentSetA() {
		return currentSetA;
	}
	public void setCurrentSetA(Integer currentSetA) {
		this.currentSetA = currentSetA;
	}
	public Integer getCurrentSetB() {
		return currentSetB;
	}
	public void setCurrentSetB(Integer currentSetB) {
		this.currentSetB = currentSetB;
	}
	public String getFirstServe() {
		return firstServe;
	}
	public void setFirstServe(String firstServe) {
		this.firstServe = firstServe;
	}
	public String getCurrentServe() {
		return currentServe;
	}
	public void setCurrentServe(String currentServe) {
		this.currentServe = currentServe;
	}
	public Integer getTotalServeNum() {
		return totalServeNum;
	}
	public void setTotalServeNum(Integer totalServeNum) {
		this.totalServeNum = totalServeNum;
	}
}
