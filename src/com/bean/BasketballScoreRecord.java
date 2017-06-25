package com.bean;

import java.util.Date;

public class BasketballScoreRecord {
	private Integer vid;
	private Integer matchId;
	private String scoreSide;
	private Integer scoreIncreased;
	private Integer oldScoreA;
	private Integer oldScoreB;
	private Integer newScoreA;
	private Integer newScoreB;
	private Integer setNum;
	private Integer minute;
	private Integer second;
	private Integer sideTime;
	private Date createTime;
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	public Integer getMatchId() {
		return matchId;
	}
	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}
	public String getScoreSide() {
		return scoreSide;
	}
	public void setScoreSide(String scoreSide) {
		this.scoreSide = scoreSide;
	}
	public Integer getScoreIncreased() {
		return scoreIncreased;
	}
	public void setScoreIncreased(Integer scoreIncreased) {
		this.scoreIncreased = scoreIncreased;
	}
	public Integer getOldScoreA() {
		return oldScoreA;
	}
	public void setOldScoreA(Integer oldScoreA) {
		this.oldScoreA = oldScoreA;
	}
	public Integer getOldScoreB() {
		return oldScoreB;
	}
	public void setOldScoreB(Integer oldScoreB) {
		this.oldScoreB = oldScoreB;
	}
	public Integer getNewScoreA() {
		return newScoreA;
	}
	public void setNewScoreA(Integer newScoreA) {
		this.newScoreA = newScoreA;
	}
	public Integer getNewScoreB() {
		return newScoreB;
	}
	public void setNewScoreB(Integer newScoreB) {
		this.newScoreB = newScoreB;
	}
	public Integer getSetNum() {
		return setNum;
	}
	public void setSetNum(Integer setNum) {
		this.setNum = setNum;
	}
	public Integer getMinute() {
		return minute;
	}
	public void setMinute(Integer minute) {
		this.minute = minute;
	}
	public Integer getSecond() {
		return second;
	}
	public void setSecond(Integer second) {
		this.second = second;
	}
	public Integer getSideTime() {
		return sideTime;
	}
	public void setSideTime(Integer sideTime) {
		this.sideTime = sideTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
