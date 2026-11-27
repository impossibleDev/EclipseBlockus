/*
 * File: Keybinds.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Yanjun
 * Date: November 27, 2026
 * Description: Creates keyboard shortcuts for gameplay actions.
 */

package controller;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

// yanjun
// Binds the R and F keys to rotate and flip the selected piece
public class Keybinds {

	public Keybinds(JComponent target, GameController gameController) {
		// WHEN_IN_FOCUSED_WINDOW means the keys work no matter which component has focus
		target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("R"), "rotate");
		target.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F"), "flip");

		target.getActionMap().put("rotate", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.rotatePreview();
			}
		});

		target.getActionMap().put("flip", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.flipPreview();
			}
		});
	}
}