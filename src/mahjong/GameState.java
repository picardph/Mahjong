package mahjong;

/**
 * Represents the different possible states
 * of the game board. Mahjong is unique in that
 * a board can be in an unbeatable state but the game
 * can still be won via shuffling. This means that a simple
 * boolean is not enough to track the state of the game.
 */
public enum GameState {
    /** When there is no game loaded. */
    NoGame,
    /** When the game is currently being played and there are still tiles to remove. */
    InProgress,
    /** When all tiles have been removed. */
    Won,
    /** When there are no more tiles to remove and the user is out of shuffles. */
    Lost,
    /** When there are no more tiles to remove but the user has shuffles left. */
    Stuck
}
