package mahjong;

/**
 * Keeps track of the amount of time that the game has been playing
 * for. Not able to record times over 59 minutes as it does not track
 * hours.
 */
public class TimerEntry {
	private static int seconds = 0;
	private static int minutes = 0;

	/**
	 * Resets the time to zero statically. Probably a bad design but
	 * changing it would involve fixing a lot of older code.
	 * @param inSec Starting seconds.
	 * @param inMin Starting minutes.
	 */
	public TimerEntry(int inSec, int inMin) {
		seconds = inSec;
		minutes = inMin;
	}

	/**
	 * Get the number of seconds that have passed
	 * since the game has started.
	 * @return Seconds as integer.
	 */
	public static int getSeconds() {
		return seconds;
	}

	/**
	 * Get the number of minutes that have passed since the game started.
	 * @return Minutes as integer.
	 */
	public static int getMinutes() {
		return minutes;
	}

	/**
	 * Increases the seconds until a minute has passed and then
	 * it counts it as a minute while resetting the seconds.
	 */
	public static void incrementTimer() {
		if (seconds == 59) {
			minutes++;
			seconds = 0;
		}
		else
			seconds++;
	}

	/**
	 * Reset the timer for a new game.
	 */
	public static void reset() {
		seconds = 0;
		minutes = 0;
	}
}
