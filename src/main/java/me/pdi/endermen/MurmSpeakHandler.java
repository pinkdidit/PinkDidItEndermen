package me.pdi.endermen;

import com.destroystokyo.paper.event.entity.EndermanAttackPlayerEvent;
import io.papermc.paper.event.entity.EntityMoveEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class MurmSpeakHandler implements Listener {

    private static final String[] MESSAGES = new String[]{
            "⟟ ⊑⏃⎐⟒ ⏚⍀⍜⎍☌⊑⏁ ⊬⍜⎍ ⏁⍜ ⏁⊑⟒ ⋏⍜⍜⎅⌰⟒⌇⋔⌿, ⍜⋏⟒ ⍙⟒⟒☍ ⟟⋏ ⏁⊑⟒ ⎎⎍⏁⎍⍀⟒. ⏁⊑⟟⋏☌⌇ ⏃⍀⟒ ⌰⍜⍜☍⟟⋏☌ ⎅⟟⍀⟒. ",
            "⟟⋏ ⏁⊑⟟⌇ ⎎⎍⏁⎍⍀⟒, ⌿⟟⋏☍ ⊑⏃⌇ ⎎⍜⎍⋏⎅ ⏃⋏ ⟒⋏☊⊑⏃⋏⏁⟒⎅ ☊⍀⊬⌇⏁⏃⌰, ⏃⋏⎅ ⟟⏁⌇ ⌿⍜⍙⟒⍀⌇ ⊑⏃⎐⟒ ⌇⏁⏃⍀⏁⟒⎅ ⏁⍜ ☊⍜⋏⌇⎍⋔⟒ ⏁⊑⟒ ⍙⍜⍀⌰⎅. ⏃⏁ ⏁⊑⟒ ⌿⍜⟟⋏⏁ ⟟⋏ ⏁⟟⋔⟒, ⟟⏁⌇ ⏁⍜⍜ ⌰⏃⏁⟒ ⏁⍜ ⌇⏁⍜⌿ ⟟⏁",
            "⊬⍜⎍ ⋔⎍⌇⏁ ⋏⍜⏁ ⌰⟒⏁ ⏁⊑⟟⌇ ⊑⏃⌿⌿⟒⋏. ⊬⍜⎍ ⋔⎍⌇⏁ ⎎⟟⋏⎅ ⏁⊑⟒ ☊⍀⊬⌇⏁⏃⌰ ⏃⋏⎅ ⎅⟒⌇⏁⍀⍜⊬ ⟟⏁, ⏚⟒⎎⍜⍀⟒ ⟟⏁ ⟟⌇ ⏁⍜⍜ ⌰⏃⏁⟒. ⟟ ⎅⍜⋏⏁ ☍⋏⍜⍙ ⍙⊑⟒⍀⟒ ⏁⊑⟒ ☊⍀⊬⌇⏁⏃⌰ ⟟⌇, ⏚⎍⏁ ⊑⍜⌿⟒⎎⎍⌰⌰⊬ ⊬⍜⎍ ☊⏃⋏ ⎎⟟⋏⎅ ⏁⊑⏃⏁ ⍜⎍⏁."
    };

    private final Set<UUID> hasClicked = new LinkedHashSet<>();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerInteractEntity(@NotNull PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (!this.hasClicked.contains(player.getUniqueId())) {
            this.hasClicked.add(player.getUniqueId());

            if (entity.getType() == EntityType.ENDERMAN) {
                int delay = 0;
                for (String message : MESSAGES) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(PinkDidItEndermen.INSTANCE, () ->
                                    player.sendMessage(Component.text("<MURM> ").append(Component.text(message))),
                            delay
                    );
                    delay += 20;
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEndermanAttackPlayer(@NotNull EndermanAttackPlayerEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityMove(@NotNull EntityMoveEvent event) {
        Entity entity = event.getEntity();

        if (entity.getType() == EntityType.ENDERMAN && entity.isCustomNameVisible()) {
            if (entity.getLocation().getY() < 75) {
                entity.teleport(new Location(entity.getWorld(), 87.5, 94.5, 1306.5));
            }
        }
    }
}
