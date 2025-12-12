package net.superdark.minecraft.plugins.SuperDarkCore.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import java.lang.reflect.Type;
import java.util.Map;

public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        // Treat null / AIR as null
        if (src == null || src.getType() == Material.AIR) {
            return JsonNull.INSTANCE;
        }

        Map<String, Object> data = src.serialize();
        return context.serialize(data);
    }

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        if (json == null || json.isJsonNull()) {
            return new ItemStack(Material.AIR);
        }

        Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
        Map<String, Object> data = context.deserialize(json, mapType);

        return ItemStack.deserialize(data);
    }
}
