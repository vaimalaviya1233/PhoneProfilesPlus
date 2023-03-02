package sk.henrichg.phoneprofilesplus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EventCalendarBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        PPApplicationStatic.logE("[IN_BROADCAST] EventCalendarBroadcastReceiver.onReceive", "xxx");
//        PPApplicationStatic.logE("[IN_BROADCAST_ALARM] EventCalendarBroadcastReceiver.onReceive", "xxx");

        String action = intent.getAction();
        if (action != null) {
            doWork(/*true,*/ context);
        }
    }

    private void doWork(/*boolean useHandler,*/ Context context) {
        if (!PPApplicationStatic.getApplicationStarted(true, true))
            // application is not started
            return;

        if (EventStatic.getGlobalEventsRunning(context)) {
            //if (useHandler) {
            final Context appContext = context.getApplicationContext();
            PPExecutors.handleEvents(appContext, EventsHandler.SENSOR_TYPE_CALENDAR, "SENSOR_TYPE_CALENDAR", 0);
            /*
            PPApplication.startHandlerThreadBroadcast();
            final Handler __handler = new Handler(PPApplication.handlerThreadBroadcast.getLooper());
            //__handler.post(new PPApplication.PPHandlerThreadRunnable(
            //        context.getApplicationContext()) {
            __handler.post(() -> {
//                    PPApplicationStatic.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", "START run - from=EventCalendarBroadcastReceiver.doWork");

                //Context appContext= appContextWeakRef.get();
                //if (appContext != null) {
                    PowerManager powerManager = (PowerManager) appContext.getSystemService(Context.POWER_SERVICE);
                    PowerManager.WakeLock wakeLock = null;
                    try {
                        if (powerManager != null) {
                            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, PPApplication.PACKAGE_NAME + ":EventCalendarBroadcastReceiver_doWork");
                            wakeLock.acquire(10 * 60 * 1000);
                        }

//                        PPApplicationStatic.logE("[EVENTS_HANDLER_CALL] EventCalendarBroadcastReceiver.doWork", "sensorType=SENSOR_TYPE_CALENDAR");
                        EventsHandler eventsHandler = new EventsHandler(appContext);
                        eventsHandler.handleEvents(EventsHandler.SENSOR_TYPE_CALENDAR);

                    } catch (Exception e) {
//                        PPApplicationStatic.logE("[IN_THREAD_HANDLER] PPApplication.startHandlerThread", Log.getStackTraceString(e));
                        PPApplicationStatic.recordException(e);
                    } finally {
                        if ((wakeLock != null) && wakeLock.isHeld()) {
                            try {
                                wakeLock.release();
                            } catch (Exception ignored) {
                            }
                        }
                    }
                //}
            });
            */
            /*}
            else {
                if (Event.getGlobalEventsRunning(appContext)) {
                    EventsHandler eventsHandler = new EventsHandler(appContext);
                    eventsHandler.handleEvents(EventsHandler.SENSOR_TYPE_CALENDAR);
                }
            }*/
        }
    }

}
