/*
 * File: LeaderboardOverlay.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Melinda, Aiden
 * Date: November 27, 2026
 * Description: Displays sorted player scores and ranks at the end of a game.
 */

package view.overlay;

import java.awt.Color;
import javax.swing.*;
import model.Score;
import shared.Team;
import view.GuiUtil;

//Melinda
public class LeaderboardOverlay extends Overlay {
	
	//Constants - Visual positions from left to right: [2nd Place, 1st Place, 3rd Place, 4th Place]
	private static final int[] labelX = {85, 205, 320, 400};
	private static final int teamLabelY = 357;
	private static final int scoreLabelY = 382;
	
	//imageIcon resources
	private static final ImageIcon frameImg = new ImageIcon("resources/ui/LeaderboardOverlayFrame.png");
	private static final ImageIcon newGameButtonImg = new ImageIcon("resources/ui/LeaderboardOverlayNewGameButton.png");
	private static final ImageIcon newGameButtonHoverImg = new ImageIcon("resources/ui/LeaderboardOverlayNewGameButtonHover.png");
	private static final ImageIcon quitButtonImg = new ImageIcon("resources/ui/LeaderboardOverlayQuitButton.png");
	private static final ImageIcon quitButtonHoverImg = new ImageIcon("resources/ui/LeaderboardOverlayQuitButtonHover.png");
	
	//UI elements
	private JButton newGameButton = GuiUtil.createButton(newGameButtonImg, newGameButtonHoverImg);
	private JButton quitButton = GuiUtil.createButton(quitButtonImg, quitButtonHoverImg);
	
	private JLabel[] teamLabels = new JLabel[4];
	private JLabel[] scoreLabels = new JLabel[4];
	
	//Constructor
	public LeaderboardOverlay() { //Aiden
		
		super(frameImg);
		
		for (int i = 0; i < teamLabels.length; i++) {
			teamLabels[i] = new JLabel("");
			teamLabels[i].setFont(GuiUtil.PIXELFONTSMALL);
			teamLabels[i].setForeground(Color.BLACK);
			teamLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			teamLabels[i].setBounds(labelX[i], teamLabelY, 80, 30);
			add(teamLabels[i]);
			
			scoreLabels[i] = new JLabel("");
			scoreLabels[i].setFont(GuiUtil.PIXELFONTSMALL);
			scoreLabels[i].setForeground(Color.BLACK);
			scoreLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			scoreLabels[i].setBounds(labelX[i], scoreLabelY, 80, 30);
			add(scoreLabels[i]);
		}
		
		newGameButton.setBounds(455, 205, 183, 60);
		add(newGameButton);

		quitButton.setBounds(512, 270, 89, 52);
		add(quitButton);
	}

	//Melinda
	public void updateData(Score[] scores) {
		if (scores == null)
			return;

		// Maps visual podium columns (Left to right) to sorted scores array:
		// Index 0 -> 2nd Place (scores[1])
		// Index 1 -> 1st Place (scores[0])
		// Index 2 -> 3rd Place (scores[2])
		// Index 3 -> 4th Place (scores[3])
		int[] podiumMapping = {1, 0, 2, 3};

		for (int i = 0; i < teamLabels.length; i++) {
			int scoreIndex = podiumMapping[i];
			if (scoreIndex < scores.length && scores[scoreIndex] != null) {
				Team t = scores[scoreIndex].getTeam();
				String name = t.toString();
				
				teamLabels[i].setText(name);
				scoreLabels[i].setText(String.valueOf(scores[scoreIndex].getScore()));
			} else {
				teamLabels[i].setText("");
				scoreLabels[i].setText("");
			}
		}
	}

	public JButton getNewGameButton() {
		return newGameButton;
	}

	public JButton getQuitButton() {
		return quitButton;
	}
}