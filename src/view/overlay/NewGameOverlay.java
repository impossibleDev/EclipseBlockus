package view.overlay;

import java.awt.*;
import javax.swing.*;

import view.GuiUtil;

// melinda
public class NewGameOverlay extends Overlay {

	// fields
	private JLabel title;
	private JPanel settingsPanel;
	private JButton startButton;
	private JButton closeButton;

	private JLabel[] settingLabels = { new JLabel("# CPUS"), new JLabel("# Flips"), new JLabel("Time Limit") };

	private JComboBox<Integer>[] settingBox = new JComboBox[3];

	// constructor
	public NewGameOverlay() {
		setSize(500, 450);
		setPreferredSize(new Dimension(500, 450));
		setLayout(new BorderLayout(15, 15));
		setOpaque(true);
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

		// title and close button
		title = new JLabel("NEW GAME", SwingConstants.CENTER);
		title.setFont(GuiUtil.PIXELFONTBIG);

		closeButton = new JButton("X");
		closeButton.setPreferredSize(new Dimension(40, 40));
		styleButton(closeButton);

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setOpaque(false);
		topPanel.add(title, BorderLayout.CENTER);
		topPanel.add(closeButton, BorderLayout.EAST);
		topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		add(topPanel, BorderLayout.NORTH);

		// settings panel
		settingsPanel = new JPanel(new GridLayout(3, 2, 10, 25));
		settingsPanel.setOpaque(false);
		settingsPanel.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));

		// combo boxes
		settingBox[0] = new JComboBox<>(new Integer[] { 1, 2, 3 });
		settingBox[1] = new JComboBox<>(new Integer[] { 10, 25, 50 });
		settingBox[2] = new JComboBox<>(new Integer[] { 10, 30, 60 });

		for (int i = 0; i < settingBox.length; i++) {

			settingLabels[i].setFont(GuiUtil.PIXELFONTSMALL);
			settingLabels[i].setForeground(Color.BLACK);
			settingLabels[i].setOpaque(false);

			settingBox[i].setFont(GuiUtil.PIXELFONTSMALL);
			settingBox[i].setPreferredSize(new Dimension(100, 35));
			settingBox[i].setBackground(Color.WHITE);
			settingBox[i].setForeground(Color.BLACK);

			settingsPanel.add(settingLabels[i]);
			settingsPanel.add(settingBox[i]);
		}

		add(settingsPanel, BorderLayout.CENTER);

		// play button
		startButton = new JButton("PLAY");
		startButton.setFont(GuiUtil.PIXELFONTMEDIUM);
		startButton.setPreferredSize(new Dimension(160, 45));
		styleButton(startButton);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		bottomPanel.add(startButton);

		add(bottomPanel, BorderLayout.SOUTH);

	}

	private void styleButton(JButton button) {
		button.setFont(GuiUtil.PIXELFONTSMALL);
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		button.setFocusPainted(false);
		button.setOpaque(true);

	}

	// getter and setter methods
	public JButton getStartButton() {
		return startButton;
	}

	public JButton getCloseButton() {
		return closeButton;
	}

	public JComboBox<Integer> getCpuSelector() {
		return settingBox[0];
	}

	public JComboBox<Integer> getFlipSelector() {
		return settingBox[1];
	}

	public JComboBox<Integer> getTimeLimitSelector() {
		return settingBox[2];
	}

}