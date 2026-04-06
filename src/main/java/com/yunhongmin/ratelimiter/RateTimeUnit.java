package com.yunhongmin.ratelimiter;

public enum RateTimeUnit {
    SECONDS,
    MINUTES,
    HOURS,
    DAYS;

    public long toSeconds() {
        if (this.equals(SECONDS)) {
            return 1L;
        } else if (this.equals(MINUTES)) {
            return 60L;
        } else if (this.equals(HOURS)) {
            return 3600L;
        } else if (this.equals(DAYS)) {
            return 86400L;
        } else {
            throw new IllegalStateException("Invalid RateTimeUnit");
        }
    }
}
