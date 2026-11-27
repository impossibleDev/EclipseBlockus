/*
 * File: CardTypes.java
 * Project: SDP #1 - BLOKUS
 * Course: [Course code, section, and instructor]
 * Group: 5 (Aiden, Yanjun, Melinda)
 * Contributors: Not explicitly documented in source
 * Date: November 27, 2026
 * Description: Defines the available special card types.
 */

package shared;

import model.Card;
import model.Player;

public enum CardTypes {
	SKIP, EXPLODE, REGENERATE, DETACH;

	// player is the one using the card (needed by cards that change the player,
	// like REGENERATE)
	public static void useCard(CardTypes cardType, Team[][] board, int x, int y, Player player) {
		switch (cardType) {

		case SKIP:
			model.Card.useSkip(null);
			break;

		case EXPLODE:
			model.Card.useExplosion(board, x, y);
			break;

		case REGENERATE:
			model.Card.useRegenerate(player);
			break;

		case DETACH:
			player.setDetachActive(true);
			break;

		default:
			break;
		}
	}
}