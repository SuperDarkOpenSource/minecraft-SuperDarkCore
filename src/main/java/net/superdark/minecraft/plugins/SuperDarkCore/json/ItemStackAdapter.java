package net.superdark.minecraft.plugins.SuperDarkCore.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Map;

public final class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack>
{
    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context)
    {
        try
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeObject(src);
            dataOutput.close();

            return new JsonPrimitive(Base64Coder.encodeLines(outputStream.toByteArray()));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        try
        {
            if(!json.isJsonPrimitive())
            {
                System.out.println("Tried decoding from a non-json primitive. This is not allowed.");
            }
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(json.getAsString()));
            BukkitObjectInputStream bukkitInputStream = new BukkitObjectInputStream(inputStream);

            return (ItemStack) bukkitInputStream.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
