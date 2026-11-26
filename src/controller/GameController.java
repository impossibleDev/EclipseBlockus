package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import model.Game;
import shared.CardTypes;
import shared.PiecePlacer;
import view.GameScreen;
import view.SoundService;
import view.gameUi.Piece;

// Aiden & Melinda & Yanjun
public class GameController implements ActionListener, MouseListener, MouseMotionListener {

	private static final String ACTION_SETTINGS = "settings";
	private static final String ACTION_HELP = "help";
	private static final String ACTION_BACK = "back";
	private static final String ACTION_PIECE = "piece";
	private static final String ACTION_CARD_PREFIX = "card:";

	private AppController appController;
	private Game model = new Game();
	private GameScreen gameScreen;

	private boolean[][] previewPiece;
	private CardTypes previewCardType;
	private int lastMouseX, lastMouseY;

	public GameController(AppController appController, GameScreen gameScreen) {
		this.appController = appController;
		this.gameScreen = gameScreen;
		refreshGameScreen();

		// Adding listeners
		gameScreen.getSettingsButton().setActionCommand(ACTION_SETTINGS);
		gameScreen.getSettingsButton().addActionListener(this);
		gameScreen.getHelpButton().setActionCommand(ACTION_HELP);
		gameScreen.getHelpButton().addActionListener(this);
		gameScreen.getBackButton().setActionCommand(ACTION_BACK);
		gameScreen.getBackButton().addActionListener(this);

		for (Piece piece : gameScreen.getPiecePanel().getPieces()) {
			piece.setActionCommand(ACTION_PIECE);
			piece.addActionListener(this);
			piece.addMouseListener(this);
		}

		JButton[] deck = gameScreen.getDeckPanel().getCurrentDeck();
		for (int i = 0; i < deck.length; i++) {
			deck[i].setActionCommand(ACTION_CARD_PREFIX + i);
			deck[i].addActionListener(this);
			deck[i].addMouseListener(this);
		}

		gameScreen.getBoardPanel().addMouseMotionListener(this);
		gameScreen.getBoardPanel().addMouseListener(this);
		new Keybinds(gameScreen, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (!ACTION_PIECE.equals(action)) {
			SoundService.playSound("click");
		}
		if (action.startsWith(ACTION_CARD_PREFIX)) {
			int index = Integer.parseInt(action.substring(ACTION_CARD_PREFIX.length()));
			selectCard((JButton) e.getSource(), index);
			return;
		}

		switch (action) {
			case ACTION_SETTINGS:
				AppController.showSettingsOverlay();
				break;
			case ACTION_HELP:
				AppController.showHelpOverlay();
				break;
			case ACTION_BACK:
				AppController.showSaveOverlay();
				break;
			case ACTION_PIECE:
				selectPiece((Piece) e.getSource());
				break;
			default:
				break;
		}
	}

	private void selectPiece(Piece piece) {
		if (gameScreen.getPiecePanel().getSelectedPiece() == piece) {
			clearSelection();
			return;
		}

		clearSelection();
		previewPiece = PiecePlacer.transpose(piece.getData());
		gameScreen.selectPiece(piece);
	}

	private void selectCard(JButton cardButton, int index) {
		if (gameScreen.getDeckPanel().getSelectedCard() == cardButton) {
			clearSelection();
			return;
		}
		clearSelection();
		previewCardType = model.getCurrentPlayer().getDeck()[index];
		gameScreen.getDeckPanel().selectCard(cardButton);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		// if mouse is inside board panel, attempt to play turn (use ability or place piece)
		if (e.getSource() == gameScreen.getBoardPanel()) {
			int x = gameScreen.getBoardPanel().getPreviewX();
			int y = gameScreen.getBoardPanel().getPreviewY();
			
			//Play card
			if (previewCardType!=null) {
				SoundService.playSound("place");
				model.playCard(previewCardType, x, y);
				gameScreen.getDeckPanel().setDeckButtonsEnabled(false); //you can only play card once per turn
				clearSelection();
				refreshGameScreen();
				return;
			}
			
			//Play turn
			if (previewPiece != null && PiecePlacer.validMove(model.getBoard(), x, y, previewPiece,
					model.getCurrentPlayer().getTeam())) {
				
				model.playTurn(x, y, previewPiece);
				SoundService.playSound("place");
				gameScreen.getDeckPanel().setDeckButtonsEnabled(true); //enable back deck buttons if disabled from last player's card use
				clearSelection();
				refreshGameScreen();
			}
		}
	}

	private void clearSelection() {
		previewPiece = null;
		previewCardType = null;
		gameScreen.getPiecePanel().deselectPiece();
		gameScreen.getDeckPanel().deselectCard();
	}

	private void refreshGameScreen() {
		gameScreen.update(model.getCurrentPlayer(), model.getPlayers(), model.getBoard());
	}
	
	public void rotatePreview() {
		if (previewPiece != null) {
			previewPiece = PiecePlacer.rotate(previewPiece);
			refreshPreview();
		}
	}

	public void flipPreview() {
		if (previewPiece != null) {
			previewPiece = PiecePlacer.flip(previewPiece);
			refreshPreview();
		}
	}

	// redraws the preview right away, using the last known mouse position on the board
	private void refreshPreview() {
		if (previewCardType!=null) {
			gameScreen.getBoardPanel().previewPieceAlt(lastMouseX, lastMouseY);
			return;
		}
		gameScreen.getBoardPanel().previewPiece(lastMouseX, lastMouseY, previewPiece);
	}
	

	@Override
	public void mouseMoved(MouseEvent e) {
		lastMouseX = e.getX();
		lastMouseY = e.getY();
		if (previewCardType != null) {
		    gameScreen.getBoardPanel().previewPieceAlt(e.getX(), e.getY());
		} else {
		    gameScreen.getBoardPanel().previewPiece(e.getX(), e.getY(), previewPiece);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() instanceof Piece) {
			gameScreen.hoverPiece((Piece) e.getSource());
		} else if (e.getSource() instanceof JButton) {
			gameScreen.getDeckPanel().hoverCard((JButton) e.getSource());
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() instanceof Piece) {
			gameScreen.dehoverPiece();
		} else if (e.getSource() instanceof JButton) {
			gameScreen.getDeckPanel().dehoverCard();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}
}