package fliplus.spyglassastronomysync.client.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fliplus.spyglassastronomysync.SpyglassAstronomySync;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

public class SpyglassAstronomySyncConfig {
    public boolean AllowAdminCommands = false;

    private static final Path configPath = FabricLoader.getInstance().getConfigDir()
            .resolve("spyglass-astronomy-sync.json");

    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .excludeFieldsWithModifiers(Modifier.FINAL)
            .setPrettyPrinting()
            .create();

    public static SpyglassAstronomySyncConfig loadConfig() {
        if (!Files.exists(configPath)) {
            saveConfig(new SpyglassAstronomySyncConfig());
        }

        try (FileReader reader = new FileReader(configPath.toFile())) {
            SpyglassAstronomySyncConfig config = GSON.fromJson(reader, SpyglassAstronomySyncConfig.class);
            if (config == null) config = new SpyglassAstronomySyncConfig();
            saveConfig(config);
            return config;
        } catch (IOException e) {
            SpyglassAstronomySync.LOGGER.error("Failed to load " + configPath.getFileName(), e);
            SpyglassAstronomySync.LOGGER.error("Using default configuration instead");
            return new SpyglassAstronomySyncConfig();
        }
    }

    public static void saveConfig(SpyglassAstronomySyncConfig config) {
        try {
            Path configDirecotry = configPath.getParent();
            if (!Files.exists(configDirecotry)) Files.createDirectories(configDirecotry);

            Files.writeString(configPath, GSON.toJson(config));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write configuration file", e);
        }
    }
}
