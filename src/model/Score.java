/*
 * File: Score.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Melinda
 * Date: November 27, 2026
 * Description: Stores and compares a teams score for leaderboard ranking.
 */

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