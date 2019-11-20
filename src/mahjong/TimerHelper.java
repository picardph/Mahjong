package mahjong;

import java.util.TimerTask;
import java.util.Timer;

class TimerHelper extends TimerTask
{
	private TimerEntry t = new TimerEntry();
	public void run()
	{
		t.incrementTimer();
	}
}
