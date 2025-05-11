package iwust.demoskill.skill.vampire;

import iwust.demoskill.constant.ItemId;
import iwust.demoskill.constant.Key;
import iwust.demoskill.constant.PDT;
import iwust.demoskill.util.ItemStackUtil;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import javax.annotation.Nonnull;

public final class VampireItem {
    @Nonnull
    public static ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.RED_DYE);

        ItemStackUtil.setCustomName(itemStack, "<#BA2F2F>Вампирская сила");
        ItemStackUtil.setLore(
                itemStack,
                List.of("<yellow>ПКМ: <gray>Паралич",
                        "<yellow>ЛКМ: <gray>Мышиный полёт")
        );

        ItemStackUtil.setPDC(itemStack, Key.ID, PDT.STRING, ItemId.VAMPIRE_SKILL);
        ItemStackUtil.setPDC(itemStack, Key.TICK_A, PDT.INTEGER, 0);
        ItemStackUtil.setPDC(itemStack, Key.TICK_B, PDT.INTEGER, 0);

        ItemStackUtil.unsetExtraData(itemStack);

        return itemStack;
    }
}
