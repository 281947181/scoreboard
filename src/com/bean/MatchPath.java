package com.bean;

import java.math.BigInteger;

public class MatchPath {
	private BigInteger winner;
	private BigInteger id;
	private BigInteger tournament_id;
	private BigInteger create_time;
	private BigInteger serve_player_id;
	private BigInteger receiver_id;
	private Integer score_set_a;
	private Integer score_set_b;
	private Integer score_council_a;
	private Integer score_council_b;
	private String score_small_a;
	private String score_small_b;
	private Integer bounce_num;
	private Integer path_type;
	private Integer side_change;
	private Integer is_one;
	private Integer is_seven;
 	public Integer getSide_change() {
		return side_change;
	}
	public void setSide_change(Integer side_change) {
		this.side_change = side_change;
	}
	public Integer getIs_one() {
		return is_one;
	}
	public void setIs_one(Integer is_one) {
		this.is_one = is_one;
	}
	public Integer getIs_seven() {
		return is_seven;
	}
	public void setIs_seven(Integer is_seven) {
		this.is_seven = is_seven;
	}
	public String getScore_small_a() {
		return score_small_a;
	}
	public void setScore_small_a(String score_small_a) {
		this.score_small_a = score_small_a;
	}
	public String getScore_small_b() {
		return score_small_b;
	}
	public void setScore_small_b(String score_small_b) {
		this.score_small_b = score_small_b;
	}
	public BigInteger getWinner() {
		return winner;
	}
	public void setWinner(BigInteger winner) {
		this.winner = winner;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public BigInteger getTournament_id() {
		return tournament_id;
	}
	public void setTournament_id(BigInteger tournament_id) {
		this.tournament_id = tournament_id;
	}
	public BigInteger getCreate_time() {
		return create_time;
	}
	public void setCreate_time(BigInteger create_time) {
		this.create_time = create_time;
	}
	public BigInteger getServe_player_id() {
		return serve_player_id;
	}
	public void setServe_player_id(BigInteger serve_player_id) {
		this.serve_player_id = serve_player_id;
	}
	public BigInteger getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(BigInteger receiver_id) {
		this.receiver_id = receiver_id;
	}
	public Integer getScore_set_a() {
		return score_set_a;
	}
	public void setScore_set_a(Integer score_set_a) {
		this.score_set_a = score_set_a;
	}
	public Integer getScore_set_b() {
		return score_set_b;
	}
	public void setScore_set_b(Integer score_set_b) {
		this.score_set_b = score_set_b;
	}
	public Integer getScore_council_a() {
		return score_council_a;
	}
	public void setScore_council_a(Integer score_council_a) {
		this.score_council_a = score_council_a;
	}
	public Integer getScore_council_b() {
		return score_council_b;
	}
	public void setScore_council_b(Integer score_council_b) {
		this.score_council_b = score_council_b;
	}
	public Integer getBounce_num() {
		return bounce_num;
	}
	public void setBounce_num(Integer bounce_num) {
		this.bounce_num = bounce_num;
	}
	public Integer getPath_type() {
		return path_type;
	}
	public void setPath_type(Integer path_type) {
		this.path_type = path_type;
	}
}
