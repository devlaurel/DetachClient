package dev.laurel.util;

import lombok.experimental.UtilityClass;
import net.minecraft.util.Vec3;

@UtilityClass
public class RotationUtil {

    public float[] getRotationToVec3(Vec3 from, Vec3 to) {
        double d0 = to.xCoord - from.xCoord;
        double d1 = to.yCoord - from.yCoord;
        double d2 = to.zCoord - from.zCoord;
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        float f = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
        return new float[]{f, f1};
    }
}
