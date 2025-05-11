package iwust.demoskill.skill.hook;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public final class HookUtil {
    public static void drawParticleLine(@Nonnull final Location start,
                                        @Nonnull final Location end,
                                        final double step) {
        World world = start.getWorld();
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GRAY, 0.5f);
        for (Location point : getLinePoints(start, end, step)) {
            world.spawnParticle(
                    Particle.REDSTONE,
                    point,
                    1,
                    dustOptions
            );
        }
    }

    public static void rotateArmorStand(ArmorStand armorStand) {
        float currentYaw = armorStand.getLocation().getYaw();
        float newYaw = currentYaw + 20.0f;
        newYaw = newYaw % 360;
        if (newYaw < 0) {
            newYaw += 360;
        }

        armorStand.setRotation(newYaw, 0);
    }

    @Nonnull
    private static List<Location> getLinePoints(@Nonnull final Location start,
                                        @Nonnull final Location end,
                                        final double step) {
        List<Location> points = new ArrayList<>();
        double distance = start.distance(end);
        Vector direction = end.toVector().subtract(start.toVector()).normalize().multiply(step);

        for (double i = 0; i <= distance; i += step) {
            points.add(start.clone().add(direction.clone().multiply(i / step)));
        }
        return points;
    }
}
