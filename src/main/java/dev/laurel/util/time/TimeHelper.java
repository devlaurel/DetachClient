package dev.laurel.util.time;

public final class TimeHelper {
    private static long lastTime = 0;

    public void reset() {
        lastTime = System.currentTimeMillis();
    }

    public boolean hasPassed(long millis) {
        return System.currentTimeMillis() - lastTime >= millis;
    }
}
