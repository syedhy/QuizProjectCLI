package com.quizapp.dashboard;

import java.awt.Desktop;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import com.quizapp.data.AppData;
import com.quizapp.profiles.Profile;
import com.quizapp.profiles.ProfileManager;

public class DashboardGenerator {

    public static void openDashboard(Profile currentProfile) {
        try {
            AppData.ensureGeneratedDataFolder();

            Path dashboardPath = AppData.GENERATED_DATA_DIR.resolve("dashboard.html").toAbsolutePath();

            String html = buildHtml(currentProfile);

            Files.writeString(dashboardPath , html);

            System.out.println("Dashboard generated at:");
            System.out.println(dashboardPath);

            openFile(dashboardPath);

        } catch (Exception e) {
            System.out.println("Could not open dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void openFile(Path dashboardPath) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(dashboardPath.toUri());
                return;
            }

            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("mac")) {
                new ProcessBuilder("open" , dashboardPath.toString()).start();
            } else if (os.contains("win")) {
                new ProcessBuilder("cmd" , "/c" , "start" , "" , dashboardPath.toString()).start();
            } else {
                new ProcessBuilder("xdg-open" , dashboardPath.toString()).start();
            }

        } catch (Exception e) {
            System.out.println("Open this file manually:");
            System.out.println(dashboardPath);
        }
    }

    private static String buildHtml(Profile currentProfile) {
        List<Profile> profiles = ProfileManager.getAllProfiles();
        profiles.sort(Comparator.comparingInt(Profile::getElo).reversed());

        int rank = 1;

        for (int i = 0; i < profiles.size(); i++) {
            if (profiles.get(i).getName().equals(currentProfile.getName())) {
                rank = i + 1;
                break;
            }
        }

        double winRate = currentProfile.getGamesPlayed() == 0
                ? 0
                : (currentProfile.getWins() * 100.0) / currentProfile.getGamesPlayed();

        double averageScore = currentProfile.getGamesPlayed() == 0
                ? 0
                : (currentProfile.getTotalScore() * 1.0) / currentProfile.getGamesPlayed();

        StringBuilder rows = new StringBuilder();

        for (int i = 0; i < profiles.size(); i++) {
            Profile p = profiles.get(i);

            rows.append("<tr>")
                .append("<td>#").append(i + 1).append("</td>")
                .append("<td>").append(escapeHtml(p.getName())).append("</td>")
                .append("<td>").append(p.getElo()).append("</td>")
                .append("<td>").append(p.getGamesPlayed()).append("</td>")
                .append("<td>").append(p.getWins()).append("</td>")
                .append("<td>").append(p.getLosses()).append("</td>")
                .append("<td>").append(p.getBestScore()).append("</td>")
                .append("</tr>");
        }

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Quiz Dashboard</title>

                <style>
                    * {
                        box-sizing: border-box;
                    }

                    body {
                        margin: 0;
                        font-family: Arial , sans-serif;
                        background: radial-gradient(circle at top , #164e63 , #020617 55%);
                        color: #e5e7eb;
                    }

                    .container {
                        max-width: 1180px;
                        margin: auto;
                        padding: 40px;
                    }

                    .hero {
                        background: linear-gradient(135deg , #0891b2 , #7c3aed);
                        padding: 34px;
                        border-radius: 28px;
                        margin-bottom: 24px;
                        box-shadow: 0 20px 60px rgba(0 , 0 , 0 , 0.35);
                    }

                    .hero h1 {
                        margin: 0;
                        font-size: 46px;
                        letter-spacing: -1px;
                    }

                    .hero p {
                        margin-bottom: 0;
                        color: #e0f2fe;
                        font-size: 18px;
                    }

                    .grid {
                        display: grid;
                        grid-template-columns: repeat(4 , 1fr);
                        gap: 16px;
                        margin-bottom: 24px;
                    }

                    .chart-grid {
                        display: grid;
                        grid-template-columns: repeat(2 , 1fr);
                        gap: 16px;
                        margin-bottom: 24px;
                    }

                    .card {
                        background: rgba(15 , 23 , 42 , 0.88);
                        border: 1px solid #334155;
                        border-radius: 20px;
                        padding: 22px;
                        box-shadow: 0 12px 30px rgba(0 , 0 , 0 , 0.25);
                    }

                    .card span {
                        color: #94a3b8;
                        font-size: 14px;
                    }

                    .card h2 {
                        margin: 10px 0 0;
                        font-size: 32px;
                    }

                    .section {
                        background: rgba(15 , 23 , 42 , 0.88);
                        border: 1px solid #334155;
                        border-radius: 24px;
                        padding: 24px;
                        margin-bottom: 24px;
                        box-shadow: 0 12px 30px rgba(0 , 0 , 0 , 0.25);
                    }

                    .section h2 {
                        margin-top: 0;
                    }

                    .bar {
                        height: 14px;
                        border-radius: 999px;
                        background: #1e293b;
                        overflow: hidden;
                        margin-top: 12px;
                    }

                    .bar-fill {
                        height: 100%;
                        background: linear-gradient(90deg , #22d3ee , #a78bfa);
                    }

                    table {
                        width: 100%;
                        border-collapse: collapse;
                        overflow: hidden;
                        border-radius: 18px;
                    }

                    th , td {
                        padding: 15px;
                        text-align: left;
                        border-bottom: 1px solid #334155;
                    }

                    th {
                        background: #1e293b;
                        color: #67e8f9;
                    }

                    tr:hover {
                        background: #1f2937;
                    }

                    canvas {
                        max-height: 280px;
                    }

                    .muted {
                        color: #94a3b8;
                    }

                    .footer {
                        text-align: center;
                        color: #64748b;
                        margin-top: 24px;
                    }

                    @media(max-width: 900px) {
                        .grid {
                            grid-template-columns: repeat(2 , 1fr);
                        }

                        .chart-grid {
                            grid-template-columns: 1fr;
                        }
                    }

                    @media(max-width: 560px) {
                        .grid {
                            grid-template-columns: 1fr;
                        }

                        .container {
                            padding: 20px;
                        }

                        .hero h1 {
                            font-size: 34px;
                        }
                    }
                </style>
            </head>

            <body>
                <div class="container">
                    <div class="hero">
                        <h1>Quiz Project Dashboard</h1>
                        <p>Current Profile: {{name}}</p>
                    </div>

                    <div class="grid">
                        <div class="card">
                            <span>Rank</span>
                            <h2>#{{rank}}</h2>
                        </div>

                        <div class="card">
                            <span>ELO Rating</span>
                            <h2>{{elo}}</h2>
                        </div>

                        <div class="card">
                            <span>Games Played</span>
                            <h2>{{games}}</h2>
                        </div>

                        <div class="card">
                            <span>Best Score</span>
                            <h2>{{bestScore}}</h2>
                        </div>
                    </div>

                    <div class="grid">
                        <div class="card">
                            <span>Wins</span>
                            <h2>{{wins}}</h2>
                        </div>

                        <div class="card">
                            <span>Losses</span>
                            <h2>{{losses}}</h2>
                        </div>

                        <div class="card">
                            <span>Win Rate</span>
                            <h2>{{winRate}}%</h2>
                        </div>

                        <div class="card">
                            <span>Average Score</span>
                            <h2>{{averageScore}}</h2>
                        </div>
                    </div>

                    <div class="section">
                        <h2>Leaderboard</h2>

                        <table>
                            <tr>
                                <th>Rank</th>
                                <th>Profile</th>
                                <th>ELO</th>
                                <th>Games</th>
                                <th>Wins</th>
                                <th>Losses</th>
                                <th>Best Score</th>
                            </tr>

                            {{rows}}
                        </table>
                    </div>

                    <div class="chart-grid">
                        <div class="section">
                            <h2>Win / Loss Split</h2>
                            <canvas id="winChart"></canvas>
                        </div>

                        <div class="section">
                            <h2>ELO Progress</h2>
                            <canvas id="eloChart"></canvas>
                        </div>
                    </div>

                    <div class="section">
                        <h2>Performance Breakdown</h2>

                        <div style="margin-top:20px;">
                            <div style="display:flex;justify-content:space-between;">
                                <span>Win Rate</span>
                                <span>{{winRate}}%</span>
                            </div>

                            <div class="bar">
                                <div class="bar-fill" style="width: {{winRateRaw}}%;"></div>
                            </div>
                        </div>

                        <div style="margin-top:25px;">
                            <div style="display:flex;justify-content:space-between;">
                                <span>Average Score</span>
                                <span>{{averageScore}}</span>
                            </div>

                            <div class="bar">
                                <div class="bar-fill" style="width: {{averageScoreBar}}%;"></div>
                            </div>
                        </div>
                    </div>

                    <div class="footer">
                        Generated locally from QuizProjectMaven
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

                <script>
                    const wins = Number("{{wins}}");
                    const losses = Number("{{losses}}");
                    const elo = Number("{{elo}}");

                    new Chart(document.getElementById("winChart") , {
                        type: "doughnut" ,
                        data: {
                            labels: ["Wins" , "Losses"] ,
                            datasets: [{
                                data: [wins , losses] ,
                                backgroundColor: ["#22d3ee" , "#7c3aed"] ,
                                borderColor: "#020617" ,
                                borderWidth: 4
                            }]
                        } ,
                        options: {
                            plugins: {
                                legend: {
                                    labels: {
                                        color: "#e5e7eb"
                                    }
                                }
                            }
                        }
                    });

                    new Chart(document.getElementById("eloChart") , {
                        type: "line" ,
                        data: {
                            labels: ["Start" , "Current"] ,
                            datasets: [{
                                label: "ELO" ,
                                data: [1000 , elo] ,
                                borderColor: "#22d3ee" ,
                                backgroundColor: "rgba(34 , 211 , 238 , 0.18)" ,
                                fill: true ,
                                tension: 0.35
                            }]
                        } ,
                        options: {
                            scales: {
                                x: {
                                    ticks: {
                                        color: "#e5e7eb"
                                    } ,
                                    grid: {
                                        color: "#334155"
                                    }
                                } ,
                                y: {
                                    ticks: {
                                        color: "#e5e7eb"
                                    } ,
                                    grid: {
                                        color: "#334155"
                                    }
                                }
                            } ,
                            plugins: {
                                legend: {
                                    labels: {
                                        color: "#e5e7eb"
                                    }
                                }
                            }
                        }
                    });
                </script>
            </body>
            </html>
            """
            .replace("{{name}}" , escapeHtml(currentProfile.getName()))
            .replace("{{rank}}" , String.valueOf(rank))
            .replace("{{elo}}" , String.valueOf(currentProfile.getElo()))
            .replace("{{games}}" , String.valueOf(currentProfile.getGamesPlayed()))
            .replace("{{wins}}" , String.valueOf(currentProfile.getWins()))
            .replace("{{losses}}" , String.valueOf(currentProfile.getLosses()))
            .replace("{{bestScore}}" , String.valueOf(currentProfile.getBestScore()))
            .replace("{{winRate}}" , String.format("%.1f" , winRate))
            .replace("{{winRateRaw}}" , String.valueOf(Math.min(100 , Math.max(0 , winRate))))
            .replace("{{averageScore}}" , String.format("%.1f" , averageScore))
            .replace("{{averageScoreBar}}" , String.valueOf(Math.min(100 , Math.max(0 , averageScore * 10))))
            .replace("{{rows}}" , rows.toString());
    }

    private static String escapeHtml(String text) {
        if (text == null) {
            return "";
        }

        return text
                .replace("&" , "&amp;")
                .replace("<" , "&lt;")
                .replace(">" , "&gt;")
                .replace("\"" , "&quot;")
                .replace("'" , "&#39;");
    }
}