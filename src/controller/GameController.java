package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.Timer;

import model.ComputerPlayer;
import model.Game;
import model.GameState;
import model.Player;
import shared.CardTypes;
import shared.PiecePlacer;
import shared.Team;
import view.GameScreen;
import view.SoundService;
import view.gameUi.Piece;

// Aiden & Melinda & Yanjun
public class GameController implements ActionListener, MouseListener, MouseMotionListener {
	
	//CONSTANTS
	private static final String ACTION_SETTINGS = "settings";
	private static final String ACTION_HELP = "help";
	private static final String ACTION_BACK = "back";
	private static final String ACTION_PIECE = "piece";
	private static final String ACTION_CARD_PREFIX = "card:";

	
	//FIELDS
	private AppController appController;
	private Game model = new Game();
	private GameScreen gameScreen;

	private int previewPieceNum;
	private boolean[][] previewPiece;
	private CardTypes previewCardType;
	private int lastMouseX, lastMouseY;

	// teams controlled by a real person (the rest are computers)
	private final Set<Team> humanTeams = new HashSet<>();

	private Timer roundTimer;
	private Timer cpuTimer;
	private int secElapsed = 0;
	private int currentTimeLimit;

	
	//Constructor - Aiden
	public GameController(AppController appController, GameScreen gameScreen) {
		this.appController = appController;
		this.gameScreen = gameScreen;
		refreshGameScreen();

		// Adding listeners with custom "action commands"
		gameScreen.getSettingsButton().setActionCommand(ACTION_SETTINGS);
		gameScreen.getSettingsButton().addActionListener(this);

		gameScreen.getHelpButton().setActionCommand(ACTION_HELP);
		gameScreen.getHelpButton().addActionListener(this);

		gameScreen.getBackButton().setActionCommand(ACTION_BACK);
		gameScreen.getBackButton().addActionListener(this);

		//listener for pieces in PiecePanel
		for (Piece piece : gameScreen.getPiecePanel().getPieces()) {
			piece.setActionCommand(ACTION_PIECE);
			piece.addActionListener(this);
			piece.addMouseListener(this);
		}

		Piece bonusPiece = gameScreen.getPiecePanel().getBonusOneByOnePiece();
		bonusPiece.setActionCommand(ACTION_PIECE);
		bonusPiece.addActionListener(this);
		bonusPiece.addMouseListener(this);

		//listener for card buttons in DeckPanel
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

	//Aiden
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		// Play click sound effect for buttons/cards, not raw piece selections
		if (!ACTION_PIECE.equals(action)) {
			SoundService.playSound("click");
		}

		if (action.startsWith(ACTION_CARD_PREFIX)) {
			if (!isHuman(model.getCurrentPlayer().getTeam()))
				return;
			int index = Integer.parseInt(action.substring(ACTION_CARD_PREFIX.length()));
			selectCard((JButton) e.getSource(), index);
			return;
		}

		//coresponding action based on it's "tag"
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
			if (!isHuman(model.getCurrentPlayer().getTeam()))
				return;
			selectPiece((Piece) e.getSource());
			break;
		default:
			break;
		}
	}

	//Aiden
	private void selectPiece(Piece piece) {
		// if already selected, deselect
		if (gameScreen.getPiecePanel().getSelectedPiece() == piece) {
			clearSelection();
			return;
		}

		clearSelection();
		previewPieceNum = gameScreen.getPiecePanel().getPieceNum(piece);
		previewPiece = PiecePlacer.transpose(piece.getData());
		gameScreen.selectPiece(piece);
	}

	//Aiden
	private void selectCard(JButton cardButton, int index) {
		Player current = model.getCurrentPlayer();
		if (!current.canUseCard()) {
			return;
		}

		if (gameScreen.getDeckPanel().getSelectedCard() == cardButton) {
			clearSelection();
			return;
		}

		CardTypes card = current.getDeck()[index];
		if (card == null) {
			return;
		}

		clearSelection();
		if (card == CardTypes.REGENERATE) {
			model.playCard(card, 0, 0);
			current.triggerCardCooldown();
			gameScreen.getDeckPanel().setDeckButtonsEnabled(false);
			SoundService.playSound("place");
			refreshGameScreen();
			return;
		}

		previewCardType = card;
		gameScreen.getDeckPanel().selectCard(cardButton);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// ignore clicks while it is the computer's turn
		if (!isHuman(model.getCurrentPlayer().getTeam()))
			return;

		// if mouse is inside board panel, attempt to place card or piece
		if (e.getSource() == gameScreen.getBoardPanel()) {
			int x = gameScreen.getBoardPanel().getPreviewX();
			int y = gameScreen.getBoardPanel().getPreviewY();

			// Play card
			if (previewCardType != null) {
				SoundService.playSound("place");
				Player current = model.getCurrentPlayer();
				model.playCard(previewCardType, x, y);
				current.triggerCardCooldown();
				gameScreen.getDeckPanel().setDeckButtonsEnabled(false);
				clearSelection();
				refreshGameScreen();
				advanceTurnFlow();
				return;
			}

			// Play turn (place piece)
			if (previewPiece != null && PiecePlacer.validMove(model.getBoard(), x, y, previewPiece,
					model.getCurrentPlayer().getTeam())) {
				Piece selectedPiece = gameScreen.getPiecePanel().getSelectedPiece();
				if (selectedPiece == gameScreen.getPiecePanel().getBonusOneByOnePiece()) {
					model.playBonusOneByOne(x, y);
				} else {
					model.playTurn(x, y, previewPiece, previewPieceNum);
				}

				SoundService.playSound("place");
				clearSelection();
				refreshGameScreen();

				advanceTurnFlow();
			}
		}
	}

	//Aiden
	private void clearSelection() {
		previewPiece = null;
		previewCardType = null;
		gameScreen.getPiecePanel().deselectPiece();
		gameScreen.getDeckPanel().deselectCard();
	}

	//Aiden
	private void refreshGameScreen() {
		gameScreen.update(model.getCurrentPlayer(), model.getPlayers(), model.getBoard());
	}

	// melinda
	public void rotatePreview() {
		if (!isHuman(model.getCurrentPlayer().getTeam()))
			return;

		if (previewPiece != null) {
			model.Player current = model.getCurrentPlayer();
			int flips = current.getRemainingFlips();

			// -1 means Unlimited, otherwise check if they have flips left
			if (flips == -1 || flips > 0) {
				if (flips > 0) {
					current.setRemainingFlips(flips - 1);
				}
				previewPiece = PiecePlacer.rotate(previewPiece);
				refreshPreview();

				// Update the flip counter display on TeamPanel
				gameScreen.getDeckPanel().updateFlipCounter(current.getRemainingFlips());
			} else {
				System.out.println("No flips remaining!");
			}
		}
	}

	// melinda
	public void flipPreview() {
		if (!isHuman(model.getCurrentPlayer().getTeam()))
			return;

		if (previewPiece != null) {
			model.Player current = model.getCurrentPlayer();
			int flips = current.getRemainingFlips();

			// -1 means Unlimited, otherwise check if they have flips left
			if (flips == -1 || flips > 0) {
				if (flips > 0) {
					current.setRemainingFlips(flips - 1);
				}
				previewPiece = PiecePlacer.flip(previewPiece);
				refreshPreview();

				// Update the flip counter display on TeamPanel
				gameScreen.getDeckPanel().updateFlipCounter(current.getRemainingFlips());
			} else {
				System.out.println("No flips remaining!");
			}
		}
	}

	// redraws the preview right away, using the last known mouse position on the board
	private void refreshPreview() {
		if (previewCardType != null) {
			gameScreen.getBoardPanel().previewPieceAlt(lastMouseX, lastMouseY);
			return;
		}
		gameScreen.getBoardPanel().previewPiece(lastMouseX, lastMouseY, previewPiece);
	}

	// Coordinates game over checks, round timers, and CPU turns after any turn change
	private void advanceTurnFlow() {
		advanceTurnFlow(true);
	}

	private void advanceTurnFlow(boolean beginTurn) {
		if (model.isGameOver()) {
			handleGameOver();
			return;
		}

		Player current = model.getCurrentPlayer();
		if (beginTurn) {
			current.beginTurn();
		}
		boolean humanTurn = isHuman(current.getTeam());
		gameScreen.getDeckPanel().setDeckButtonsEnabled(humanTurn && current.canUseCard());

		if (humanTurn) {
			startTurnTimer(currentTimeLimit);
		} else {
			if (roundTimer != null) {
				roundTimer.stop();
			}
			gameScreen.getRoundTimerLabel().setText("CPU Turn...");
			computerTurnIfNeeded();
		}
	}

	// lets the computer play if it is a computer's turn, one move at a time with a short delay
	private void computerTurnIfNeeded() {
		if (model.isGameOver() || isHuman(model.getCurrentPlayer().getTeam()))
			return;

		// stop any potential cpu timer before starting a new one
		if (cpuTimer != null) {
			cpuTimer.stop();
		}

		cpuTimer = new Timer(600, e -> {
			if (model.isGameOver()) {
				return;
			}

			ComputerPlayer.Move m = ComputerPlayer.chooseMove(model.getBoard(), model.getCurrentPlayer());
			if (m == null) {
				model.passTurn();
			} else {
				model.playTurn(m.x, m.y, m.data, m.pieceNum);
				SoundService.playSound("place");
			}
			refreshGameScreen();
			advanceTurnFlow();
		});
		cpuTimer.setRepeats(false);
		cpuTimer.start();
	}

	// cpus = number of computer players (0 to 3). Humans take the first colours in turn order.
	public void setCpuCount(int cpus) {
		humanTeams.clear();
		Team[] order = { Team.BLUE, Team.GREEN, Team.YELLOW, Team.RED };
		int humans = order.length - cpus;
		for (int i = 0; i < humans; i++) {
			humanTeams.add(order[i]);
		}
	}

	private boolean isHuman(Team team) {
		return humanTeams.contains(team);
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

	// melinda
	// initializes game options (number of CPUs, maximum number of flips, and time limit per round)
	public void initialNewGameSettings(int cpuCount, int maxFlips, int timeLimit) {
		this.currentTimeLimit = timeLimit;
		model.initializeNewGame(cpuCount, maxFlips, timeLimit);
		setCpuCount(cpuCount);
		refreshGameScreen();
		advanceTurnFlow();
	}

	// melinda
	// starts the timer for the player based on user's desired time limit declared in new game overlay
	public void startTurnTimer(int timeLimit) {
		if (roundTimer != null) {
			roundTimer.stop();
		}

		if (timeLimit < 0) {
			gameScreen.getRoundTimerLabel().setText("Time: Unlimited");
			return;
		}

		secElapsed = 0;
		
		gameScreen.tick(0, timeLimit);
		roundTimer = new Timer(1000, e -> {
			secElapsed++;
			gameScreen.tick(secElapsed, timeLimit);
			
			if (secElapsed >= timeLimit) {
				roundTimer.stop();

				// clear any selected piece so the new player starts fresh
				clearSelection();

				// automatically pass turn to the next player
				model.passTurn();

				// update the UI for the new player
				refreshGameScreen();

				// advance to next human timer, CPU turn, or game over
				advanceTurnFlow();
			}
		});
		roundTimer.start();
	}

	// melinda
	// save current game state
	public void saveCurrentGame() {
		appController.getSavedGameState().saveGame(model.getBoard(), model.getPlayers(), model.getCpuCount(),
				model.getMaxFlipsAllowed(), currentTimeLimit, model.getCurrentPlayerIndex());
	}

	// melinda - restores the game state and restarts the timer / CPU flow
	public void restoreGameFromSave(GameState state) {
		// stop timers to avoid overlapping
		if (roundTimer != null) {
			roundTimer.stop();
		}
		if (cpuTimer != null) {
			cpuTimer.stop();
		}

		model.restoreGame(state.getBoardState(), state.getPlayersState(), state.getCpuCount(), state.getMaxFlips(),
				state.getTimeLimit(), state.getCurrentPlayerIndex());
		this.currentTimeLimit = state.getTimeLimit();
		setCpuCount(state.getCpuCount());
		refreshGameScreen();
		advanceTurnFlow(false);
	}

	// melinda
	private void handleGameOver() {
		if (roundTimer != null) {
			roundTimer.stop();
		}
		if (cpuTimer != null) {
			cpuTimer.stop();
		}
		System.out.println("Game Over! No players have valid moves left.");

		// Clear the saved game state so finished games aren't reloadable
		appController.getSavedGameState().clear();

		// Sort final scores and push them to the overlay
		model.Score[] finalScores = model.getSortedLeaderboard();
		appController.getLeaderboardOverlay().updateData(finalScores);

		// Show the leaderboard overlay automatically
		AppController.showLeaderboardOverlay();
	}

	public Game getGame() {
		return model;
	}
}