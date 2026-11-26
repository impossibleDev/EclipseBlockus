package view;

import shared.Team;
import view.gameUi.BoardPanel;
import view.gameUi.DeckPanel;
import view.gameUi.Piece;
import view.gameUi.PiecesPanel;
import view.gameUi.TeamPanel;

import java.util.EnumMap;
import javax.swing.*;

import model.Player;

//Aiden
public class GameScreen extends Screen {

	//imageIcon Resources
	public static final ImageIcon bgImageIcon = new ImageIcon("resources/bg/gameBg.png");
	private static final ImageIcon backButtonImg = new ImageIcon("resources/ui/BackButton.png");
	private static final ImageIcon backButtonHoverImg = new ImageIcon("resources/ui/BackButtonHover.png");
	public static final EnumMap<Team, ImageIcon> cornerImages = new EnumMap<>(Team.class);
	static {
		//loads  corner images
		cornerImages.put(Team.YELLOW, new ImageIcon("resources/bg/CornerYellow.png"));
		cornerImages.put(Team.RED, new ImageIcon("resources/bg/CornerRed.png"));
		cornerImages.put(Team.GREEN, new ImageIcon("resources/bg/CornerGreen.png"));
		cornerImages.put(Team.BLUE, new ImageIcon("resources/bg/CornerBlue.png"));
	}

	//UI Elements
	private BoardPanel boardPanel;
	private TeamPanel teamPanel;
	private DeckPanel deckPanel;
	private PiecesPanel piecesPanel;
	private JScrollPane piecesScrollPane;
	private JButton settingsButton;
	private JButton helpButton; // melinda
	private JButton backButton; // melinda
	private JLabel currentCornerImage;
	private JLabel bgLabel = new JLabel(bgImageIcon);
	private EnumMap<Team, JLabel> cornerLabels = new EnumMap<>(Team.class);

	//Constructors
	public GameScreen() {
		super();
		setLayout(null);

		// add UI elements
		boardPanel = new BoardPanel();
		teamPanel = new TeamPanel();
		deckPanel = new DeckPanel();

		piecesPanel = new PiecesPanel();
		piecesScrollPane = new JScrollPane(piecesPanel);
		piecesScrollPane.setOpaque(false);
		piecesScrollPane.getViewport().setOpaque(false);
		piecesScrollPane.setBorder(null);
		piecesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		piecesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		settingsButton = GuiUtil.createButton(ViewConstants.settingsImage);
		helpButton = GuiUtil.createButton(ViewConstants.helpImage);
		backButton = GuiUtil.createButton(backButtonImg, backButtonHoverImg);

		add(backButton);
		add(boardPanel);
		add(deckPanel);
		add(teamPanel);
		// add(piecesPanel);
		add(piecesScrollPane);
		add(settingsButton);
		add(helpButton);

		// corner images
		JLabel yellowCorner = new JLabel(cornerImages.get(Team.YELLOW));
		JLabel redCorner = new JLabel(cornerImages.get(Team.RED));
		JLabel blueCorner = new JLabel(cornerImages.get(Team.BLUE));
		JLabel greenCorner = new JLabel(cornerImages.get(Team.GREEN));

		cornerLabels.put(Team.YELLOW, yellowCorner);
		cornerLabels.put(Team.RED, redCorner);
		cornerLabels.put(Team.BLUE, blueCorner);
		cornerLabels.put(Team.GREEN, greenCorner);

		yellowCorner.setVisible(false);
		redCorner.setVisible(false);
		blueCorner.setVisible(false);
		greenCorner.setVisible(false);

		add(yellowCorner);
		add(redCorner);
		add(blueCorner);
		add(greenCorner);

		// bg image
		add(bgLabel);
		boardPanel.setVisible(true);
	}

	public void update(Player currentPlayer, Player[] players, Team[][] board) {
		this.teamPanel.update(players);
		this.deckPanel.update(currentPlayer);
		this.piecesPanel.update(currentPlayer);
		this.boardPanel.updateBoard(board);

		if (currentCornerImage != null) {
			currentCornerImage.setVisible(false);
		}
		currentCornerImage = cornerLabels.get(currentPlayer.getTeam());
		if (currentCornerImage != null) {
			currentCornerImage.setVisible(true);
		}
	}

	// getters for ui elements
	public PiecesPanel getPiecePanel() {
		return piecesPanel;
	}

	public BoardPanel getBoardPanel() {
		return boardPanel;
	}

	public TeamPanel getTeamPanel() {
		return teamPanel;
	}

	public DeckPanel getDeckPanel() {
		return deckPanel;
	}
	
	public JButton getSettingsButton() {
		return settingsButton;
	}

	public JButton getHelpButton() {
		return helpButton;
	}

	public JButton getBackButton() {
		return backButton;
	}

	@Override
	public void resize() {
		super.resize();
		int w = getWidth();
		int h = getHeight();

		boardPanel.setBounds((int) (w / 2 - 250), (int) (h / 2 - 250), 500, 500);
		teamPanel.setBounds(50, 50, 250, 660);
		deckPanel.setBounds(50, 490, 250, 220);
		//piecesPanel.setBounds(w - 300, 50, 250, 660);
		//piecesPanel.setSize(piecesPanel.getPreferredSize());
		piecesScrollPane.setBounds(w - 300, 60, 250, 660);

		settingsButton.setBounds(w - 70, 10, 60, 60);
		helpButton.setBounds(w - 140, 10, 60, 60);
		backButton.setBounds(20, 20, 60, 60);

		cornerLabels.get(Team.BLUE).setBounds(0, 0, 600, 700);
		cornerLabels.get(Team.GREEN).setBounds(w - 600, 0, 600, 700);
		cornerLabels.get(Team.RED).setBounds(0, h - 700, 600, 700);
		cornerLabels.get(Team.YELLOW).setBounds(w - 600, h - 700, 600, 700);

		bgLabel.setSize(getSize());
	}

	public void hoverPiece(Piece piece) {
		piecesPanel.hoverPiece(piece);
	}

	public void dehoverPiece() {
		piecesPanel.dehoverPiece();
	}

	public void selectPiece(Piece piece) {
		piecesPanel.selectPiece(piece);
	}
}