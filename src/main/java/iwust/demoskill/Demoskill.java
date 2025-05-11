package iwust.demoskill;

import iwust.demoskill.command.SkillCommand;
import iwust.demoskill.skill.hook.HookThrow;
import iwust.demoskill.skill.iceclone.IceCloneUse;
import iwust.demoskill.skill.land.LandGorge;
import iwust.demoskill.skill.vampire.VampireMouseFly;
import iwust.demoskill.skill.vampire.VampireMoveLess;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public final class Demoskill extends JavaPlugin {
    @Override
    public void onEnable() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new VampireMoveLess(), this);
        pluginManager.registerEvents(new VampireMouseFly(), this);
        pluginManager.registerEvents(new HookThrow(), this);
        pluginManager.registerEvents(new IceCloneUse(), this);
        pluginManager.registerEvents(new LandGorge(), this);

        PluginCommand skillCommand = getCommand("skill");
        if(skillCommand != null) {
            skillCommand.setExecutor(new SkillCommand());
        } else {
            Bukkit.shutdown();
        }

        Bukkit.getScheduler().runTaskLater(this, () -> {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard scoreboard = manager.getMainScoreboard();
            Team team = scoreboard.getTeam("hide_display_name");
            if(team == null) {
                team = scoreboard.registerNewTeam("hide_display_name");
            }
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            team.addEntry("");
        }, 20L);
    }
}
