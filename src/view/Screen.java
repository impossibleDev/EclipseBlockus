// Description: Provides shared behavior for application screens.

package view;

import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel {
	
	//Constructor
    public Screen(){
        setBackground(Color.DARK_GRAY);
        setOpaque(true);
    }
    
    public void resize(){
        //Resize this panel to exactly match the size of parent AppFrame.
        setSize(getParent().getSize());
    };
}
