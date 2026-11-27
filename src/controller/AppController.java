package controller;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import model.Game;
import model.GameState;
import view.AppFrame;
import view.SoundService;
import view.overlay.HelpPanelOverlay;
import view.overlay.LeaderboardOverlay;
import view.overlay.NewGameOverlay;
import view.overlay.SaveOverlay;
import view.overlay.SettingsOverlay;

public class AppController {

	private static AppFrame view = new AppFrame();
	private static GameController gameController;
	private static SettingsOverlay settingsOverlay;
	private static LeaderboardOverlay leaderboardOverlay; // melinda
	private static GameState savedGameState = new GameState(); // melinda

	public AppController() {
		SoundService.loadSound("bgm", "resources/audio/BackgroundMusic.wav");
		SoundService.loadSound("click", "resources/audio/Click.wav");
		SoundService.loadSound("place", "resources/audio/PlacePiece.wav");

		// start background music
		SoundService.playMusic("bgm");

		newGame();
		view.toMenuScreen();
	}

	private void newGame() {
		gameController = new GameController(this, view.getGameScreen());
		new MenuController(view.getMenuScreen(), this);
	}

	// melinda & yanjun
	// displays the new game settings panel before starting the game
	public static void showNewGameOverlay() {
		// check if a previous save exists
		if (savedGameState != null && savedGameState.hasSavedGame()) {
			int choice = JOptionPane.showConfirmDialog(view.getMenuScreen(),
					"A previous save was found from this session. Would you like to load it?", "Load Game",
					JOptionPane.YES_NO_OPTION);

			if (choice == JOptionPane.YES_OPTION) {
				// Restore the game into the active model
				if (gameController != null) {
					gameController.restoreGameFromSave(savedGameState);
				}
				view.toGameScreen();
				return;
			}
		}

		// if there is no previous file saved, start a new game
		NewGameOverlay overlay = new NewGameOverlay();

		openOverlay(overlay);

		overlay.getCloseButton().addActionListener(e -> {
			SoundService.playSound("click");
			closeOverlay(overlay);
			view.getMenuScreen().getSettingsButton().setEnabled(true);
		});

		overlay.getStartButton().addActionListener(e -> {
			SoundService.playSound("click");

			// Clear old session save when starting a fresh game
			savedGameState = new GameState();

			// obtain user's desired settings
			int cpuCount = parseSettingValue(overlay.getCpuSelector().getSelectedItem(), 0);
			int maxFlips = parseSettingValue(overlay.getFlipSelector().getSelectedItem(), -1);
			int timeLimit = parseSettingValue(overlay.getTimeLimitSelector().getSelectedItem(), -1);

			// initialize game model
			if (gameController != null) {
				gameController.initialNewGameSettings(cpuCount, maxFlips, timeLimit);
			}
			closeOverlay(overlay);
			view.getMenuScreen().getSettingsButton().setEnabled(true);
			view.toGameScreen();
		});
	}

