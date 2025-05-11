package iwust.demoskill.skill.land;

import iwust.demoskill.constant.ItemId;
import iwust.demoskill.constant.Key;
import iwust.demoskill.constant.PDT;
import iwust.demoskill.util.ItemStackUtil;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import javax.annotation.Nonnull;

public final class LandItem {
    @Nonnull
    public static ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.SCUTE);

        ItemStackUtil.setCustomName(itemStack, "<#693010>Удобные земли");
        ItemStackUtil.setLore(
                itemStack,
                List.of("<yellow>ПКМ: <gray>Ущелье")
        );

        ItemStackUtil.setPDC(itemStack, Key.ID, PDT.STRING, ItemId.LAND_SKILL);
        ItemStackUtil.setPDC(itemStack, Key.TICK_A, PDT.INTEGER, 0);

        ItemStackUtil.unsetExtraData(itemStack);

        return itemStack;
    }
}
