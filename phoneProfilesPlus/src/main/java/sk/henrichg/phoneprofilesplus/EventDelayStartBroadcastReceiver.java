package sk.henrichg.phoneprofilesplus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EventDelayStartBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PPApplication.logE("##### EventDelayStartBroadcastReceiver.onReceive", "xxx");

        CallsCounter.logCounter(context, "EventDelayStartBroadcastReceiver.onReceive", "EventDelayStartBroadcastReceiver_onReceive");

        Context appContext = context.getApplicationContext();

        if (!PPApplication.getApplicationStarted(appContext, true))
            // application is not started
            return;

        if (Event.getGlobalEventsRunning(appContext))
        {
            PPApplication.logE("@@@ EventDelayStartBroadcastReceiver.onReceive","xxx");

            // start job
            EventsHandlerJob.startForSensor(appContext, EventsHandler.SENSOR_TYPE_EVENT_DELAY_START);
        }

    }

}
