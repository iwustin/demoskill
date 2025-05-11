package iwust.demoskill.skill.iceclone;

import iwust.demoskill.constant.ItemId;
import iwust.demoskill.constant.Key;
import iwust.demoskill.constant.PDT;
import iwust.demoskill.util.ItemStackUtil;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import javax.annotation.Nonnull;

public final class IceCloneItem {
    @Nonnull
    public static ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.PRISMARINE_CRYSTALS);

        ItemStackUtil.setCustomName(itemStack, "<#91C9F7>Ледяная копия");
        ItemStackUtil.setLore(
                itemStack,
                List.of("<yellow>ПКМ: <gray>Призвать копию<dark_gray>",
                        "<yellow>ЛКМ: <gray>Вернутся к копии<dark_gray>")
        );

        ItemStackUtil.setPDC(itemStack, Key.ID, PDT.STRING, ItemId.ICE_CLONE_SKILL);
        ItemStackUtil.setPDC(itemStack, Key.TICK_A, PDT.INTEGER, 0);
        ItemStackUtil.setPDC(itemStack, Key.ICE_COPY_ID, PDT.INTEGER, -1);
        ItemStackUtil.setPDC(itemStack, Key.ICE_COPY_TASK_ID, PDT.INTEGER, -1);

        ItemStackUtil.unsetExtraData(itemStack);

        return itemStack;
    }
}
