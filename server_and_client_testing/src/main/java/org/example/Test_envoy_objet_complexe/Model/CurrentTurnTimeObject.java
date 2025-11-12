package org.example.Test_envoy_objet_complexe.Model;

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
