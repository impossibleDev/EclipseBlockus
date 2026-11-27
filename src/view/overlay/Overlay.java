/*
 * File: Overlay.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Aiden
 * Date: November 27, 2026
 * Description: Provides shared structure and behavior for modal overlays.
 */

package view.overlay;

import java.awt.Dimension;

import javax.swing.*;

import view.GuiUtil;
import view.ViewConstants;

//Aiden
public abstract class Overlay extends JLabel {

	// UI Elements
	protected JButton closeButton = GuiUtil.createButton(ViewConstants.CloseButtonImage,
			ViewConstants.CloseButtonHoverImage);

	// Constructors
	public Overlay() {
		setOpaque(false);
		setSize(700, 500);
		setPreferredSize(new Dimension(700, 500));

		closeButton.setSize(45, 45);
		add(closeButton);
	}

	public Overlay(ImageIcon bgImg) {
		super(bgImg);
		setOpaque(false);
		setSize(700, 500);

		closeButton.setSize(45, 45);
		add(closeButton);
	}

	// Getters for UI elements
	public JButton getCloseButton() {
		return closeButton;
	}
}