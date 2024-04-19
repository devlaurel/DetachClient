package dev.laurel.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class RandomUtil {
    private final Random random = new Random();

    public float randomFloat(float min, float max) {
        return min + (max - min) * random.nextFloat();
    }
}
