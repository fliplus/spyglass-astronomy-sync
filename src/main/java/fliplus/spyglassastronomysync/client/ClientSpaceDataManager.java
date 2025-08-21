package fliplus.spyglassastronomysync.client;

import com.nettakrim.spyglass_astronomy.*;

import java.util.ArrayList;
import java.util.Base64;

public class ClientSpaceDataManager {
    public static final int SAVE_FORMAT = 1;

    public static int revision;

    public static void loadData(String data) {
        SpaceDataManager spaceDataManager = SpyglassAstronomyClient.spaceDataManager;

        int stage = 0;
        Base64.Decoder decoder = Base64.getDecoder();
        int starIndex = 0;
        spaceDataManager.starDatas = new ArrayList<>();
        spaceDataManager.orbitingBodyDatas = new ArrayList<>();

        for (String line : data.lines().toArray(String[]::new)) {
            if (line.equals("---")) {
                stage++;
                continue;
            }
            switch (stage) {
                case 1 -> {
                    String[] seeds = line.split(" ");
                    if (seeds.length == 1) {
                        spaceDataManager.setStarSeed(Long.parseLong(line));
                        spaceDataManager.setPlanetSeed(spaceDataManager.getStarSeed());
                    } else {
                        spaceDataManager.setStarSeed(Long.parseLong(seeds[0]));
                        spaceDataManager.setPlanetSeed(Long.parseLong(seeds[1]));
                    }
                }
                case 2 -> {
                    String[] constellationParts = line.split(" \\| ");
                    SpyglassAstronomyClient.constellations.add(SpaceDataManager.decodeConstellation(decoder, constellationParts[0], constellationParts[1]));
                }
                case 3 -> {
                    int starSplit = line.indexOf(' ');
                    starIndex += Integer.parseInt(line.substring(0, starSplit));
                    String starName = line.substring(starSplit + 1);
                    spaceDataManager.starDatas.add(new SpaceDataManager.StarData(starIndex, starName));
                }
                case 4 -> {
                    int orbitingBodySplit = line.indexOf(' ');
                    int orbitingBodyIndex = Integer.parseInt(line.substring(0, orbitingBodySplit));
                    String orbitingBodyName = line.substring(orbitingBodySplit + 1);
                    spaceDataManager.orbitingBodyDatas.add(new SpaceDataManager.OrbitingBodyData(orbitingBodyIndex, orbitingBodyName));
                }
                case 5 -> {
                    String[] parts = line.split(" ");
                    SpyglassAstronomyClient.setStarCount(Integer.parseInt(parts[0]));
                    if (parts.length > 1) spaceDataManager.setYearLength(Float.parseFloat(parts[1]));
                    else spaceDataManager.setYearLength(8);
                }
            }
        }
    }

    public static String dataToString() {
        SpaceDataManager spaceDataManager = SpyglassAstronomyClient.spaceDataManager;

        StringBuilder s = new StringBuilder("Spyglass Astronomy - Format: " + SAVE_FORMAT);
        s.append("\n---\n");
        s.append(spaceDataManager.getStarSeed());
        if (spaceDataManager.getPlanetSeed() != spaceDataManager.getStarSeed()) {
            s.append(' ');
            s.append(spaceDataManager.getPlanetSeed());
        }
        s.append("\n---");
        Base64.Encoder encoder = Base64.getEncoder();
        for (Constellation constellation : SpyglassAstronomyClient.constellations) {
            s.append('\n');
            s.append(SpaceDataManager.encodeConstellation(encoder, constellation));
        }
        s.append("\n---");
        int lastIndex = 0;
        for (Star star : SpyglassAstronomyClient.stars) {
            if (!star.isUnnamed()) {
                s.append('\n');
                s.append(star.index - lastIndex).append(" ").append(star.name);
                lastIndex = star.index;
            }
        }
        s.append("\n---");
        int index = 0;
        for (OrbitingBody orbitingBody : SpyglassAstronomyClient.orbitingBodies) {
            if (!orbitingBody.isUnnamed()) {
                s.append('\n');
                s.append(index).append(" ").append(orbitingBody.name);
            }
            index++;
        }
        s.append("\n---\n");
        s.append(SpyglassAstronomyClient.getStarCount());
        s.append(" ");
        s.append(spaceDataManager.getYearLength());
        s.append("\n---");

        return s.toString();
    }
}
