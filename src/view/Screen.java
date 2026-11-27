/*
 * File: Screen.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Not explicitly documented in source
 * Date: November 27, 2026
 * Description: Provides shared behavior for application screens.
 */

package view;

import view.overlay.Overlay;

import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JPanel {
//    private Overlay currentOverlay;
    public Screen(){
//        super(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        setOpaque(true);
    }

//    public void setOverlay(Overlay overlay){
//        removeOverlay();
//        currentOverlay = overlay;
//        add(overlay, BorderLayout.CENTER);
//        setComponentZOrder(overlay, 0);
//        revalidate();
//        repaint();
//    }
//
//    public void removeOverlay(){
//        if (currentOverlay!=null){
//            remove(currentOverlay);
//        }
//    }
    public void resize(){
        //TODO make it resize the same size as AppFrame size
        setSize(getParent().getSize());
    };
}
