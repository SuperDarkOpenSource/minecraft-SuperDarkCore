package net.superdark.minecraft.plugins.SuperDarkCore.services;

import net.superdark.minecraft.plugins.SuperDarkCore.SuperDarkCorePlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportService {

    private SuperDarkCorePlugin plugin_;

    public TeleportService(SuperDarkCorePlugin plugin)
    {
        this.plugin_ = plugin;
    }

    public boolean allowedToTeleport()
    {
        //TODO: Check integrations for permission to teleport (combat logger, etc.)
        return true;
    }


    /**
     * Taking in the player's name as a string, the x, and y, coordinate, the player will be teleported depending on the method used.
     */
    public static class TeleportTypes
    {
        /**
         * Attempts to teleport a player safely to a location starting from sky limit (y = 255).
         * @param world World object that the player will be teleported in.
         * @param player Bukkit player object used to teleport the player.
         * @param x X-Coordinate to teleport player to.
         * @param z Z-Coordinate to teleport player to.
         * @return TRUE if a safe place for a player to spawn was found and the player was attempted to be teleported, FALSE if the world does not exist, the player does not exist, or a safe location was not found.
         */
        static public boolean SafeTeleportAboveGround(@NotNull World world, @NotNull Player player, double x, double z)
        {
            double y = player.getWorld().getMaxHeight() - 1; // Build height for minecraft. - 1 because above build height is null.
            Location locationToTeleportTo = new Location(world, x, y, z);

            while (locationToTeleportTo.getBlock().getType().equals(Material.AIR))
            {
                if (locationToTeleportTo.getBlockY() < player.getWorld().getMinHeight() + 5) return false; // + 5 accounts for bedrock spawning
                locationToTeleportTo.setY(locationToTeleportTo.getBlockY() - 1);
            }
            locationToTeleportTo.setY(locationToTeleportTo.getBlockY() + 1); // The block that the location is currently at is a solid block, add 1 to prevent them from spawning inside a block.
            player.teleport(locationToTeleportTo);
            return true;
        }

        /**
         * Attempts to teleport a player safely starting from where bedrock starts spawning (y = -59).
         * @param world World object that the player will be teleported in.
         * @param player Bukkit player object used to teleport the player.
         * @param x X-Coordinate to teleport player to.
         * @param z Z-Coordinate to teleport player to.
         * @return TRUE if a safe place for a player to spawn was found and the player was attempted to be teleported, FALSE if the world does not exist, the player does not exist, or a safe location was not found.
         */
        static public boolean SafeTeleportAnywhere(@NotNull World world, @NotNull Player player, double x, double z)
        {
            double y = player.getWorld().getMinHeight() + 5; // Where bedrock starts to spawn for minecraft.

            Location locationToTeleportTo = new Location(world, x, y, z);
            Location checkBlockAboveFirstLocation =  new Location(world, x, y + 1, z);

            while (locationToTeleportTo.getBlock().getType().equals(Material.AIR) && checkBlockAboveFirstLocation.getBlock().getType().equals(Material.AIR))
            {
                if (locationToTeleportTo.getBlockY() > player.getWorld().getMaxHeight()) return false; // return false if the spot to teleport is above world height.
                locationToTeleportTo.setY(locationToTeleportTo.getBlockY() + 1);
            }
            player.teleport(locationToTeleportTo);
            return true;
        }
    }
}
