package dev.fliplus.spyglassastronomysync.server.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import dev.fliplus.spyglassastronomysync.SpyglassAstronomySync;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

public class SpyglassAstronomySyncConfig {
    public boolean allowAdminCommands = false;

    private static SpyglassAstronomySyncConfig instance = new SpyglassAstronomySyncConfig();

    private static final Path configDirectory = FabricLoader.getInstance().getConfigDir();
    private static final Path configPath = configDirectory.resolve(SpyglassAstronomySync.MOD_ID + ".json");

    private static final Gson GSON = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithModifiers(Modifier.FINAL)
        .setPrettyPrinting()
        .create();

    public static SpyglassAstronomySyncConfig instance() {
        return instance;
    }

    public static boolean load() {
        if (!Files.exists(configPath)) {
            instance = new SpyglassAstronomySyncConfig();
            save();
            return true;
        }

        try (FileReader reader = new FileReader(configPath.toFile())) {
            SpyglassAstronomySyncConfig loaded = GSON.fromJson(reader, SpyglassAstronomySyncConfig.class);
            if (loaded != null) instance = loaded;
            save();
            return true;
        } catch (IOException | JsonSyntaxException e) {
            SpyglassAstronomySync.LOGGER.error("Failed to load {}", configPath.getFileName(), e);
            return false;
        }
    }

    public static void save() {
        try {
            if (!Files.exists(configDirectory)) Files.createDirectories(configDirectory);
            Files.writeString(configPath, GSON.toJson(instance));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write " + configPath.getFileName(), e);
        }
    }
}
