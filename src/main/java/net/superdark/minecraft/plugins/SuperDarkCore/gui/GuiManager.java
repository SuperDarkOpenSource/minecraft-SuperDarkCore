package net.superdark.minecraft.plugins.SuperDarkCore.gui;

import net.superdark.minecraft.plugins.SuperDarkCore.SuperDarkCorePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GuiManager implements Listener
{
    final SuperDarkCorePlugin _plugin;
    private final HashMap<Inventory, IInventoryHandler> _inventories;

    public  GuiManager(SuperDarkCorePlugin plugin)
    {
        _plugin = plugin;
        _inventories = new HashMap<>();

        _plugin.getServer().getPluginManager().registerEvents(this, _plugin);
    }

    public void RegisterInventory(Inventory inventory, IInventoryHandler handler)
    {
        _inventories.put(inventory, handler);
    }

    public void UnRegisterInventory(Inventory inventory)
    {
        _inventories.remove(inventory);
    }

    @EventHandler
    public void onClickEvent(InventoryClickEvent event)
    {
        var inventory = _inventories.get(event.getClickedInventory());
        if(inventory != null) inventory.onClick(event);
    }

    @EventHandler
    public void onOpenEvent(InventoryOpenEvent event)
    {
        var inventory = _inventories.get(event.getInventory());
        if(inventory != null) inventory.onOpen(event);
    }

    @EventHandler
    public void onCloseEvent(InventoryCloseEvent event)
    {
        var inventory = _inventories.get(event.getInventory());
        if(inventory != null) inventory.onClose(event);
    }
}
