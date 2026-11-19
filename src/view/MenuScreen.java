package view;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

// melinda
public class MenuScreen extends Screen {
    // fields
    private JLabel title;
    private JPanel buttonPanel;
    private JButton[] buttons = { GuiUtil.createButton("PLAY"), GuiUtil.createButton("HELP"), GuiUtil.createButton("QUIT") };
    // constructor
    public MenuScreen() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.GRAY);
        
        add(Box.createVerticalStrut(100));
        
        title = new JLabel("BLOKUS", SwingConstants.CENTER);
        title.setFont(GuiUtil.PIXELFONTBIG);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title);
        
        add(Box.createVerticalStrut(40));
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        
        for (JButton button : buttons) {
            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(15));
        }
        
        add(buttonPanel);
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
}

