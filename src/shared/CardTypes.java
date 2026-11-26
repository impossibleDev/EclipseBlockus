package shared;

//Aiden
public enum CardTypes {
	SKIP, EXPLODE, SPAWNBLOCK;


	public static void useCard(CardTypes cardType, Team[][] board, int x, int y) {
		switch (cardType) {

		case EXPLODE:
			useExplosion(board, x, y);
			break;

		default:
			break;
		}
	}

	//EXPLOSION ABILITY - Aiden
	//blast radius in a "circle" radius, random generation
	private final static int explosionRadius = 2;

	public static void useExplosion(Team[][] board, int x, int y) {
		int startX = Math.max(x - explosionRadius, 0);
		int endX = Math.min(x + explosionRadius, GameConstants.boardSize - 1);
		int startY = Math.max(y - explosionRadius, 0);
		int endY = Math.min(y + explosionRadius, GameConstants.boardSize - 1);

		for (int x2 = startX; x2 <= endX; x2++) {
			for (int y2 = startY; y2 <= endY; y2++) {
				if (( Math.abs(x2 - x) != explosionRadius || Math.abs(y2 - y) != explosionRadius) && Math.random()>0.3) {
					board[x2][y2] = Team.NULL;
				}
			}
		}
	}

}
