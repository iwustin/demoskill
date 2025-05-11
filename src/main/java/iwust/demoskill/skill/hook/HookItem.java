package iwust.demoskill.skill.hook;

import iwust.demoskill.constant.ItemId;
import iwust.demoskill.constant.Key;
import iwust.demoskill.constant.PDT;
import iwust.demoskill.util.ItemStackUtil;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import javax.annotation.Nonnull;

public final class HookItem {
    @Nonnull
    public static ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.IRON_HOE);

        ItemStackUtil.setCustomName(itemStack, "<#50636B>Прочный крюк");
        ItemStackUtil.setLore(
                itemStack,
                List.of("<yellow>ПКМ: <gray>Забросить крюк<dark_gray>",
                        "<yellow>ЛКМ: <gray>Открепить крюк<dark_gray>")
        );

        ItemStackUtil.setPDC(itemStack, Key.ID, PDT.STRING, ItemId.HOOK_SKILL);
        ItemStackUtil.setPDC(itemStack, Key.TICK_A, PDT.INTEGER, 0);

        ItemStackUtil.unsetExtraData(itemStack);

        return itemStack;
    }
}
