package net.superdark.minecraft.plugins.SuperDarkCore.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

public class GuiButton
{
    private Function<Player, ItemStack> _iconCreator;
    private Consumer<InventoryClickEvent> _consumer;

    public GuiButton(
            Function<Player, ItemStack> iconFunction,
            Consumer<InventoryClickEvent> consumer
    )
    {
        _iconCreator = iconFunction;
        _consumer = consumer;
    }

    public ItemStack getIcon(Player player)
    {
        return _iconCreator.apply(player);
    }

    public Consumer<InventoryClickEvent> getConsumer()
    {
        return _consumer;
    }
}
