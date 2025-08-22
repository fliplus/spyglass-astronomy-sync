package fliplus.spyglassastronomysync.server;

import fliplus.spyglassastronomysync.mixin.BiomeManagerAccessor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ServerSpaceDataManager {
    public static int revision = 0;

    public static List<ServerPlayer> players = new ArrayList<>();

    public static void addPlayer(ServerPlayer player) {
        players.add(player);
    }

    public static void removePlayer(ServerPlayer player) {
        players.remove(player);
    }

    public static void createDataFile(MinecraftServer server) {
        Path dataPath = getDataPath(server);

        if (dataPath.toFile().exists()) return;

        try {
            Files.createDirectories(dataPath.getParent());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create data directory", e);
        }

        try {
            int SAVE_FORMAT = 1;
            long seedHash = ((BiomeManagerAccessor) server.overworld().getBiomeManager()).getBiomeZoomSeed();
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

    public static Path getDataPath(MinecraftServer server) {
        return server.getWorldPath(LevelResource.ROOT)
            .resolve("data")
            .resolve("spyglass_astronomy")
            .resolve("spyglass_astronomy_sync.txt");
    }

    public static String getData(MinecraftServer server) {
        createDataFile(server);

        Path dataPath = getDataPath(server);

        try {
            return Files.readString(dataPath);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read data file", e);
        }
    }

    public static void saveData(String data, MinecraftServer server) {
        createDataFile(server);

        Path dataPath = getDataPath(server);

        try {
            Files.writeString(dataPath, data);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write data file", e);
        }

        revision++;
    }

    public static boolean isDesynced(int revision) {
        if (revision - 1 != ServerSpaceDataManager.revision) return true;
        return false;
    }
}
