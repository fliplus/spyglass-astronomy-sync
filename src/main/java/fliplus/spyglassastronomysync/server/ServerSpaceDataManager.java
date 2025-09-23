package fliplus.spyglassastronomysync.server;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServerSpaceDataManager {
    public static int revision = 0;

    public static void createDataFile(MinecraftServer server, long seedHash) {
        Path dataPath = getDataPath(server, seedHash);

        if (dataPath.toFile().exists()) return;

        try {
            Files.createDirectories(dataPath.getParent());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create data directory", e);
        }

        try {
            int SAVE_FORMAT = 1;
            int starCount = 1024;
            float yearLength = 8.0f;

            Files.writeString(dataPath, new StringBuilder()
                .append("Spyglass Astronomy - Format: ").append(SAVE_FORMAT)
                .append("\n---\n")
                .append(seedHash)
                .append("\n---")
                .append("\n---")
                .append("\n---")
                .append("\n---\n")
                .append(starCount).append(" ").append(yearLength)
                .append("\n---")
                .toString()
            );
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create data file", e);
        }
    }

    public static String getData(MinecraftServer server, long seedHash) {
        createDataFile(server, seedHash);

        Path dataPath = getDataPath(server, seedHash);

        try {
            return Files.readString(dataPath);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read data file", e);
        }
    }

    public static void saveData(String data, MinecraftServer server, long seedHash) {
        createDataFile(server, seedHash);

        Path dataPath = getDataPath(server, seedHash);

        try {
            Files.writeString(dataPath, data);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write data file", e);
        }

        revision++;
    }

    public static Path getDataPath(MinecraftServer server, long seedHash) {
        return server.getWorldPath(LevelResource.ROOT)
            .resolve("data")
            .resolve("spyglass_astronomy")
            .resolve(seedHash + ".txt");
    }

    public static boolean isDesynced(int revision) {
        return revision - 1 != ServerSpaceDataManager.revision;
    }
}
