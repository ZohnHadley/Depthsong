package co.px.depthsong.engin.enginUtils;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralTimer {
    private static GeneralTimer instance;

    @Setter(AccessLevel.NONE)
    private  double  runTime;

    private double startTime;
    private boolean timIsUp = false;



    private GeneralTimer() {
        startTime = runTime;
    }
    public static  GeneralTimer getInstance(){
        if (instance == null){
            instance = new GeneralTimer();
        }
        return instance;
    }

    public void updateRuntime(float deltaTime){
        runTime+=deltaTime;
    }

    public boolean secondsHasPassed(float _seconds) {
        timIsUp = ((runTime - startTime) >= _seconds);
        return timIsUp;
    }
}
