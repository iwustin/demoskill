package iwust.demoskill.skill.vampire;

import iwust.demoskill.Demoskill;
import iwust.demoskill.constant.ItemId;
import iwust.demoskill.constant.Key;
import iwust.demoskill.skill.SkillUtil;
import iwust.demoskill.util.SoundUtil;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public final class VampireMoveLess implements Listener {
    private static final List<Player> moveLessList = new ArrayList<>();

    @EventHandler
    public void onPlayerInteract(@Nonnull final PlayerInteractEvent event) {
        if(!event.getAction().isRightClick()) {
            return;
        }

        Player player = event.getPlayer();
        if(moveLessList.contains(player)) {
            event.setCancelled(true);
            return;
        }

        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getItemInMainHand();
        if(!SkillUtil.compareId(itemStack, ItemId.VAMPIRE_SKILL)) {
            return;
        }

        event.setCancelled(true);

        if(SkillUtil.isTick(itemStack, Key.TICK_B,10.f * 20)) {
            SoundUtil.playSoundAtLocation(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 16);
            SkillUtil.updateTick(itemStack, Key.TICK_B);
            moveLess(player);
        }
    }

    @EventHandler
    public void onPlayerMove(@Nonnull final PlayerMoveEvent event) {
        if(moveLessList.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    private void moveLess(@Nonnull final Player player) {
        Location center = player.getLocation();

        List<Entity> entityList = player.getNearbyEntities(3.5f, 3.5f, 3.5f);
        List<Player> playerList = new ArrayList<>();

        for(Entity entity : entityList) {
            if(entity instanceof Player p) {
                moveLessList.add(p);
                playerList.add(p);
            }
        }

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                Demoskill.getPlugin(Demoskill.class),
                () -> {
                    VampireUtil.spawnParticles(center, true, 3.5f);
                    SoundUtil.playSoundAtLocation(player.getLocation(), Sound.ENTITY_BAT_DEATH, 16);
                    for(Player p : playerList) {
                        p.damage(2.f);
                    }
                },
                0L,
                10L
        );

        Bukkit.getScheduler().runTaskLater(
                Demoskill.getPlugin(Demoskill.class),
                () -> {
                    for(Player p : playerList) {
                        moveLessList.remove(p);
                    }
                    task.cancel();
                },
                4L * 20L
        );
    }
}
