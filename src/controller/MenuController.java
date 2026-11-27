/*
 * File: MenuController.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Melinda
 * Date: November 27, 2026
 * Description: Handles menu button actions and screen navigation.
 */

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Game;
import view.MenuScreen;
import view.SoundService;

// melinda
public class MenuController implements ActionListener {
	private Game model;
	private MenuScreen menuScreen;
	private AppController appController;

	// constructor
	public MenuController(MenuScreen menuScreen, AppController appController) {
		this.menuScreen = menuScreen;
		this.appController = appController;
		this.model = new Game();

		// connect controller to buttons
		menuScreen.setButtonListener(this);
		menuScreen.getSettingsButton().addActionListener(this);
		System.out.print("menu opened");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SoundService.playSound("click");
		if (e.getSource() == menuScreen.getSettingsButton()) {
			AppController.showSettingsOverlay();

		} else {
			String command = e.getActionCommand();
			if (command.equals("PLAY")) {
				AppController.showNewGameOverlay();
			} else if (command.equals("HELP")) {
				AppController.showHelpOverlay();
			} else if (command.equals("QUIT")) {
				AppController.exit();
			}
		}
	}
}