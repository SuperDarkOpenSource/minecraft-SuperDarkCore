package net.superdark.minecraft.plugins.SuperDarkCore.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public interface IGsonSerializable
{
    default void saveToDisk(JavaPlugin plugin, String fileName, String... extraPaths)
    {
        var gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(ItemStack.class, new ItemStackAdapter())
                .create();

        try{
            var absoluteDataPath = plugin.getDataFolder().getAbsolutePath();
            Path dataFolder = Path.of(absoluteDataPath, extraPaths);

            if(!dataFolder.toFile().exists())
            {
                Files.createDirectories(dataFolder);
            }

            String jsonString = gson.toJson(this);

            // Creates the file if it does not exist.
            Files.writeString(dataFolder.resolve(fileName), jsonString);
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
    static <T> T readFromDisk(JavaPlugin plugin, String fileName, Class<T> clazz, String... extraPaths) throws IOException {
        Gson gson = new Gson();
        T out = null;
        var dataFile = Path.of(plugin.getDataFolder().getAbsolutePath(), extraPaths).resolve(fileName);
        var jsonString = Files.readString(dataFile);
        out = gson.fromJson(jsonString, clazz);
        plugin.getLogger().info("Loaded file " + fileName + " from disk.");
        return out;
    }

    static <T> List<T> readFromDisk(JavaPlugin plugin, Class<T> clazz, String... extraPaths) throws IOException
    {
        Gson gson = new Gson();
        var out = new ArrayList<T>();
        var where = Path.of(plugin.getDataFolder().getAbsolutePath(), extraPaths);

        // suppress the stupid warning, doesn't take into account throwing the exception up the stack
        @SuppressWarnings("resource")
        var files = Files.walk(where)
                .filter(Files::isRegularFile)
                .filter(path -> path.endsWith(".json"))
                .toList();

        for (var file : files)
        {
            var jsonString = Files.readString(file);
            out.add(gson.fromJson(jsonString, clazz));
        }

        return out;
    }

}
