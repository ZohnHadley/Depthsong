package co.px.depthsong.network.Local.Model;

public class CurrentTurnTimeObject {

    private int seconds;

    public CurrentTurnTimeObject() {
    }

    public CurrentTurnTimeObject(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String toString() {
        return "CurrentTurnTimeObject(" +
                "seconds=" + seconds +
                ')';
    }
}
