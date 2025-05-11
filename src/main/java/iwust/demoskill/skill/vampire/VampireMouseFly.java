package iwust.demoskill.skill.vampire;

import iwust.demoskill.Demoskill;
import iwust.demoskill.constant.ItemId;
import iwust.demoskill.constant.Key;
import iwust.demoskill.skill.SkillUtil;
import iwust.demoskill.util.SoundUtil;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;

public final class VampireMouseFly implements Listener {
    @EventHandler
    public void onPlayerInteract(@Nonnull final PlayerInteractEvent event) {
        if(!event.getAction().isLeftClick()) {
            return;
        }

        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getItemInMainHand();
        if(!SkillUtil.compareId(itemStack, ItemId.VAMPIRE_SKILL)) {
            return;
        }

        event.setCancelled(true);

        if(SkillUtil.isTick(itemStack, Key.TICK_A, 60.f * 20)) {
            SkillUtil.updateTick(itemStack, Key.TICK_A);
            mouseFly(player);
        }
    }

    private void mouseFly(@Nonnull final Player player) {
        player.setAllowFlight(true);
        player.setFlying(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, false));

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                Demoskill.getPlugin(Demoskill.class),
                () -> {
                    SoundUtil.playSoundAtLocation(player.getLocation(), Sound.ENTITY_BAT_AMBIENT, 16);
                    VampireUtil.spawnParticles(player.getLocation(), true, 2f);
                },
                0L,
                3L
        );

        Bukkit.getScheduler().runTaskLater(
                Demoskill.getPlugin(Demoskill.class),
                () -> {
                    task.cancel();
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                },
                10L * 20L
        );
    }
}
