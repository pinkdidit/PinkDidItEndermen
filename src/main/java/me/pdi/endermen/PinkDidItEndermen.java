package me.pdi.endermen;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PinkDidItEndermen extends JavaPlugin {

    public static PinkDidItEndermen INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Bukkit.getPluginManager().registerEvents(new WaterTeleportHandler(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);
        Bukkit.getPluginManager().registerEvents(new LighthouseTeleportHandler(), this);
        Bukkit.getPluginManager().registerEvents(new MurmSpeakHandler(), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                player.setFoodLevel(20);
                player.setSaturation(1.0f);
            }
        }, 20, 20);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
