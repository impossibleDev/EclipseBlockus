package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MenuScreen;

public class MenuController implements ActionListener {
	private AppController appController;
    private MenuScreen menuScreen;

    public MenuController(AppController appController, MenuScreen menuScreen){
    	this.appController = appController;
        this.menuScreen = menuScreen;
//        gameScreen.update(model.getBoard());
        menuScreen.getPlayButton().addActionListener(this);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == menuScreen.getPlayButton()) {
			//Play button Pressed
			appController.toGameScreen();
		}
		
	}
}
