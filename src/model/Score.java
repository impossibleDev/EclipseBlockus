package model;

import shared.Team;

// melinda
public class Score {
	private Team team;
	private int score;

	public Score(Team team, int score) {
		this.team = team;
		this.score = score;
	}

	public Team getTeam() {
		return team;
	}

	public int getScore() {
		return score;
	}
}