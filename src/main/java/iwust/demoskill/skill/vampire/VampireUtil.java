package iwust.demoskill.skill.vampire;

import iwust.demoskill.Demoskill;
import iwust.demoskill.util.MathUtil;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public final class VampireUtil {
    public static void spawnParticles(@Nonnull final Location center, final double radius) {
        spawnParticles(center, false, radius);
    }

    public static void spawnParticles(@Nonnull final Location center,
                                      final boolean spawnBats,
                                      final double radius) {
        int count = 200;
        if(spawnBats) {
            count = 30;
        }
        for(int i = 0;i < count;i++) {
            Vector randomOffset = MathUtil.getRandomOffset(radius);

            Location spawnLocation = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
            spawnLocation.add(randomOffset);

            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 1.0f);
            if (((int) randomOffset.getX()) % 2 == 0) {
                dustOptions = new Particle.DustOptions(Color.BLACK, 1.0f);
            }

            spawnLocation.getWorld().spawnParticle(
                    Particle.REDSTONE,
                    spawnLocation,
                    1,
                    dustOptions
            );
        }
        if(spawnBats) {
            spawnBats(center, 3);
        }
    }

    private static void spawnBats(@Nonnull final Location center,
                                  final int amount) {
        Vector randomOffset = MathUtil.getRandomOffset(2.f);

        Location spawnLocation = new Location(center.getWorld(), center.getX(), center.getY(), center.getZ());
        spawnLocation.add(randomOffset);

        List<Bat> batList = new ArrayList<>();
        for(int i = 0;i < amount;i++) {
            Bat bat = (Bat) center.getWorld().spawnEntity(spawnLocation, EntityType.BAT);
            batList.add(bat);
        }

        Bukkit.getScheduler().runTaskLater(
                Demoskill.getPlugin(Demoskill.class),
                () -> {
                    for(Bat bat : batList) {
                        bat.remove();
                    }
                },
                20L
        );
    }
}
