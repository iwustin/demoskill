package iwust.demoskill.util;

import org.bukkit.Location;
import org.bukkit.Sound;

import javax.annotation.Nonnull;

public final class SoundUtil {
    public static void playSoundAtLocation(@Nonnull final Location location,
                                           @Nonnull final Sound sound,
                                           int radius) {
        location.getWorld().playSound(
                location,
                sound,
                (float) radius / 16,
                1.0f
        );
    }
}
