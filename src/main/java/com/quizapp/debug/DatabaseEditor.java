package com.quizapp.debug;

import java.util.Scanner;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.collection.Document;
import org.dizitart.no2.collection.NitriteCollection;
import org.dizitart.no2.filters.FluentFilter;
import org.dizitart.no2.mvstore.MVStoreModule;

import com.quizapp.data.AppData;

public class DatabaseEditor {

    public static void main(String[] args) {

        AppData.ensureGeneratedDataFolder();

        MVStoreModule storeModule = MVStoreModule.withConfig()
                .filePath(AppData.DB_FILE.toString())
                .build();

        Nitrite db = Nitrite.builder()
                .loadModule(storeModule)
                .openOrCreate();

        NitriteCollection profiles = db.getCollection("profiles");

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n=== DATABASE EDITOR ===");
            System.out.println("1. View Profiles");
            System.out.println("2. Delete Profile");
            System.out.println("3. Edit Profile");
            System.out.println("4. Reset Database");
            System.out.println("5. Exit");

            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> {

                    System.out.println("\n=== Profiles ===\n");

                    for (Document doc : profiles.find()) {

                        System.out.println(
                            doc.get("name" , String.class)
                            + " | ELO: " + getInt(doc , "elo")
                            + " | Ranked: "
                            + getInt(doc , "rankedWins") + "W - "
                            + getInt(doc , "rankedLosses") + "L"
                            + " | Avg Score: "
                            + getInt(doc , "rankedTotalScore")
                        );
                    }
                }

                case 2 -> {

                    System.out.print("Enter profile name: ");
                    String name = sc.nextLine();

                    profiles.remove(
                            FluentFilter.where("name").eq(name)
                    );

                    System.out.println("Profile deleted");
                }

                case 3 -> {

                    System.out.print("Enter profile name: ");
                    String name = sc.nextLine();

                    Document doc = profiles.find(
                            FluentFilter.where("name").eq(name)
                    ).firstOrNull();

                    if (doc == null) {
                        System.out.println("Profile not found");
                        break;
                    }

                    while (true) {

                        System.out.println("\n=== Editing: " + name + " ===");

                        System.out.println("1. Name");
                        System.out.println("2. ELO");
                        System.out.println("3. Ranked Wins");
                        System.out.println("4. Ranked Losses");
                        System.out.println("5. Ranked Games");
                        System.out.println("6. Ranked Best Score");
                        System.out.println("7. Ranked Total Score");
                        System.out.println("8. Total Games");
                        System.out.println("9. Total Questions");
                        System.out.println("10. Total Correct");
                        System.out.println("11. Timed Games");
                        System.out.println("12. Survival Games");
                        System.out.println("13. Sudden Death Games");
                        System.out.println("14. PvP Games");
                        System.out.println("15. LLM Games");
                        System.out.println("16. ELO Games");
                        System.out.println("17. Back");

                        System.out.print("Choice: ");
                        int field = sc.nextInt();
                        sc.nextLine();

                        if (field == 17) {
                            break;
                        }

                        String key = "";

                        switch (field) {

                            case 1 -> key = "name";
                            case 2 -> key = "elo";
                            case 3 -> key = "rankedWins";
                            case 4 -> key = "rankedLosses";
                            case 5 -> key = "rankedGames";
                            case 6 -> key = "rankedBestScore";
                            case 7 -> key = "rankedTotalScore";

                            case 8 -> key = "totalGames";
                            case 9 -> key = "totalQuestions";
                            case 10 -> key = "totalCorrect";

                            case 11 -> key = "timedGames";
                            case 12 -> key = "survivalGames";
                            case 13 -> key = "suddenDeathGames";
                            case 14 -> key = "pvpGames";
                            case 15 -> key = "llmGames";
                            case 16 -> key = "eloGames";

                            default -> {
                                System.out.println("Invalid choice");
                                continue;
                            }
                        }

                        if (field == 1) {

                            System.out.print("New value: ");
                            String value = sc.nextLine();

                            doc.put(key , value);

                        } else {

                            System.out.print("New value: ");
                            int value = sc.nextInt();
                            sc.nextLine();

                            doc.put(key , value);
                        }

                        profiles.update(
                                FluentFilter.where("name").eq(name),
                                doc
                        );

                        System.out.println("Updated successfully");
                    }
                }

                case 4 -> {

                    profiles.remove(
                            FluentFilter.where("name").notEq("")
                    );

                    System.out.println("Database cleared");
                }

                case 5 -> {

                    db.close();
                    sc.close();

                    System.out.println("Goodbye");
                    return;
                }

                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static int getInt(Document doc , String key) {

        Integer value = doc.get(key , Integer.class);

        if (value == null) {
            return 0;
        }

        return value;
    }
}