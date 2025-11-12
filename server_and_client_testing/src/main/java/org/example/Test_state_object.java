package org.example;

import java.util.HashMap;
import java.util.Objects;

public class Test_state_object {
    private static boolean moving_up = false;
    private static boolean moving_down = false;
    private static boolean moving_left = false;
    private static boolean moving_right = false;


    public Byte toByte () {
        byte b = 0;
        if (moving_up) {
            b |= 1 << 0;
        }
        if (moving_down) {
            b |= 1 << 1;
        }
        if (moving_left) {
            b |= 1 << 2;
        }
        if (moving_right) {
            b |= 1 << 3;
        }
        return b;
    }

    public HashMap fromByte (byte b) {
        moving_up = (b & 1 << 0) != 0;
        moving_down = (b & 1 << 1) != 0;
        moving_left = (b & 1 << 2) != 0;
        moving_right = (b & 1 << 3) != 0;

        HashMap<String, Boolean> map = new HashMap<>();
        map.put("moving_up", moving_up);
        map.put("moving_down", moving_down);
        map.put("moving_left", moving_left);
        map.put("moving_right", moving_right);
        return map;
    }

}
