package mahjong;

/**
 * Will have its second method called whenever a second passes.
 */
public interface ITimeObserver {
	/**
	 * Called by the timer whenever a second passes.
	 * @param minutes The minutes that have passed so far.
	 * @param seconds The seconds that have passed so far.
	 */
	void second(int minutes, int seconds);
}
