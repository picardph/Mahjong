package mahjong;

import java.util.TimerTask;
import java.util.Timer;

/**
 * Used to implement the observer pattern by having the
 * timer notify us when a second has passed rather than
 * pulling.
 */
class TimerHelper extends TimerTask
{
	/**
	 * Will call our timer class and tell it
	 * that a second has passed. This method
	 * is called internally by Java.
	 */
	public void run()
	{
		TimerEntry.incrementTimer();
	}
}
