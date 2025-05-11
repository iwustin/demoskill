package iwust.demoskill.util;

import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

public final class MathUtil {
    @Nonnull
    public static Vector getRandomOffset(final double radius) {
        double theta = Math.random() * 2 * Math.PI;
        double phi = Math.acos(2 * Math.random() - 1);
        double r = Math.cbrt(Math.random()) * radius;

        double x = r * Math.sin(phi) * Math.cos(theta);
        double y = r * Math.sin(phi) * Math.sin(theta);
        double z = r * Math.cos(phi);

        return new Vector(x, y, z);
    }
}
