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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int sec) {
		seconds = sec;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int min) {
		minutes = min;
	}

	public void incrementTimer() {
		if (seconds == 59) {
			minutes++;
			seconds = 0;
		}
		else
			seconds++;
	}
}
