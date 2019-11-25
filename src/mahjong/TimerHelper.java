package mahjong;

import java.util.TimerTask;
import java.util.Timer;

class TimerHelper extends TimerTask
{
	public void run()
	{
		TimerEntry.incrementTimer();
	}
}
