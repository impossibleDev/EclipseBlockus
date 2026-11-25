package controller;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

import view.AppFrame;
import view.SoundService;
import view.overlay.HelpPanelOverlay;
import view.overlay.NewGameOverlay;
import view.overlay.SaveOverlay;
import view.overlay.SettingsOverlay;

public class AppController {

	private static AppFrame view = new AppFrame();
	private GameController gameController;
	private MenuController menuController;

	private static SettingsOverlay settingsOverlay;

	private void newGame() {
		gameController = new GameController(this, view.getGameScreen());
		menuController = new MenuController(view.getMenuScreen(), this);
	}

	// displays the new game settings panel before starting the game
	public static void showNewGameOverlay() {
		NewGameOverlay overlay = new NewGameOverlay();
		overlay.setSize(overlay.getPreferredSize());
		
		openOverlay(overlay);

		overlay.getCloseButton().addActionListener(e -> {
			closeOverlay(overlay);
			view.getMenuScreen().getSettingsButton().setEnabled(true);
		});

		overlay.getStartButton().addActionListener(e -> {
			closeOverlay(overlay);
			view.getMenuScreen().getSettingsButton().setEnabled(true);
			view.toGameScreen();
		});
	}

	// melinda
	// displays help overlay
	public static void showHelpOverlay() {
		HelpPanelOverlay overlay = new HelpPanelOverlay();
		overlay.setSize(700, 500);
		
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
			settingsOverlay.setSize(640, 440);

			// music toggle
			settingsOverlay.getMusicButton().addItemListener(e ->
				SoundService.setMusicOn(settingsOverlay.getMusicButton().isSelected()));
			// sfx toggle
			settingsOverlay.getSfxButton().addItemListener(e ->
				SoundService.setSfxOn(settingsOverlay.getSfxButton().isSelected()));
			// close overlay
			settingsOverlay.getCloseButton().addActionListener(e -> closeOverlay(settingsOverlay));
		}
		openOverlay(settingsOverlay);
	}
	
	// melinda
	// Displays the save game overlay when back is clicked
	public static void showSaveOverlay() {
		SaveOverlay overlay = new SaveOverlay();
		overlay.setSize(440, 210);
		
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
			// TODO save feature
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

	public AppController() {
	    SoundService.loadSound("bgm", "resources/audio/BackgroundMusic.wav");
	    SoundService.loadSound("click", "resources/audio/Click.wav");
	    SoundService.loadSound("place", "resources/audio/PlacePiece.wav");

	    // start background music
	    SoundService.playMusic("bgm");

	    newGame();
	    view.toMenuScreen();
	}

	public static AppFrame getView() {
		return view;
	}
}