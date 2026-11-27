// Description: Displays the main menu and its navigation buttons.
// Contributors: Aiden, Melinda, Yanjun

package view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

// melinda
public class MenuScreen extends Screen {
	
	// ImageIcon resources
	public static final ImageIcon titleImg = new ImageIcon("resources/ui/BlokusTitle.gif");
	public static final ImageIcon bgImageIcon = new ImageIcon("resources/bg/gameBg.png");
	public static final ImageIcon playButtonImg = new ImageIcon("resources/ui/MenuPlayButton.png");
	public static final ImageIcon playButtonHoverImg = new ImageIcon("resources/ui/MenuPlayButtonHover.png");
	public static final ImageIcon helpButtonImg = new ImageIcon("resources/ui/MenuHelpButton.png");
	public static final ImageIcon helpButtonHoverImg = new ImageIcon("resources/ui/MenuHelpButtonHover.png");
	public static final ImageIcon quitButtonImg = new ImageIcon("resources/ui/MenuQuitButton.png");
	public static final ImageIcon quitButtonHoverImg = new ImageIcon("resources/ui/MenuQuitButtonHover.png");
	
	// UI elements
	private JLabel bgLabel = new JLabel(bgImageIcon);
	private JLabel title = new JLabel(titleImg);
	private JPanel buttonPanel;
	private JButton settingsButton = GuiUtil.createButton(ViewConstants.settingsImage); // yanjun
	private JButton[] buttons = { GuiUtil.createButton(playButtonImg, playButtonHoverImg), 
			GuiUtil.createButton(helpButtonImg, helpButtonHoverImg),
			GuiUtil.createButton(quitButtonImg, quitButtonHoverImg), };

	// constructor
	public MenuScreen() {
		
		
		// Use BorderLayout so the background image label fills the entire screen panel
		setLayout(new BorderLayout());

		// Set layout on bgLabel so we can stack UI elements on top of the image
		bgLabel.setLayout(new BoxLayout(bgLabel, BoxLayout.Y_AXIS));

		// yanjun
		JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		topBar.setOpaque(false);
		topBar.add(settingsButton);
		bgLabel.add(topBar);

		// center panel holds the title and the menu buttons
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		center.setOpaque(false);

//		center.add(Box.createVerticalStrut(30));

		center.add(title); // Added directly to center panel instead of background

//		center.add(Box.createVerticalStrut(40));

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setOpaque(false);

		// Aiden
		buttons[0].setActionCommand("PLAY");
		buttons[1].setActionCommand("HELP");
		
		buttons[2].setActionCommand("QUIT");
		for (JButton button : buttons) {
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			buttonPanel.add(button);
			buttonPanel.add(Box.createVerticalStrut(10));
		}

		center.add(buttonPanel);
		center.add(Box.createVerticalGlue()); // keeps the content near the top

		bgLabel.add(center);

		// Add the background label container to the main screen panel
		add(bgLabel, BorderLayout.CENTER);
	}

	// connect controller to buttons
	public void setButtonListener(ActionListener listener) {
		for (JButton button : buttons) {
			button.addActionListener(listener);
		}
	}

	@Override
	public void resize() {
		setPreferredSize(getParent().getSize());
		revalidate();
		repaint();
	}

	// Getters
	public JButton getPlayButton() {
		return buttons[0];
	}

	public JButton getHelpButton() {
		return buttons[1];
	}

	public JButton getSettingsButton() {
		return settingsButton;
	}

}