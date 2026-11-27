package view.overlay;

import java.awt.Color;
import javax.swing.*;
import model.Score;
import shared.Team;
import view.GuiUtil;

//Melinda
public class LeaderboardOverlay extends Overlay {
	
	//Constants
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
//			rowLabels[i] = new JLabel("Rank " + (i + 1) + "     ---");
			teamLabels[i] = new JLabel("");
			teamLabels[i].setFont(GuiUtil.PIXELFONTSMALL);
			teamLabels[i].setForeground(Color.BLACK);
			teamLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			teamLabels[i].setBounds(labelX[i], teamLabelY, 80,30);
			add(teamLabels[i]);
			
			scoreLabels[i] = new JLabel("");
			scoreLabels[i].setFont(GuiUtil.PIXELFONTSMALL);
			scoreLabels[i].setForeground(Color.BLACK);
			scoreLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			scoreLabels[i].setBounds(labelX[i], scoreLabelY, 80,30);
			add(scoreLabels[i]);
		}
		
		closeButton.setLocation(540, 38);
		
		newGameButton.setBounds(455, 205, 183, 60);
		add(newGameButton);

		quitButton.setBounds(512, 270, 89, 52);
		add(quitButton);
		
		
//		rowLabels[0].setBounds();
		
		//old
//		setLayout(null);
//		setOpaque(true);
//		setBackground(Color.WHITE);
//		setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
//
//		// Title
//		JLabel titleLabel = new JLabel("LEADERBOARD");
//		titleLabel.setFont(GuiUtil.PIXELFONTMEDIUM);
//		titleLabel.setForeground(Color.BLACK);
//		titleLabel.setBounds(30, 25, 300, 40);
//		add(titleLabel);
//
//		// Rows for Ranks 1 to 4 (Single label per row prevents spacing/overlap issues)
//		int startY = 90;
//		for (int i = 0; i < 4; i++) {
//			rowLabels[i] = new JLabel("Rank " + (i + 1) + "     ---");
//			rowLabels[i].setFont(GuiUtil.PIXELFONTSMALL);
//			rowLabels[i].setForeground(Color.BLACK);
//			rowLabels[i].setBounds(40, startY + (i * 50), 360, 30);
//			add(rowLabels[i]);
//		}

		// Bottom Action Buttons
	}

	//Melinda
	public void updateData(Score[] scores) {
		// Reset rows to placeholder first
//		for (int i = 0; i < teamLabels.length; i++) {
//			teamLabels[i].setText("");
//		}

		if (scores == null)
			return;

		for (int i = 0; i < scores.length; i++) {
			if (i < teamLabels.length && scores[i] != null) {
				Team t = scores[i].getTeam();
				String name = t.toString();
				
//				name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase(); //i want full uppercase, no need for this
				// Format with clear spacing: Rank 1 Blue (81) <-old
//				rowLabels[i].setText(String.format("Rank %d     %s (%d)", (i + 1), name, scores[i].getScore()));
				
				teamLabels[i].setText(name);
				scoreLabels[i].setText(String.valueOf(scores[i].getScore()));
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