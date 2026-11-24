package view;


import view.overlay.*;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

//Aiden
public class AppFrame extends JFrame {
	
	//OVERLAY
	private Overlay currentOverlay;
	private HelpPanelOverlay helpPanelOverlay = new HelpPanelOverlay();
    private final JLayeredPane layeredPane = new JLayeredPane();
    //SCREENS
    private GameScreen gameScreen = new GameScreen();
    private MenuScreen menuScreen = new MenuScreen();

    //CURRENTSCREEN
    private Screen screen;

    public AppFrame(){
        super("Blockus");

        layeredPane.setLayout(null);
        setContentPane(layeredPane);
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                layoutComponents();
            }
        });

        setVisible(true);
    }


    public void toGameScreen(){
        setScreen(gameScreen);

    }

    public void toMenuScreen(){
        setScreen(menuScreen);
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }
    public MenuScreen getMenuScreen() {
        return menuScreen;
    }


    private void setScreen(Screen screen) {
        if (this.screen != null) {
            layeredPane.remove(this.screen);
        }
        this.screen = screen;
        screen.setVisible(true);
        layeredPane.add(screen, JLayeredPane.DEFAULT_LAYER);
        layoutComponents();
        revalidate();
        screen.repaint();
    }
    
    public void setOverlayHelpPanel() {
    	setOverlay(helpPanelOverlay);
//    	setOverlay(new SettingsOverlay());
    }
    private void setOverlay(Overlay overlay){
        removeOverlay();
        currentOverlay = overlay;
        layeredPane.add(overlay, JLayeredPane.PALETTE_LAYER);
        layoutComponents();
        revalidate();
        repaint();
    }

    public void removeOverlay(){
        if (currentOverlay!=null){
            layeredPane.remove(currentOverlay);
            currentOverlay = null;
            revalidate();
            repaint();
        }
    }

    private void layoutComponents() {
        int width = layeredPane.getWidth();
        int height = layeredPane.getHeight();

        if (screen != null) {
            screen.setBounds(0, 0, width, height);
            screen.resize();
        }

        if (currentOverlay != null) {
            Dimension size = currentOverlay.getPreferredSize();
            currentOverlay.setBounds(
                    (width - size.width) / 2,
                    (height - size.height) / 2,
                    size.width,
                    size.height
            );
        }
    }

}
