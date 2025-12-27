package net.superdark.minecraft.plugins.SuperDarkCore.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class InventoryGui implements IInventoryHandler
{
    private transient Inventory _inventory;
    private final HashMap<Integer, GuiButton> _buttonMap;

    protected InventoryGui()
    {
        _inventory = createInventory();
        _buttonMap = new HashMap<>();
    }

    @Override
    public void populateInventory(InventoryOpenEvent event)
    {
        _buttonMap.forEach((integer, guiButton) -> _inventory.setItem(integer, guiButton.getIcon((Player) event.getPlayer())));
    }

    public Inventory getInventory()
    {
        return _inventory;
    }

    public void addButton(int index, GuiButton button)
    {
        _buttonMap.put(index, button);
    }

    @Override
    public void onClick(InventoryClickEvent event)
    {
        // If this is not cancelled, then the DragEvent needs to be handled as well, otherwise bad stuff happens.
        event.setCancelled(true);
        int slot = event.getSlot();
        GuiButton button = _buttonMap.get(slot);
        if(button == null) return;
        button.getConsumer().accept(event);
    }


    @Override
    public void onOpen(InventoryOpenEvent event)
    {
        populateInventory(event);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {}

    public abstract Inventory createInventory();
}
