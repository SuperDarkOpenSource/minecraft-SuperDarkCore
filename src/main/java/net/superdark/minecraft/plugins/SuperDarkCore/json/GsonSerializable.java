package net.superdark.minecraft.plugins.SuperDarkCore.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public interface GsonSerializable
{
    default void saveToDisk(JavaPlugin plugin, String fileName, Object source)
    {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setPrettyPrinting();
        var gson = gsonBuilder.create();

        try{
            var absoluteDataPath = plugin.getDataFolder().getAbsolutePath();
            Path dataFolder = Path.of(absoluteDataPath);
            if(!dataFolder.toFile().exists())
            {
                Files.createDirectories(dataFolder);
            }

            String jsonString = gson.toJson(source);

            // Creates the file if it does not exist.
            Files.writeString(Path.of(absoluteDataPath, fileName), jsonString);
        } catch (IOException e) {
            plugin.getLogger().severe("A file could not be saved to with name: " + fileName + ". Reason: " + e.getMessage());
        }
    }

    /**
     *
     * @param plugin Reference to the plugin. Used for getting the data folder path and logging.
     * @param fileName the name of the file stupid
     * @param clazz Class for type information
     * @return An object of type T, which will hold data loaded from disk. Throws an exception if not read correctly.
     * @param <T> Data Type.
     */
    default <T> T readFromDisk(JavaPlugin plugin, String fileName, Class<T> clazz) throws IOException {
        Gson gson = new Gson();
        T out = null;
        var jsonString = Files.readString(Path.of(plugin.getDataFolder().getAbsolutePath(), fileName));
        out = gson.fromJson(jsonString, clazz);
        plugin.getLogger().info("Loaded file " + fileName + " from disk.");

        return out;
    }
}
