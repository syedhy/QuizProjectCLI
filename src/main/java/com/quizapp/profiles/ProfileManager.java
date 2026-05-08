package com.quizapp.profiles;

import java.util.ArrayList;
import java.util.List;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.collection.Document;
import org.dizitart.no2.collection.NitriteCollection;
import org.dizitart.no2.filters.FluentFilter;
import org.dizitart.no2.mvstore.MVStoreModule;

import com.quizapp.data.AppData;

public class ProfileManager {
    private static Nitrite openDatabase() {
        AppData.ensureGeneratedDataFolder();

        MVStoreModule storeModule = MVStoreModule.withConfig()
                .filePath(AppData.DB_FILE.toString())
                .build();

        return Nitrite.builder()
                .loadModule(storeModule)
                .openOrCreate();
    }

    private static NitriteCollection getCollection(Nitrite db) {
        return db.getCollection("profiles");
    }

    public static void createProfile(String name) {
        try (Nitrite db = openDatabase()) {
            NitriteCollection collection = getCollection(db);

            Document existing = collection.find(FluentFilter.where("name").eq(name)).firstOrNull();

            if (existing != null) {
                return;
            }

            Profile profile = new Profile(name);
            collection.insert(toDocument(profile));
        }
    }

    public static Profile getProfile(String name) {
        try (Nitrite db = openDatabase()) {
            NitriteCollection collection = getCollection(db);

            Document document = collection.find(FluentFilter.where("name").eq(name)).firstOrNull();

            if (document == null) {
                return null;
            }

            return fromDocument(document);
        }
    }

    public static List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<>();

        try (Nitrite db = openDatabase()) {
            NitriteCollection collection = getCollection(db);

            for (Document document : collection.find()) {
                profiles.add(fromDocument(document));
            }
        }

        return profiles;
    }

    public static void saveProfile(Profile profile) {
        try (Nitrite db = openDatabase()) {
            NitriteCollection collection = getCollection(db);

            collection.remove(FluentFilter.where("name").eq(profile.getName()));
            collection.insert(toDocument(profile));
        }
    }

    public static void addGameResult(Profile profile , int score , boolean won) {
        profile.setGamesPlayed(profile.getGamesPlayed() + 1);
        profile.setTotalScore(profile.getTotalScore() + score);

        if (score > profile.getBestScore()) {
            profile.setBestScore(score);
        }

        if (won) {
            profile.setWins(profile.getWins() + 1);
        } else {
            profile.setLosses(profile.getLosses() + 1);
        }

        saveProfile(profile);
    }

    private static Document toDocument(Profile profile) {
        return Document.createDocument("name" , profile.getName())
                .put("gamesPlayed" , profile.getGamesPlayed())
                .put("wins" , profile.getWins())
                .put("losses" , profile.getLosses())
                .put("totalScore" , profile.getTotalScore())
                .put("bestScore" , profile.getBestScore())
                .put("elo" , profile.getElo());
    }

    private static Profile fromDocument(Document document) {
        Profile profile = new Profile(document.get("name" , String.class));

        profile.setGamesPlayed(document.get("gamesPlayed" , Integer.class));
        profile.setWins(document.get("wins" , Integer.class));
        profile.setLosses(document.get("losses" , Integer.class));
        profile.setTotalScore(document.get("totalScore" , Integer.class));
        profile.setBestScore(document.get("bestScore" , Integer.class));
        profile.setElo(document.get("elo" , Integer.class));

        return profile;
    }
}