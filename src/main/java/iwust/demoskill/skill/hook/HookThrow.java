package iwust.demoskill.skill.hook;

import iwust.demoskill.Demoskill;
import iwust.demoskill.constant.ItemId;
import iwust.demoskill.constant.Key;
import iwust.demoskill.constant.PDT;
import iwust.demoskill.skill.SkillUtil;
import iwust.demoskill.util.ItemStackUtil;
import iwust.demoskill.util.SoundUtil;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.UUID;
import javax.annotation.Nonnull;

public final class HookThrow implements Listener {
    @EventHandler
    public void onPlayerInteractHook(@Nonnull final PlayerInteractEvent event) {
        if(!event.getAction().isRightClick()) {
            return;
        }

        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getItemInMainHand();
        if(!SkillUtil.compareId(itemStack, ItemId.HOOK_SKILL)) {
            return;
        }

        event.setCancelled(true);

        if(SkillUtil.isTick(itemStack, Key.TICK_A, 6 * 20)) {
            SkillUtil.updateTick(itemStack, Key.TICK_A);
            hookThrow(player, itemStack);
        }
    }

    @EventHandler
    public void onPlayerInteractBack(@Nonnull final PlayerInteractEvent event) {
        if(!event.getAction().isLeftClick()) {
            return;
        }

        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();

        ItemStack itemStack = playerInventory.getItemInMainHand();
        if(!SkillUtil.compareId(itemStack, ItemId.HOOK_SKILL)) {
            return;
        }

        event.setCancelled(true);

        String armorStandUUID = ItemStackUtil.getPDC(itemStack, Key.HOOK_UUID, PDT.STRING);
        if(armorStandUUID == null) {
            return;
        }

        Entity entity = Bukkit.getServer().getEntity(UUID.fromString(armorStandUUID));
        if(entity != null) {
            entity.remove();
        }
    }

    private void hookThrow(@Nonnull final Player player,
                           @Nonnull final ItemStack itemStack) {
        World world = player.getWorld();

        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection().normalize();
        double distance = 0.5;
        Location spawnLocation = eyeLocation.add(direction.multiply(distance));

        ArmorStand armorStand = (ArmorStand) world.spawnEntity(spawnLocation, org.bukkit.entity.EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(true);
        armorStand.setInvulnerable(true);
        armorStand.setSmall(true);

        ItemStackUtil.setPDC(itemStack, Key.HOOK_UUID, PDT.STRING, armorStand.getUniqueId().toString());

        ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS);
        armorStand.getEquipment().setBoots(ironBoots);

        double speed = 5.0;
        Vector velocity = direction.multiply(speed);
        armorStand.setVelocity(velocity);

        BukkitTask taskChain = Bukkit.getScheduler().runTaskTimer(
                Demoskill.getPlugin(Demoskill.class),
                () -> {
                    if(armorStand.getTargetBlockExact(1) != null) {
                        armorStand.setGravity(false);
                    }
                    else if(armorStand.getLocation().clone().add(0, -0.1, 0).getBlock().getType() != Material.AIR ||
                            armorStand.getLocation().clone().add(0, 1, 0).getBlock().getType() != Material.AIR) {
                        armorStand.setGravity(false);
                    }
                    if(!armorStand.isDead()) {
                        HookUtil.rotateArmorStand(armorStand);
                        HookUtil.drawParticleLine(player.getLocation(), armorStand.getLocation(), 1);
                    }
                },
                0L,
                1L
        );

        BukkitTask taskVelocity = Bukkit.getScheduler().runTaskTimer(
                Demoskill.getPlugin(Demoskill.class),
                () -> {
                    if(!armorStand.isDead() && !armorStand.hasGravity()) {
                        if (player.getLocation().distance(armorStand.getLocation()) > 2.f) {
                            Vector d = armorStand.getLocation().toVector().subtract(player.getLocation().toVector());
                            Vector v = d.normalize().multiply(1.2f);
                            player.setVelocity(v);
                            SoundUtil.playSoundAtLocation(armorStand.getLocation(), Sound.BLOCK_ANVIL_LAND, 16);
                        }
                    }
                },
                0L,
                3L
        );

        Bukkit.getScheduler().runTaskLater(
                Demoskill.getPlugin(Demoskill.class),
                () -> {
                    taskChain.cancel();
                    taskVelocity.cancel();
                    armorStand.remove();
                },
                5L * 20L
        );
    }
}
