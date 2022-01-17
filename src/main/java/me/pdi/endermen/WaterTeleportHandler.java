package me.pdi.endermen;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WaterTeleportHandler implements Listener {

    private static final Vector CENTER = new Vector(89, 65, 1308);

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(@NotNull PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();
        Location to = event.getTo();
        Location center = new Location(to.getWorld(), CENTER.getX(), CENTER.getY(), CENTER.getZ());
        Block block = to.getBlock();
        World world = to.getWorld();

        double distance = center.distance(to);

        if (block.getType() == Material.WATER && distance < 100 && distance > 5) {
            Location target = getTargetLocation(to);
            if (target != null) {
                target.setY(target.getBlockY());
                target.setPitch(playerLocation.getPitch());
                target.setYaw(playerLocation.getYaw());
                event.setTo(target);

                world.playSound(playerLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            } else {
                player.damage(Integer.MAX_VALUE);
            }
        }
    }

    private static @Nullable Location getTargetLocation(@NotNull Location start) {
        World world = start.getWorld();
        Vector direction = CENTER.clone().subtract(start.toVector());

        BlockIterator iterator = new BlockIterator(
                world, start.toVector(), direction, 0, 50
        );

        while (iterator.hasNext()) {
            Block next = iterator.next();
            Block surface = world.getHighestBlockAt(next.getLocation(), HeightMap.MOTION_BLOCKING);
            if (surface.getType() != Material.WATER && surface.getY() < 70) {

                return surface.getRelative(BlockFace.UP).getLocation().toCenterLocation();
            }
        }

        return null;
    }
}
