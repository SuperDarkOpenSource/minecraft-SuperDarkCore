package net.superdark.minecraft.plugins.SuperDarkCore.gui;

import net.superdark.minecraft.plugins.SuperDarkCore.SuperDarkCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ConfirmationInventoryGui extends InventoryGui
{
    private final Runnable _onConfirm;
    private final Runnable _onCancel;
    public ConfirmationInventoryGui(Runnable onConfirm, Runnable onCancel)
    {
        super();
        _onConfirm = onConfirm;
        _onCancel = onCancel;
    }

    @Override
    public Inventory createInventory()
    {
        return Bukkit.createInventory(null, 9);
    }

    @Override
    public void populateInventory(InventoryOpenEvent event)
    {
        var inventory = getInventory();
        int size = inventory.getSize();

        for (int i = 0; i < size; ++i)
        {
            if(i == (size / 2) - 1)
            {
                var yes = createYesButton();
                addButton(i, yes);
            } else if( i == (size / 2) + 1)
            {
                var no = createNoButton();
                addButton(i, no);
            } else {
                var thing = new GuiButton(plr -> new ItemStack(Material.GRAY_STAINED_GLASS), e -> {});
                addButton(i, thing);
            }
        }
        super.populateInventory(event);
    }

    public GuiButton createYesButton()
    {
        return new GuiButton(player -> new ItemStack(Material.GREEN_WOOL),
                event -> _onConfirm.run());
    }

    public GuiButton createNoButton()
    {
        return new GuiButton((player -> new ItemStack(Material.RED_WOOL)),
                event -> _onCancel.run());
    }

    @Override
    public void onClose(InventoryCloseEvent event)
    {
        SuperDarkCorePlugin.getInstance().getGuiManager().UnRegisterInventory(getInventory());
    }
}
