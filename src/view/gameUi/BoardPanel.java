/*
 * File: BoardPanel.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Aiden
 * Date: November 27, 2026
 * Description: Displays the game board and selected-piece previews.
 */

package view.gameUi;

import shared.GameConstants;
import shared.Team;

import javax.swing.*;
import java.awt.*;

import static view.ViewConstants.teamColors;
import static view.ViewConstants.blockSize;
import static view.ViewConstants.blockPadding;

//Aiden
public class BoardPanel extends JPanel {

	// IMAGE RESOURCES
	private static final ImageIcon img = new ImageIcon("resources/ui/BoardPanelFrame.png");

	private JLabel borderFrameLabel = new JLabel(img);
	private Team[][] board;
	private int previewX, previewY;
	private boolean previewPieceAltSkin = true;
	private boolean[][] previewPieceData;

	public BoardPanel() {
		super(null);
		borderFrameLabel.setBounds(0, 0, 500, 500);
		add(borderFrameLabel);
		setOpaque(false);
	}

	//redraws an updated board
	public void updateBoard(Team[][] board) {
		this.board = board;
		repaint();
	}

	public void previewPiece(int mouseX, int mouseY, boolean[][] pieceData) {
		previewPieceAltSkin = false;
		previewX = (mouseX - 50) / blockSize;
		previewY = (mouseY - 50) / blockSize;
		previewPieceData = pieceData;
		repaint();
	}

	public void previewPieceAlt(int mouseX, int mouseY) {
		previewPieceAltSkin = true;
		previewX = (mouseX - 50) / blockSize;
		previewY = (mouseY - 50) / blockSize;
		repaint();
	}

	public int getPreviewX() {
		return previewX;
	}

	public int getPreviewY() {
		return previewY;
	}

	//custom rendering
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(50, 50, 400, 400);

		if (board == null)
			return;
		for (int x = 0; x < GameConstants.boardSize; x++) {
			for (int y = 0; y < GameConstants.boardSize; y++) {
				// draw tile
				g.setColor(teamColors.get(board[x][y]));
				if (board[x][y] == Team.NULL)
					g.fillRect(50 + x * blockSize, 50 + y * blockSize, blockSize - blockPadding,
							blockSize - blockPadding);
				else
					g.fillRoundRect(50 + x * blockSize, 50 + y * blockSize, blockSize - blockPadding,
							blockSize - blockPadding, 2, 2);
			}
		}

		// Alt version, used for when using cards
		if (previewPieceAltSkin) {
			g.setColor(Color.BLACK);
			g.fillRect(50 + previewX * blockSize - 3 + (int) (blockSize / 2), 50, 6, 400);
			g.fillRect(50, 50 + previewY * blockSize - 3 + (int) (blockSize / 2), 400, 6);
			g.setColor(Color.CYAN);
			g.fillRect(50 + previewX * blockSize - 1 + (int) (blockSize / 2), 50, 2, 400);
			g.fillRect(50, 50 + previewY * blockSize - 1 + (int) (blockSize / 2), 400, 2);
			return;
		}
		if (previewPieceData == null)
			return;

		g.setColor(Color.CYAN); // preview color
		for (int x = 0; x < previewPieceData.length; x++) {
			for (int y = 0; y < previewPieceData[x].length; y++) {
				if (!previewPieceData[x][y])
					continue;

				int boardX = previewX + x;
				int boardY = previewY + y;
				if (boardX >= 0 && boardX < GameConstants.boardSize && boardY >= 0
						&& boardY < GameConstants.boardSize) {
					g.fillRoundRect(50 + boardX * blockSize, 50 + boardY * blockSize, blockSize - blockPadding,
							blockSize - blockPadding, 2, 2);
				}
			}
		}

		// frame
		super.paintComponent(g);
	}
}