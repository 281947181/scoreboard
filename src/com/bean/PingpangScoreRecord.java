package com.bean;

import java.util.Date;

public class PingpangScoreRecord {
	private Integer vid;
	private Integer matchId;
	private String scoreSide;
	private String oldServeSide;
	private Integer oldScoreA;
	private Integer oldScoreB;
	private Integer newScoreA;
	private Integer newScoreB;
	private Integer setNum;
	private Integer setA;
	private Integer setB;
	private Integer oldTotalServeNum;
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
	public Integer getSetA() {
		return setA;
	}
	public void setSetA(Integer setA) {
		this.setA = setA;
	}
	public Integer getSetB() {
		return setB;
	}
	public void setSetB(Integer setB) {
		this.setB = setB;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOldServeSide() {
		return oldServeSide;
	}
	public void setOldServeSide(String oldServeSide) {
		this.oldServeSide = oldServeSide;
	}
	public Integer getOldTotalServeNum() {
		return oldTotalServeNum;
	}
	public void setOldTotalServeNum(Integer oldTotalServeNum) {
		this.oldTotalServeNum = oldTotalServeNum;
	}
}
