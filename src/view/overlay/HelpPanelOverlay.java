package view.overlay;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import view.GuiUtil;

//yanjun
public class HelpPanelOverlay extends Overlay {

    private final JButton closeButton = new JButton("X");

    public HelpPanelOverlay() {
    	//Panel appearance
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        setPreferredSize(new Dimension(700, 500));
        
        //Panel title
        JLabel title = new JLabel("BLOKUS", SwingConstants.CENTER);
        title.setFont(GuiUtil.PIXELFONTBIG); //using font
        
        //Close button
        closeButton.setFont(GuiUtil.PIXELFONTMEDIUM); //using font
        
        //Top bar holds the title (center) and close button (right)
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        //empty border adds space above and below so the title is not cramped
        top.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10)); 
        top.add(title, BorderLayout.CENTER);
        top.add(closeButton, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);
        
        //Content(JTextPane)
        JTextPane text = new JTextPane();
        text.setText(loadHelpText());
        text.setFont(GuiUtil.PIXELFONTSMALL); //using font
        text.setEditable(false);
        text.setForeground(new Color(74, 48, 32)); //dark brown text color
        text.setBackground(Color.WHITE);
        text.setMargin(new java.awt.Insets(8, 8, 8, 8));
        
        //increase the space between lines so the text is easier to read
        StyledDocument doc = text.getStyledDocument();
        SimpleAttributeSet spacing = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(spacing, 0.5f);   
        doc.setParagraphAttributes(0, doc.getLength(), spacing, false);
        text.setCaretPosition(0);  
        
        //Scroll bar
        JScrollPane scroll = new JScrollPane(text);
        add(scroll, BorderLayout.CENTER);
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    //Instruction on the Help Panel
    private String loadHelpText() {
        return """
   How to Play
   
   BLOKUS is a 4-player game where the ultimate objective is to have as many blocks on the board as possible. Each player chooses a colour that corresponds to their blocks.
   Each player’s first block must touch the corner of the board. Each block after must touch another block of the same colour at the corners only. Blocks of different colours can touch at the edges freely. While dragging a block, press R to rotate it or F to flip it. The winner is determined by whichever player has the fewest leftover squares in unused blocks after no more blocks can be placed.
   
   
   FAQ
   
   Q: What happens if I have no legal moves left?
   A: You’re skipped for the rest of the game, but play continues for the remaining players.
   
   Q: Can I pass my turn voluntarily?
   A: No, you can only be skipped when you truly have no legal moves left.
   
   Q: How is my final score calculated?
   A: Your score is based on the number of squares left in your unused blocks. Fewer leftover squares means a higher score.
   
   Q: When does the game officially end?
   A: When none of the remaining players can place any more blocks.
   
   
   Contact Us!
   
   349722660@gapps.yrdsb.ca
   350777603@gapps.yrdsb.ca
   440016206@gapps.yrdsb.ca
   """;
    }
}
