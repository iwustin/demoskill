package iwust.demoskill.skill.iceclone;

import iwust.demoskill.Demoskill;
import iwust.demoskill.constant.ItemId;
import iwust.demoskill.constant.Key;
import iwust.demoskill.constant.PDT;
import iwust.demoskill.skill.SkillUtil;
import iwust.demoskill.util.ItemStackUtil;
import iwust.demoskill.util.SoundUtil;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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

public final class IceCloneUse implements Listener {
    @EventHandler
    public void onPlayerInteractRight(@Nonnull final PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) {
            return;
        }

        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getItemInMainHand();
        if (!SkillUtil.compareId(itemStack, ItemId.ICE_CLONE_SKILL)) {
            return;
        }

        event.setCancelled(true);

        if (SkillUtil.isTick(itemStack, Key.TICK_A, 60.f * 20)) {
            SoundUtil.playSoundAtLocation(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 32);
            SkillUtil.updateTick(itemStack, Key.TICK_A);
            Location copyLocation = player.getLocation().clone().add(0, 1, 0);

            int id = IceCloneUtil.createNMSPlayerCopy(player);

            BukkitTask task = Bukkit.getScheduler().runTaskTimer(
                    Demoskill.getPlugin(Demoskill.class),
                    () -> {
                        IceCloneUtil.spawnParticles(copyLocation, true, 1f);
                        IceCloneUtil.spawnParticles(player.getLocation(), false, 2f);
                        SoundUtil.playSoundAtLocation(copyLocation, Sound.BLOCK_GLASS_HIT, 16);

                        for(Entity entity : copyLocation.getNearbyEntities(5f, 5f, 5f)) {
                            if(entity instanceof LivingEntity livingEntity) {
                                if(entity == player) {
                                    continue;
                                }
                                livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2, false, false, false));
                            }
                        }
                    },
                    0L,
                    5L
            );

            player.setAllowFlight(true);
            player.setFlying(true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, false));

            Bukkit.getScheduler().runTaskLater(
                    Demoskill.getPlugin(Demoskill.class),
                    () -> {
                        if(task.isCancelled()){
                            return;
                        }
                        task.cancel();
                        copyLocation.getWorld().spawnParticle(
                                Particle.SNOWFLAKE,
                                copyLocation,
                                1
                        );
                        IceCloneUtil.spawnParticles(copyLocation, false, 4f);
                        IceCloneUtil.removeNMSPlayerCopy(id);
                        SoundUtil.playSoundAtLocation(copyLocation, Sound.BLOCK_GLASS_BREAK, 32);
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    },
                    30 * 20
            );

            ItemStackUtil.setPDC(itemStack, Key.ICE_COPY_ID, PDT.INTEGER, id);
            ItemStackUtil.setPDC(itemStack, Key.ICE_COPY_TASK_ID, PDT.INTEGER, task.getTaskId());

            ItemStackUtil.setPDC(itemStack, Key.ICE_COPY_X, PDT.DOUBLE, copyLocation.getX());
            ItemStackUtil.setPDC(itemStack, Key.ICE_COPY_Y, PDT.DOUBLE, copyLocation.getY());
            ItemStackUtil.setPDC(itemStack, Key.ICE_COPY_Z, PDT.DOUBLE, copyLocation.getZ());
        }
    }

    @EventHandler
    public void onPlayerInteractLeft(@Nonnull final PlayerInteractEvent event) {
        if (!event.getAction().isLeftClick()) {
            return;
        }

        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getItemInMainHand();
        if (!SkillUtil.compareId(itemStack, ItemId.ICE_CLONE_SKILL)) {
            return;
        }

        event.setCancelled(true);

        Integer id = ItemStackUtil.getPDC(itemStack, Key.ICE_COPY_ID, PDT.INTEGER);
        if(id == null || id == -1) {
            return;
        }

        Integer taskId = ItemStackUtil.getPDC(itemStack, Key.ICE_COPY_TASK_ID, PDT.INTEGER);
        if(taskId == null || taskId == -1) {
            return;
        }

        Double x = ItemStackUtil.getPDC(itemStack, Key.ICE_COPY_X, PDT.DOUBLE);
        Double y = ItemStackUtil.getPDC(itemStack, Key.ICE_COPY_Y, PDT.DOUBLE);
        Double z = ItemStackUtil.getPDC(itemStack, Key.ICE_COPY_Z, PDT.DOUBLE);
        if(x == null || y == null || z == null) {
            return;
        }

        Location copyLocation = new Location(player.getWorld(), x, y, z);

        for(BukkitTask bukkitTask : Bukkit.getScheduler().getPendingTasks()) {
            if(bukkitTask.getTaskId() == taskId) {
                bukkitTask.cancel();
                IceCloneUtil.spawnParticles(copyLocation, false, 4f);
                IceCloneUtil.removeNMSPlayerCopy(id);
                player.teleport(copyLocation);

                ItemStackUtil.setPDC(itemStack, Key.ICE_COPY_ID, PDT.INTEGER, -1);
                ItemStackUtil.setPDC(itemStack, Key.ICE_COPY_TASK_ID, PDT.INTEGER, -1);

                SoundUtil.playSoundAtLocation(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 32);

                player.setAllowFlight(false);
                player.setFlying(false);
                player.removePotionEffect(PotionEffectType.INVISIBILITY);

                copyLocation.getWorld().spawnParticle(
                        Particle.SNOWFLAKE,
                        copyLocation,
                        1
                );
            }
        }

    }
}
