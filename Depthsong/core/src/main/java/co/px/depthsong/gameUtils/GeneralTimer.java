package co.px.depthsong.gameUtils;



public class GeneralTimer {
    public static float  runTime = 0;

    float startTime;
    boolean timIsUp = false;

    public GeneralTimer() {
        startTime = runTime;
    }

    public boolean secondsHasPassed(float _seconds) {
        timIsUp = ((runTime - startTime) >= _seconds);
        return timIsUp;
    }
}
