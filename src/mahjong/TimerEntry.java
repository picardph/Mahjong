package mahjong;

/**
 * Keeps track of the amount of time that the game has been playing
 * for. Not able to record times over 59 minutes as it does not track
 * hours.
 */
public final class TimerEntry {

    private static int seconds = 0;
    private static int minutes = 0;
    private static ITimeObserver observer = null;

    private static final int SECONDS = 60;

    private TimerEntry() {
    }

    /**
     * Resets the time to a preset value.
     * @param inSec Starting seconds.
     * @param inMin Starting minutes.
     */
    public static void set(final int inSec, final int inMin) {
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
        if (seconds == SECONDS - 1) {
            minutes++;
            seconds = 0;
        } else {
            seconds++;
        }
    }

    /**
     * Reset the timer for a new game.
     */
    public static void reset() {
        seconds = 0;
        minutes = 0;
    }

    /**
     * Set the observer that will be called whenever a second passes.
     * @param obs The new observer to call.
     */
    public static void setObserver(final ITimeObserver obs) {
        observer = obs;
    }

    /**
     * Called by the TimerHelper when a second passes so
     * that the observer is notified.
     */
    public static void change() {
        if (observer != null) {
            observer.second(minutes, seconds);
        }
    }
}
