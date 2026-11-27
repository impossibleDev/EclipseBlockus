// Description: Stores and for future comparison for leaderboard ranking.
// Contributors: Melinda

package model;

import shared.Team;

// melinda
public class Score {
	// Fields
	private Team team;
	private int score;

	//Constructor
	public Score(Team team, int score) {
		this.team = team;
		this.score = score;
	}

	// Getters
	public Team getTeam() {
		return team;
	}

	public int getScore() {
		return score;
	}
}