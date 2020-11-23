package c.cmpt276.childapp.model.timerService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * The class that stops an alarm
 */
public class TimerBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TimerService.stopAlarm();
    }
}
