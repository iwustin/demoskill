/**
 * Адаптировано под версию 1.19.3 и demoskill
 */

package iwust.demoskill.util;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class ItemStackUtil {
    public static void unsetExtraData(@Nonnull final ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            for(ItemFlag itemFlag : ItemFlag.values()) {
                itemMeta.addItemFlags(itemFlag);
            }

            itemStack.setItemMeta(itemMeta);
        }
    }

    public static void setCustomName(@Nonnull final ItemStack itemStack,
                                     @Nullable final String customName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && customName != null) {
            Component customNameComponent = StringUtil.deserialize(customName);
            itemMeta.displayName(customNameComponent);
            itemStack.setItemMeta(itemMeta);
        }
    }

    @Nullable
    public static String getCustomName(@Nonnull final ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            Component customNameComponent = itemMeta.displayName();
            return customNameComponent != null ? StringUtil.serialize(customNameComponent) : null;
        }
        return null;
    }

    public static void setLore(@Nonnull final ItemStack itemStack,
                               @Nullable final List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null && lore != null) {
            List<Component> loreComponents = new ArrayList<>();
            lore.stream()
                    .map(StringUtil::deserialize)
                    .forEach(loreComponents::add);
            itemMeta.lore(loreComponents);
            itemStack.setItemMeta(itemMeta);
        }
    }

    @Nonnull
    public static List<String> getLore(@Nonnull final ItemStack itemStack) {
        List<String> lore = new ArrayList<>();
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<Component> itemLore = itemMeta.lore();
        if (itemLore != null) {
            for (Component line : itemLore) {
                lore.add(StringUtil.serialize(line));
            }
        }
        return lore;
    }

    public static <T, Z> void setPDC(@Nonnull final ItemStack itemStack,
                                     @Nonnull final NamespacedKey key,
                                     @Nonnull final PersistentDataType<T, Z> dataType,
                                     @Nonnull final Z value) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
            pdc.set(key, dataType, value);
            itemStack.setItemMeta(itemMeta);
        }
    }

    @Nullable
    public static <T, Z> Z getPDC(@Nonnull final ItemStack itemStack,
                                  @Nonnull final NamespacedKey key,
                                  @Nonnull final PersistentDataType<T, Z> dataType) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.get(key, dataType);
    }

    public static <T, Z> boolean hasPDC(@Nonnull final ItemStack itemStack,
                                        @Nonnull final NamespacedKey key,
                                        @Nonnull final PersistentDataType<T, Z> dataType) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return false;
        }
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.has(key, dataType);
    }

    public static <T, Z> void removePDC(@Nonnull final ItemStack itemStack,
                                        @Nonnull final NamespacedKey key,
                                        @Nonnull final PersistentDataType<T, Z> dataType) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
            pdc.remove(key);
            itemStack.setItemMeta(itemMeta);
        }
    }
}