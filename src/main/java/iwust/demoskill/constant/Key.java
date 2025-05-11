package iwust.demoskill.constant;

import iwust.demoskill.Demoskill;

import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

public final class Key {
    @Nonnull
    public static final NamespacedKey ID = new NamespacedKey(Demoskill.getPlugin(Demoskill.class), "id");
    @Nonnull
    public static final NamespacedKey TICK_A = new NamespacedKey(Demoskill.getPlugin(Demoskill.class), "tick_a");
    @Nonnull
    public static final NamespacedKey TICK_B = new NamespacedKey(Demoskill.getPlugin(Demoskill.class), "tick_b");
    @Nonnull
    public static final NamespacedKey HOOK_UUID = new NamespacedKey(Demoskill.getPlugin(Demoskill.class), "hook_uuid");
    @Nonnull
    public static final NamespacedKey ICE_COPY_ID = new NamespacedKey(Demoskill.getPlugin(Demoskill.class), "ice_copy_id");
    @Nonnull
    public static final NamespacedKey ICE_COPY_TASK_ID = new NamespacedKey(Demoskill.getPlugin(Demoskill.class), "ice_copy_task_id");
    @Nonnull
    public static final NamespacedKey ICE_COPY_X = new NamespacedKey(Demoskill.getPlugin(Demoskill.class), "ice_copy_x");
    @Nonnull
    public static final NamespacedKey ICE_COPY_Y = new NamespacedKey(Demoskill.getPlugin(Demoskill.class), "ice_copy_y");
    @Nonnull
    public static final NamespacedKey ICE_COPY_Z = new NamespacedKey(Demoskill.getPlugin(Demoskill.class), "ice_copy_z");
}
