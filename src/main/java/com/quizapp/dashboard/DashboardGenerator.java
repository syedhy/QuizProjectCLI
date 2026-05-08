package com.quizapp.dashboard;

import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;

import com.quizapp.profiles.Profile;
import com.quizapp.profiles.ProfileManager;

public class DashboardGenerator {

    public static void openDashboard(Profile profile) {
        try {
            File dashboardDir = new File("generated_data/dashboard");
            dashboardDir.mkdirs();

            String html = readResource("dashboard/dashboard.html");
            String css = readResource("dashboard/dashboard.css");
            String js = readResource("dashboard/dashboard.js");

            String eloImage;
            String eloMessage;

            if (profile.getElo() >= 1100) {
                eloImage = "images/elo_legend.png";
                eloMessage = "Ranked monster detected";
            } else if (profile.getElo() >= 1050) {
                eloImage = "images/elo_good.png";
                eloMessage = "Solid climb";
            } else if (profile.getElo() >= 900) {
                eloImage = "images/elo_mid.png";
                eloMessage = "Respectable";
            } else {
                eloImage = "images/elo_low.png";
                eloMessage = "The dashboard is being polite but the ELO is not";
            }

            int totalModeGames =
                    profile.getTimedGames()
                    + profile.getSurvivalGames()
                    + profile.getSuddenDeathGames()
                    + profile.getPvpGames()
                    + profile.getLlmGames()
                    + profile.getEloGames();

            int chartTotal = totalModeGames == 0 ? 1 : totalModeGames;

            html = replacePlaceholders(html , profile , eloImage , eloMessage , chartTotal);
            js = replacePlaceholders(js , profile , eloImage , eloMessage , chartTotal);

            Files.writeString(new File(dashboardDir , "dashboard.html").toPath() , html , StandardCharsets.UTF_8);
            Files.writeString(new File(dashboardDir , "dashboard.css").toPath() , css , StandardCharsets.UTF_8);
            Files.writeString(new File(dashboardDir , "dashboard.js").toPath() , js , StandardCharsets.UTF_8);

            copyImageIfExists("elo_legend.png");
            copyImageIfExists("elo_good.png");
            copyImageIfExists("elo_mid.png");
            copyImageIfExists("elo_low.png");

            Desktop.getDesktop().browse(new File(dashboardDir , "dashboard.html").toURI());

        } catch (Exception e) {
            System.out.println("Could not open dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String readResource(String path) throws Exception {
        InputStream inputStream = DashboardGenerator.class.getClassLoader().getResourceAsStream(path);

        if (inputStream == null) {
            throw new IllegalStateException("Missing resource: " + path);
        }

        return new String(inputStream.readAllBytes() , StandardCharsets.UTF_8);
    }

    private static void copyImageIfExists(String fileName) {
        try {
            InputStream inputStream = DashboardGenerator.class
                    .getClassLoader()
                    .getResourceAsStream("dashboard/images/" + fileName);

            if (inputStream == null) return;

            File imageDir = new File("generated_data/dashboard/images");
            imageDir.mkdirs();

            Files.write(new File(imageDir , fileName).toPath() , inputStream.readAllBytes());

        } catch (Exception e) {
            System.out.println("Could not copy image: " + fileName);
        }
    }

    private static String replacePlaceholders(
            String text ,
            Profile profile ,
            String eloImage ,
            String eloMessage ,
            int chartTotal
    ) {
        double rankedAvg = profile.getRankedAverageScore();
        double rankedWinRate = profile.getRankedWinRate();
        double overallAccuracy = profile.getOverallAccuracy();

        return text
                .replace("{{name}}" , escapeHtml(profile.getName()))
                .replace("{{elo}}" , String.valueOf(profile.getElo()))

                .replace("{{rankedWinRate}}" , String.format("%.1f" , rankedWinRate))
                .replace("{{rankedWinRateRaw}}" , String.valueOf(clamp(rankedWinRate)))

                .replace("{{overallAccuracy}}" , String.format("%.1f" , overallAccuracy))
                .replace("{{overallAccuracyRaw}}" , String.valueOf(clamp(overallAccuracy)))

                .replace("{{rankedAvg}}" , String.format("%.2f" , rankedAvg))
                .replace("{{rankedAvgRaw}}" , String.valueOf(clamp(rankedAvg * 20)))

                .replace("{{rankedWins}}" , String.valueOf(profile.getRankedWins()))
                .replace("{{rankedLosses}}" , String.valueOf(profile.getRankedLosses()))
                .replace("{{rankedBestScore}}" , String.valueOf(profile.getRankedBestScore()))
                .replace("{{rankedGames}}" , String.valueOf(profile.getRankedGames()))

                .replace("{{totalGames}}" , String.valueOf(profile.getTotalGames()))
                .replace("{{totalQuestions}}" , String.valueOf(profile.getTotalQuestions()))
                .replace("{{totalCorrect}}" , String.valueOf(profile.getTotalCorrect()))
                .replace("{{favoriteMode}}" , escapeHtml(profile.getFavoriteMode()))

                .replace("{{timedGames}}" , String.valueOf(profile.getTimedGames()))
                .replace("{{survivalGames}}" , String.valueOf(profile.getSurvivalGames()))
                .replace("{{suddenDeathGames}}" , String.valueOf(profile.getSuddenDeathGames()))
                .replace("{{pvpGames}}" , String.valueOf(profile.getPvpGames()))
                .replace("{{llmGames}}" , String.valueOf(profile.getLlmGames()))
                .replace("{{eloGames}}" , String.valueOf(profile.getEloGames()))

                .replace("{{timedPercent}}" , String.valueOf(percent(profile.getTimedGames() , chartTotal)))
                .replace("{{survivalPercent}}" , String.valueOf(percent(profile.getSurvivalGames() , chartTotal)))
                .replace("{{suddenPercent}}" , String.valueOf(percent(profile.getSuddenDeathGames() , chartTotal)))
                .replace("{{pvpPercent}}" , String.valueOf(percent(profile.getPvpGames() , chartTotal)))
                .replace("{{llmPercent}}" , String.valueOf(percent(profile.getLlmGames() , chartTotal)))
                .replace("{{eloPercent}}" , String.valueOf(percent(profile.getEloGames() , chartTotal)))

                .replace("{{leaderboardRows}}" , buildLeaderboardRows(profile))

                .replace("{{eloImage}}" , eloImage)
                .replace("{{eloMessage}}" , escapeHtml(eloMessage));
    }

    private static String buildLeaderboardRows(Profile currentProfile) {
        List<Profile> profiles = ProfileManager.getAllProfiles();

        profiles.sort(
                Comparator.comparingInt(Profile::getElo)
                        .reversed()
                        .thenComparing(Profile::getName)
        );

        StringBuilder rows = new StringBuilder();

        for (int i = 0; i < profiles.size(); i++) {
            Profile p = profiles.get(i);

            String rowClass = p.getName().equalsIgnoreCase(currentProfile.getName())
                    ? " class=\"current-user-row\""
                    : "";

            rows.append("<tr")
                    .append(rowClass)
                    .append(">")
                    .append("<td class=\"leaderboard-rank\">#")
                    .append(i + 1)
                    .append("</td>")
                    .append("<td>")
                    .append(escapeHtml(p.getName()))
                    .append("</td>")
                    .append("<td>")
                    .append(p.getElo())
                    .append("</td>")
                    .append("<td>")
                    .append(p.getRankedWins())
                    .append("W - ")
                    .append(p.getRankedLosses())
                    .append("L")
                    .append("</td>")
                    .append("<td>")
                    .append(String.format("%.1f" , p.getRankedWinRate()))
                    .append("%")
                    .append("</td>")
                    .append("<td>")
                    .append(p.getRankedBestScore())
                    .append("</td>")
                    .append("</tr>");
        }

        if (rows.isEmpty()) {
            rows.append("<tr><td colspan=\"6\">No profiles found yet</td></tr>");
        }

        return rows.toString();
    }

    private static double clamp(double value) {
        return Math.min(100 , Math.max(0 , value));
    }

    private static int percent(int value , int total) {
        if (total <= 0) return 0;
        if (value == 0) return 0;

        return Math.max(6 , (int) Math.round((value * 100.0) / total));
    }

    private static String escapeHtml(String text) {
        if (text == null) return "";

        return text
                .replace("&" , "&amp;")
                .replace("<" , "&lt;")
                .replace(">" , "&gt;")
                .replace("\"" , "&quot;")
                .replace("'" , "&#39;");
    }
}