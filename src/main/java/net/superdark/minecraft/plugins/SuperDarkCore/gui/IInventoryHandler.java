package net.superdark.minecraft.plugins.SuperDarkCore.gui;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

/// Reference: https://www.spigotmc.org/threads/a-modern-approach-to-inventory-guis.594005/
public interface IInventoryHandler
{
    Inventory createInventory();

    void populateInventory(InventoryOpenEvent event);
    void onClick(InventoryClickEvent event);
    void onOpen(InventoryOpenEvent event);
    void onClose(InventoryCloseEvent event);
}
