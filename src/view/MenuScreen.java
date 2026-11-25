package view;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

// melinda 
public class MenuScreen extends Screen {
    // fields
    private JLabel title;
    private JPanel buttonPanel;
    private JButton settingsButton = GuiUtil.createButton(ViewConstants.settingsImage); //yanjun
    private JButton[] buttons = { GuiUtil.createButton("PLAY"), GuiUtil.createButton("HELP"), GuiUtil.createButton("QUIT") };
    // constructor
    public MenuScreen() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.GRAY);
        
        // yanjun
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        topBar.setOpaque(false);
        topBar.add(settingsButton);
        add(topBar, BorderLayout.NORTH);

        // center panel holds the title and the menu buttons
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);

        center.add(Box.createVerticalStrut(30));
        
        title = new JLabel("BLOKUS", SwingConstants.CENTER);
        title.setFont(GuiUtil.PIXELFONTBIG);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        
        center.add(Box.createVerticalStrut(40));
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        
        for (JButton button : buttons) {
            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(15));
        }
        
        center.add(buttonPanel);
        center.add(Box.createVerticalGlue());   // keeps the content near the top
        add(center, BorderLayout.CENTER);
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
//        super.resize();
    }
    
    //get ui elements
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