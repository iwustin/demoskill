package iwust.demoskill.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;

import javax.annotation.Nonnull;

public final class StringUtil {
    @Nonnull
    public static Component deserialize(@Nonnull String input, boolean italic) {
        return italic ? MiniMessage.miniMessage().deserialize(input) : deserialize(input);
    }

    @Nonnull
    public static Component deserialize(@Nonnull String input) {
        return MiniMessage.miniMessage().deserialize(input).decoration(TextDecoration.ITALIC, false);
    }

    @Nonnull
    public static String serialize(@Nonnull Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }
}