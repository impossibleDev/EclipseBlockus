// Description: Handles menu button actions and screen navigation.
// Contributors: Melinda

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.MenuScreen;
import view.SoundService;

// melinda
public class MenuController implements ActionListener {
	// Fields
	private MenuScreen menuScreen;

	// constructor
	public MenuController(MenuScreen menuScreen) {
		this.menuScreen = menuScreen;

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