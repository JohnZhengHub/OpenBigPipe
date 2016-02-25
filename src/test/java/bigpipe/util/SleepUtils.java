package bigpipe.util;

/**
 * Created by lepdou on 15/4/19.
 */
public class SleepUtils {

    public static void sleep(long sec) {
        try {
            Thread.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
