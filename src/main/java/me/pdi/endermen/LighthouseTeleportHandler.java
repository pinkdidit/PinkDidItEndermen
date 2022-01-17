package me.pdi.endermen;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class LighthouseTeleportHandler implements Listener {

    private static final Vector CENTER = new Vector(314, 73, 100);

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(@NotNull PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();
        World world = to.getWorld();
        Location center = new Location(to.getWorld(), CENTER.getX(), CENTER.getY(), CENTER.getZ());

        double distance = center.distance(to);

        if (distance < 100 && distance > 60) {
            event.setCancelled(true);

            world.playSound(to, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

            player.sendActionBar(Component.text("It's dangerous out there, better to stay safe...", NamedTextColor.DARK_PURPLE));
        }
    }
}
