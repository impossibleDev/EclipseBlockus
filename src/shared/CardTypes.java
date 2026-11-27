// Description: Defines the card types as enum, and method to use card based off given enum

package shared;

import model.Player;

public enum CardTypes {
	SKIP, EXPLODE, REGENERATE, DETACH;

	// player is the one using the card (needed by cards that change the player,
	// like REGENERATE)
	//Aiden
	public static void useCard(CardTypes cardType, Team[][] board, int x, int y, Player player) {
		switch (cardType) {

		case SKIP: //Melinda
			model.Card.useSkip(null);
			break;

		case EXPLODE: //Aiden
			model.Card.useExplosion(board, x, y);
			break;

		case REGENERATE: //Yanjun
			model.Card.useRegenerate(player);
			break;

		case DETACH: //Melinda
			player.setDetachActive(true);
			break;

		default:
			break;
		}
	}
}