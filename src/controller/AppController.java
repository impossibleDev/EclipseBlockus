package controller;

import view.AppFrame;

public class AppController {
	private AppFrame view = new AppFrame();
    private GameController gameController;
    private MenuController menuController;

    private void newGame(){
        gameController = new GameController(this, view.getGameScreen());
        
        //new GameController(settings1, settings2, etc);
        // input paramters of settings stuff
    }
    private void exit(){
        //close window
    }
    public AppController(){
    	menuController = new MenuController(this, view.getMenuScreen());
        
        view.toMenuScreen();
        //test code here for now
        newGame();
    }
    
    public AppFrame getView() {
    	return view;
    }
    
    public void toGameScreen() {
    	view.toGameScreen();
    }
    
    public void toMenuScreen() {
    	view.toMenuScreen();
    }
}
