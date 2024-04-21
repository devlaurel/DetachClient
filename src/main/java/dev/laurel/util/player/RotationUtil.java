package dev.laurel.util.player;

import dev.laurel.util.math.MathUtil;
import lombok.experimental.UtilityClass;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import static dev.laurel.client.IMinecraft.mc;

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

    public float[] getClosestHitVec3Rotation(Vec3 from, AxisAlignedBB to) {
        double x = MathUtil.clamp(from.xCoord, to.minX, to.maxX);
        double y = MathUtil.clamp(from.yCoord, to.minY, to.maxY);
        double z = MathUtil.clamp(from.zCoord, to.minZ, to.maxZ);

        final Vec3 closestHitVec3 = new Vec3(x, y, z);
        return getRotationToVec3(from, closestHitVec3);
    }

    public Vec3 getClosestHitVec3(Vec3 from, AxisAlignedBB to) {
        double x = MathUtil.clamp(from.xCoord, to.minX, to.maxX);
        double y = MathUtil.clamp(from.yCoord, to.minY, to.maxY);
        double z = MathUtil.clamp(from.zCoord, to.minZ, to.maxZ);

        return new Vec3(x, y, z);
    }

    public double getReachToEntity(Entity entity) {
        Vec3 from = mc.thePlayer.getPositionEyes(1F);
        return from.distanceTo(getClosestHitVec3(from, entity.getEntityBoundingBox()));
    }

//    public float getPlayerDirection() {
//        float yaw = mc.thePlayer.rotationYaw;
//        if (mc.thePlayer.moveForward < 0) {
//            yaw += 180;
//        } else if (mc.thePlayer.moveStrafing > 0) {
//            yaw -= 45;
//        } else if (mc.thePlayer.moveStrafing < 0) {
//            yaw += 45;
//        }
//        return yaw;
//    }
}
