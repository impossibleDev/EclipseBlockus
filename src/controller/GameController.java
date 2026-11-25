package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import model.Game;
import shared.PiecePlacer;
import view.GameScreen;
import view.SoundService;
import view.gameUi.Piece;

// Aiden & Melinda & Yanjun
public class GameController implements ActionListener, MouseListener, MouseMotionListener {

	private AppController appController;
	private Game model = new Game();
	private GameScreen gameScreen;

	private int previewPieceNum;
	private boolean[][] previewPiece;
	private int lastMouseX, lastMouseY;

	public GameController(AppController appController, GameScreen gameScreen) {
		this.appController = appController;
		this.gameScreen = gameScreen;
		gameScreen.update(model.getCurrentPlayer(), model.getPlayers(), model.getBoard());

		// Adding listeners
		gameScreen.getSettingsButton().addActionListener(this);
		gameScreen.getHelpButton().addActionListener(this);
		gameScreen.getBackButton().addActionListener(this);

		for (Piece piece : gameScreen.getPiecePanel().getPieces()) {
			piece.addActionListener(this);
			piece.addMouseListener(this);
		}

		gameScreen.getBoardPanel().addMouseMotionListener(this);
		gameScreen.getBoardPanel().addMouseListener(this);
		new Keybinds(gameScreen, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// plays click sound effect for buttons, not a piece 
		if (!(e.getSource() instanceof Piece)) {
			SoundService.playSound("click");
		}
		if (e.getSource() == gameScreen.getSettingsButton()) {
			AppController.showSettingsOverlay();

		} else if (e.getSource() == gameScreen.getHelpButton()) {
			AppController.showHelpOverlay();

		} else if (e.getSource() == gameScreen.getBackButton()) {
			AppController.showSaveOverlay();

		} else if (e.getSource() instanceof Piece) {
			Piece piece = (Piece) e.getSource();

			// if already selected, deselect
			if (gameScreen.getPiecePanel().getSelectedPiece() == piece) {
				gameScreen.getPiecePanel().deselectPiece();
				previewPiece = null;
				return;
			}

			// select new piece
			previewPieceNum = gameScreen.getPiecePanel().getPieceNum(piece);
			previewPiece = PiecePlacer.transpose(piece.getData());
			gameScreen.selectPiece(piece);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// if mouse is inside board panel, attempt to place the selected piece
		if (e.getSource() == gameScreen.getBoardPanel()) {
			int x = gameScreen.getBoardPanel().getPreviewX();
			int y = gameScreen.getBoardPanel().getPreviewY();

			if (previewPiece != null && PiecePlacer.validMove(model.getBoard(), x, y, previewPiece,
					model.getCurrentPlayer().getTeam())) {
				model.playTurn(x, y, previewPiece);
				SoundService.playSound("place");
				previewPiece = null;                              
				gameScreen.getPiecePanel().deselectPiece();
				gameScreen.update(model.getCurrentPlayer(), model.getPlayers(), model.getBoard());
			}
		}
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
		gameScreen.getBoardPanel().previewPiece(lastMouseX, lastMouseY, previewPiece);
	}
	

	@Override
	public void mouseMoved(MouseEvent e) {
		lastMouseX = e.getX();
		lastMouseY = e.getY();
		gameScreen.getBoardPanel().previewPiece(e.getX(), e.getY(), previewPiece);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() instanceof Piece) {
			gameScreen.hoverPiece((Piece) e.getSource());
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() instanceof Piece) {
			gameScreen.dehoverPiece();
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