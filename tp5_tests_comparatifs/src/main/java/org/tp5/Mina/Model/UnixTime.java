package org.tp5.Mina.Model;

import java.util.Date;

public class UnixTime {
    private final long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return "{UnixTime : value{" + new Date((value() - 2208988800L) * 1000L) + "}}";
    }
}
