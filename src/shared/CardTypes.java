package shared;

import model.Player;

//Aiden
public enum CardTypes {
	SKIP, EXPLODE, REGENERATE, DETACH;

	// player is the one using the card (needed by cards that change the player, like REGENERATE)
	public static void useCard(CardTypes cardType, Team[][] board, int x, int y, Player player) {
		switch (cardType) {

		case EXPLODE:
			useExplosion(board, x, y, player);
			break;

		case REGENERATE:
			useRegenerate(player);
			break;

		default:
			break;
		}
	}

	// REGENERATE ABILITY - gives the player an extra 1x1 block right away
	// yanjun
	public static void useRegenerate(Player player) {
		player.addBonusOneByOne();
	}

	//EXPLOSION ABILITY - Aiden
	//blast radius in a "circle" radius, random generation
	private final static int explosionRadius = 2;

	public static void useExplosion(Team[][] board, int x, int y, Player player) {
		int startX = Math.max(x - explosionRadius, 0);
		int endX = Math.min(x + explosionRadius, GameConstants.boardSize - 1);
		int startY = Math.max(y - explosionRadius, 0);
		int endY = Math.min(y + explosionRadius, GameConstants.boardSize - 1);

		Team myTeam = player.getTeam();

		for (int x2 = startX; x2 <= endX; x2++) {
			for (int y2 = startY; y2 <= endY; y2++) {
				if (board[x2][y2] != Team.NULL && board[x2][y2] != myTeam) {
					if ((Math.abs(x2 - x) != explosionRadius || Math.abs(y2 - y) != explosionRadius)
							&& Math.random() > 0.3) {
						board[x2][y2] = Team.NULL;
					}
				}
			}
		}
	}

}