	// melinda
	// helper method to convert JComboBox items to int
	private static int parseSettingValue(Object selection, int defaultValue) {
		if (selection instanceof Integer) {
			return (Integer) selection;
		} else if (selection instanceof String) {
			String str = (String) selection;
			if ("Unlimited".equalsIgnoreCase(str)) {
				return -1;
			}
			try {
				return Integer.parseInt(str);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	// melinda
	// displays help overlay
	public static void showHelpOverlay() {
		HelpPanelOverlay overlay = new HelpPanelOverlay();

		openOverlay(overlay);

		if (overlay.getCloseButton() != null) {
			overlay.getCloseButton().addActionListener(e -> closeOverlay(overlay));
		}
	}

	// melinda
	// displays settings overlay (created once, listeners added once)
	public static void showSettingsOverlay() {
		if (settingsOverlay == null) {
			settingsOverlay = new SettingsOverlay();

			// music toggle
			settingsOverlay.getMusicButton()
					.addItemListener(e -> SoundService.setMusicOn(settingsOverlay.getMusicButton().isSelected()));
			// sfx toggle
			settingsOverlay.getSfxButton()
					.addItemListener(e -> SoundService.setSfxOn(settingsOverlay.getSfxButton().isSelected()));
			// close overlay
			settingsOverlay.getCloseButton().addActionListener(e -> closeOverlay(settingsOverlay));
		}
		openOverlay(settingsOverlay);
	}

	// melinda
	// displays leaderboard overlay when game ends
	public static void showLeaderboardOverlay() {
		if (leaderboardOverlay == null) {
			leaderboardOverlay = new LeaderboardOverlay();

			// Quit button
			leaderboardOverlay.getQuitButton().addActionListener(e -> {
				SoundService.playSound("click");
				closeOverlay(leaderboardOverlay);
				view.toMenuScreen();
			});

			// New Game button
			leaderboardOverlay.getNewGameButton().addActionListener(e -> {
				SoundService.playSound("click");
				closeOverlay(leaderboardOverlay);
				showNewGameOverlay();
			});
		}

		// Pull the final scores and update the overlay data every time it opens
		if (gameController != null && gameController.getGame() != null) {
			Game game = (Game) gameController.getGame();
			leaderboardOverlay.updateData(game.getSortedLeaderboard());
		}

		openOverlay(leaderboardOverlay);
	}

	public static void hideLeaderboardOverlay() {
		if (leaderboardOverlay != null) {
			closeOverlay(leaderboardOverlay);
		}
	}

	public LeaderboardOverlay getLeaderboardOverlay() {
		return getOrCreateLeaderboardOverlay();
	}

	private static LeaderboardOverlay getOrCreateLeaderboardOverlay() {
		if (leaderboardOverlay == null) {
			leaderboardOverlay = new LeaderboardOverlay();

			// Quit button
			leaderboardOverlay.getQuitButton().addActionListener(e -> {
				SoundService.playSound("click");
				closeOverlay(leaderboardOverlay);
				view.toMenuScreen();
			});

			// New Game button
			leaderboardOverlay.getNewGameButton().addActionListener(e -> {
				SoundService.playSound("click");
				closeOverlay(leaderboardOverlay);
				showNewGameOverlay();
			});
		}
		return leaderboardOverlay;
	}

	// melinda
	// Displays the save game overlay when back is clicked
	public static void showSaveOverlay() {
		SaveOverlay overlay = new SaveOverlay();

		openOverlay(overlay);

		// Close button
		overlay.getCloseButton().addActionListener(e -> closeOverlay(overlay));

		// Don't save
		overlay.getDontSaveButton().addActionListener(e -> {
			closeOverlay(overlay);
			view.toMenuScreen();
		});

		// Save
		overlay.getSaveButton().addActionListener(e -> {
			closeOverlay(overlay);
			// save feature
			if (gameController != null) {
				gameController.saveCurrentGame();
			}
			System.out.println("Saving game...");
			view.toMenuScreen();
		});
	}

	// Helper methods for clean overlay and button management
	private static void openOverlay(JComponent overlay) {
		setGameButtonsEnabled(false);
		overlay.setLocation((view.getWidth() - overlay.getWidth()) / 2, (view.getHeight() - overlay.getHeight()) / 2);
		view.getLayeredPane().add(overlay, JLayeredPane.POPUP_LAYER);
		overlay.revalidate();
		view.repaint();
	}

	private static void closeOverlay(JComponent overlay) {
		view.getLayeredPane().remove(overlay);
		setGameButtonsEnabled(true);
		view.repaint();
	}

	private static void setGameButtonsEnabled(boolean enabled) {
		view.getMenuScreen().getSettingsButton().setEnabled(enabled);
		if (view.getGameScreen() != null) {
			view.getGameScreen().getBackButton().setEnabled(enabled);
			view.getGameScreen().getSettingsButton().setEnabled(enabled);
			view.getGameScreen().getHelpButton().setEnabled(enabled);
		}
	}

	static void exit() {
		System.exit(0);
	}

	public static AppFrame getView() {
		return view;
	}

	public static GameState getSavedGameState() {
		return savedGameState;
	}
}