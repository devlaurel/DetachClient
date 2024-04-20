package dev.laurel.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class RandomUtil {
    private final Random random = new Random();

    public double randomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}
