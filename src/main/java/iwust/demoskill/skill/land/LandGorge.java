package iwust.demoskill.skill.land;

import iwust.demoskill.Demoskill;
import iwust.demoskill.constant.ItemId;
import iwust.demoskill.constant.Key;
import iwust.demoskill.skill.SkillUtil;
import iwust.demoskill.util.SoundUtil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

public final class LandGorge implements Listener {
    @EventHandler
    public void onPlayerInteract(@Nonnull final PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getItemInMainHand();
        if (!SkillUtil.compareId(itemStack, ItemId.LAND_SKILL)) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getRightClicked() instanceof LivingEntity livingEntity)) {
            return;
        }

        if (SkillUtil.isTick(itemStack, Key.TICK_A, 20 * 20)) {
            SoundUtil.playSoundAtLocation(player.getLocation(), Sound.BLOCK_ROOTED_DIRT_BREAK, 16);
            SkillUtil.updateTick(itemStack, Key.TICK_A);

            Block startBlock = livingEntity.getLocation().clone().add(0, -2, 0).getBlock();

            for (int x = -1; x <= 1; x++) {
                for (int y = -3; y <= 3; y++) {
                    for (int z = -1; z <= 1; z++) {
                        Block block = startBlock.getLocation().clone().add(x, y, z).getBlock();
                        Material material = block.getType();

                        if (block.getType() != Material.AIR) {
                            block.breakNaturally();
                        }

                        Bukkit.getScheduler().runTaskLater(
                                Demoskill.getPlugin(Demoskill.class),
                                () -> block.setType(material),
                                60L
                        );
                    }
                }
            }

            Bukkit.getScheduler().runTaskLater(
                    Demoskill.getPlugin(Demoskill.class),
                    () -> {
                        livingEntity.setVelocity(new Vector(0, 1f, 0));
                        for(Entity entity : livingEntity.getLocation().getNearbyEntities(4f, 4f, 4f)) {
                            if(entity instanceof LivingEntity living) {
                                living.setVelocity(new Vector(0, 1f, 0));
                            }
                        }
                    },
                    50L
            );
        }
    }
}
