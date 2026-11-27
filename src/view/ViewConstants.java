/*
 * File: ViewConstants.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Aiden
 * Date: November 27, 2026
 * Description: Stores shared UI colours, dimensions, fonts, and images.
 */

package view;

import shared.CardTypes;
import shared.Team;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

//Aiden
public class ViewConstants {

	// enumMaps
	public static final EnumMap<Team, Color> teamColors = new EnumMap<>(Team.class);
	public static final EnumMap<CardTypes, ImageIcon> cardImages = new EnumMap<>(CardTypes.class);

	// numbers
	public static final int blockSize = 20; // or mabye blockSize is dynmaic to JPanle size?
	public static final int blockPadding = 2;

	// imageIcons
	public static final ImageIcon settingsImage = new ImageIcon("resources/ui/SettingsButton.png");
	public static final ImageIcon helpImage = new ImageIcon("resources/ui/HelpButton.png");
	public static final ImageIcon backImage = new ImageIcon("resources/ui/BackButton.png");
	public static final ImageIcon CloseButtonImage = new ImageIcon("resources/ui/CloseButton.png");
	public static final ImageIcon CloseButtonHoverImage = new ImageIcon("resources/ui/CloseButtonHover.png");

	// automatically runs
	static {

		// TEAM COLORS
		teamColors.put(Team.NULL, Color.GRAY);
		teamColors.put(Team.YELLOW, Color.YELLOW);
		teamColors.put(Team.RED, Color.RED);
		teamColors.put(Team.GREEN, Color.GREEN);
		teamColors.put(Team.BLUE, Color.BLUE);

		// CARD IMAGES
		cardImages.put(CardTypes.SKIP, new ImageIcon("resources/cards/skip.png"));
		cardImages.put(CardTypes.EXPLODE, new ImageIcon("resources/cards/explode.png"));
		cardImages.put(CardTypes.REGENERATE, new ImageIcon("resources/cards/spawnBlock.png"));
		cardImages.put(CardTypes.DETACH, new ImageIcon("resources/cards/detach.png"));

	}

}