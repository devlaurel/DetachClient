package dev.laurel.util.math;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class MathUtil {

    public double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }
}
