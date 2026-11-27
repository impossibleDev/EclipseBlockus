/*
 * File: Piece.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Aiden, Melinda
 * Date: November 27, 2026
 * Description: Renders one polyomino piece and its selected appearance.
 */

package view.gameUi;

import shared.Team;

import javax.swing.*;
import java.awt.*;

import static view.ViewConstants.teamColors;
import static view.ViewConstants.blockSize;
import static view.ViewConstants.blockPadding;

//Aiden
public class Piece extends JButton {
	private Team team;
	private boolean[][] data;
	private boolean altSkin = false;

	public Piece(boolean[][] data, Team team) {
		setData(data);
		setTeam(team);
		
	}

	//getters & setters for data
	public boolean[][] getData() {
		return data;
	}

	public void setData(boolean[][] data) {
		this.data = data;
		Dimension dimension = getPreferredSize();
		setSize(dimension);
		setPreferredSize(dimension);
	}

	@Override
	public Dimension getPreferredSize() {
		if (data == null)
			return new Dimension(20, 20);
		int w = Math.max(blockSize * data[0].length, 20);
		int h = Math.max(blockSize * data.length, 20);
		return new Dimension(w, h);
	}


	public void setTeam(Team team) {
		this.team = team;
		repaint();
	}

	// custom paint
	@Override
	protected void paintComponent(Graphics g) {
		// super.paintComponent(g);
		if (data == null)
			return;

		int rows = data.length;
		int cols = data[0].length;

		// draw outlines if altSkin == true
		if (altSkin) {
			g.setColor(Color.WHITE);
			for (int r = 0; r < rows; r++) {
				for (int c = 0; c < cols; c++) {
					if (data[r][c]) {
						g.fillRect(c * blockSize, r * blockSize, blockSize, blockSize);
					}
				}
			}
		}

		// draw tiles
		g.setColor(teamColors.get(team));
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (data[r][c]) {
					g.fillRoundRect(c * blockSize + blockPadding / 2, r * blockSize + blockPadding / 2,
							blockSize - blockPadding, blockSize - blockPadding, 2, 2);
				}
			}
		}
	}

	// aka appearance when selected, if it have outline or not
	public void altSkinOn(boolean altSkin) {
		this.altSkin = altSkin;
		repaint();
	}
}