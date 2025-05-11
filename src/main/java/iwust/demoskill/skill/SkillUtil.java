package iwust.demoskill.skill;

import iwust.demoskill.constant.Key;
import iwust.demoskill.constant.PDT;
import iwust.demoskill.util.ItemStackUtil;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public final class SkillUtil {
    public static boolean compareId(@Nonnull final ItemStack itemStack,
                                    @Nonnull final String id) {
        String itemStackId = ItemStackUtil.getPDC(itemStack, Key.ID, PDT.STRING);
        return itemStackId != null && itemStackId.equals(id);
    }

    public static boolean isTick(@Nonnull final ItemStack itemStack,
                                 @Nonnull final NamespacedKey tickKey,
                                 final float tick) {
        Integer itemStackTick = ItemStackUtil.getPDC(itemStack, tickKey, PDT.INTEGER);
        if(itemStackTick == null) {
            return false;
        }

        if(Bukkit.getCurrentTick() - itemStackTick < 0) {
            return true;
        }
        return Bukkit.getCurrentTick() - itemStackTick >= tick;
    }

    public static void updateTick(@Nonnull final ItemStack itemStack,
                                  @Nonnull final NamespacedKey tickKey) {
        ItemStackUtil.setPDC(itemStack, tickKey, PDT.INTEGER, Bukkit.getCurrentTick());
    }
}
