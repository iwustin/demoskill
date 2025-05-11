package iwust.demoskill.command;

import iwust.demoskill.skill.hook.HookItem;
import iwust.demoskill.skill.iceclone.IceCloneItem;
import iwust.demoskill.skill.land.LandItem;
import iwust.demoskill.skill.vampire.VampireItem;
import iwust.demoskill.util.StringUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public final class SkillCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@Nonnull CommandSender sender,
                             @Nonnull Command command,
                             @Nonnull String label,
                             @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(StringUtil.deserialize("Эта команда только для игроков."));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(StringUtil.deserialize("Использование: /skill <скилл>"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "vampire" -> {
                PlayerInventory playerInventory = ((Player) sender).getInventory();
                playerInventory.addItem(VampireItem.getItemStack());
            }
            case "hook" -> {
                PlayerInventory playerInventory = ((Player) sender).getInventory();
                playerInventory.addItem(HookItem.getItemStack());
            }
            case "ice_clone" -> {
                PlayerInventory playerInventory = ((Player) sender).getInventory();
                playerInventory.addItem(IceCloneItem.getItemStack());
            }
            case "land" -> {
                PlayerInventory playerInventory = ((Player) sender).getInventory();
                playerInventory.addItem(LandItem.getItemStack());
            }
            default -> {
                sender.sendMessage(StringUtil.deserialize("Неизвестый скилл. Использование: /skill <скилл>"));
                return true;
            }
        }
        sender.sendMessage(StringUtil.deserialize("Выдан скилл."));
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender,
                                      @Nonnull Command command,
                                      @Nonnull String alias,
                                      @Nonnull String[] args) {
        if (args.length == 1) {
            return List.of(
                    "vampire",
                    "hook",
                    "ice_clone",
                    "land"
            );
        }
        return null;
    }
}