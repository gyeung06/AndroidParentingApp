//package c.cmpt276.childapp.model.BreathEvent;
//
//import android.os.SystemClock;
//
////class to process the pressed time
//public class TimePressed {
//
//    private static final long LONG_PRESS_TIME_MS_3 = 3000;
//    private static final long LONG_PRESS_TIME_MS_10 = 10000;
//
//
//    private boolean btnDown = false;
//    private long btnDuration = -1;
//
//    synchronized void buttonDown() {
//        btnDown = true;
//        btnDuration = SystemClock.elapsedRealtime();
//    }
//
//    synchronized void buttonUp() {
//        btnDuration = SystemClock.elapsedRealtime() - btnDuration;
//        btnDown = false;
//    }
//
//    //check the time of button pressed
//    synchronized boolean isLessThanThree() {
//        return btnDuration < LONG_PRESS_TIME_MS_3;
//    }
//
//    synchronized boolean isTreeToTenPressed() {
//        return (btnDuration >= LONG_PRESS_TIME_MS_3) && (btnDuration < LONG_PRESS_TIME_MS_10);
//    }
//
//    synchronized boolean isGreaterThanTenPressed() {
//        return btnDuration >= LONG_PRESS_TIME_MS_10;
//    }
//}
