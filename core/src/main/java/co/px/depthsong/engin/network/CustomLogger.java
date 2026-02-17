package co.px.depthsong.engin.network;

import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumActivationState;

public class CustomLogger {
    public static EnumActivationState isLogDebugging = EnumActivationState.ON;

    public static void log(String message) {
        if (isLogDebugging == EnumActivationState.OFF) {
            return;
        }
        System.out.println(PrintColors.ANSI_YELLOW.getValue() + message + PrintColors.ANSI_RESET.getValue());

    }

    public static void log(String executer, String message) {
        if (isLogDebugging == EnumActivationState.OFF) {
            return;
        }
        System.out.println(PrintColors.ANSI_BLUE.getValue() + "("+executer+") : " + message + PrintColors.ANSI_RESET.getValue());

    }

    public static void log(PrintColors color, String message) {
        if (isLogDebugging == EnumActivationState.OFF) {
            return;
        }
        System.out.println(color.getValue() + message + PrintColors.ANSI_RESET.getValue());

    }

    public static void log(PrintColors color, String executer, String message) {
        if (isLogDebugging == EnumActivationState.OFF) {
            return;
        }
        System.out.println(color.getValue() + "("+executer+") : " + message + PrintColors.ANSI_RESET.getValue());

    }

    public static void err(String message) {
        if (isLogDebugging == EnumActivationState.OFF) {
            return;
        }

        System.err.println("*** ERROR : "+message);
    }

    public static void err(String executer, String message) {
        if (isLogDebugging == EnumActivationState.OFF) {
            return;
        }

        System.err.println("("+executer+") ERROR : " + message);
    }
}
