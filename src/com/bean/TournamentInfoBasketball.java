package com.bean;

import java.util.Date;

public class TournamentInfoBasketball {
	private Integer vid;
	private Date createTime;
	private Integer setNum;
	private Integer singleSetTime;
	private String nameA;
	private String nameB;
	private Integer currentScoreA;
	private Integer currentScoreB;
	private Integer currentSetNum;
	private Integer currentRestTimeMinute;
	private Integer currentRestTimeSecond;
	private Integer currentRestSideTime;
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
	public Integer getSingleSetTime() {
		return singleSetTime;
	}
	public void setSingleSetTime(Integer singleSetTime) {
		this.singleSetTime = singleSetTime;
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
	public Integer getCurrentRestTimeMinute() {
		return currentRestTimeMinute;
	}
	public void setCurrentRestTimeMinute(Integer currentRestTimeMinute) {
		this.currentRestTimeMinute = currentRestTimeMinute;
	}
	public Integer getCurrentRestTimeSecond() {
		return currentRestTimeSecond;
	}
	public void setCurrentRestTimeSecond(Integer currentRestTimeSecond) {
		this.currentRestTimeSecond = currentRestTimeSecond;
	}
	public Integer getCurrentRestSideTime() {
		return currentRestSideTime;
	}
	public void setCurrentRestSideTime(Integer currentRestSideTime) {
		this.currentRestSideTime = currentRestSideTime;
	}
}
