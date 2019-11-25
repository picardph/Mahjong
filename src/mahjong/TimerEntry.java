package mahjong;

public class TimerEntry {

	private static String name;
	private static int seconds;
	private static int minutes;

	public TimerEntry() {
		seconds = getSeconds();
		minutes = getMinutes();
		name = getName();
	}

	public TimerEntry(int inSec, int inMin) {
		name = "";
		seconds = inSec;
		minutes = inMin;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		TimerEntry.name = name;
	}

	public static int getSeconds() {
		return seconds;
	}

	public static void setSeconds(int sec) {
		seconds = sec;
	}

	public static int getMinutes() {
		return minutes;
	}

	public static void setMinutes(int min) {
		minutes = min;
	}

	public static void incrementTimer() {
		if (seconds == 59) {
			minutes++;
			seconds = 0;
		}
		else
			seconds++;
	}
}
