package com.quizapp.dashboard;

import java.awt.Desktop;
import java.io.File;
import java.io.PrintWriter;

import com.quizapp.profiles.Profile;

public class DashboardGenerator {

    public static void openDashboard(Profile profile) {
        try {
            File dashboard = new File("generated_data/dashboard.html");
            dashboard.getParentFile().mkdirs();

            double rankedAvg = profile.getRankedAverageScore();
            double rankedWinRate = profile.getRankedWinRate();
            double overallAccuracy = profile.getOverallAccuracy();

            int timedGames = profile.getTimedGames();
            int survivalGames = profile.getSurvivalGames();
            int suddenDeathGames = profile.getSuddenDeathGames();
            int pvpGames = profile.getPvpGames();
            int llmGames = profile.getLlmGames();
            int eloGames = profile.getEloGames();

            int totalModeGames = timedGames + survivalGames + suddenDeathGames + pvpGames + llmGames + eloGames;
            int chartTotal = totalModeGames == 0 ? 1 : totalModeGames;

            String eloImage;
            String eloMessage;

            if (profile.getElo() >= 1400) {
                eloImage = "images/elo_legend.png";
                eloMessage = "Ranked monster detected , this profile is not here to play around";
            } else if (profile.getElo() >= 1100) {
                eloImage = "images/elo_good.png";
                eloMessage = "Solid climb , the ranked arc is looking strong";
            } else if (profile.getElo() >= 900) {
                eloImage = "images/elo_mid.png";
                eloMessage = "Respectable , some wins , some pain , mostly character development";
            } else {
                eloImage = "images/elo_low.png";
                eloMessage = "The dashboard is being polite , the ELO is not";
            }

            String html = """
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width , initial-scale=1.0">
<title>Quiz Project Dashboard</title>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<style>
:root {
    --bg: #050816;
    --panel: rgba(15 , 23 , 42 , 0.82);
    --panel2: rgba(30 , 41 , 59 , 0.72);
    --border: rgba(148 , 163 , 184 , 0.18);
    --text: #e5e7eb;
    --muted: #94a3b8;
    --cyan: #22d3ee;
    --purple: #a855f7;
    --green: #22c55e;
    --orange: #fb923c;
    --pink: #e879f9;
    --blue: #38bdf8;
}

* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    min-height: 100vh;
    font-family: Inter , Arial , sans-serif;
    color: var(--text);
    background:
        radial-gradient(circle at 15% 10% , rgba(34 , 211 , 238 , 0.22) , transparent 28%),
        radial-gradient(circle at 85% 5% , rgba(168 , 85 , 247 , 0.28) , transparent 26%),
        radial-gradient(circle at 50% 100% , rgba(14 , 165 , 233 , 0.14) , transparent 32%),
        var(--bg);
    padding: 34px;
}

.container {
    max-width: 1480px;
    margin: auto;
}

.hero {
    position: relative;
    overflow: hidden;
    border-radius: 34px;
    padding: 42px;
    margin-bottom: 28px;
    background:
        linear-gradient(135deg , rgba(34 , 211 , 238 , 0.95) , rgba(99 , 102 , 241 , 0.95) , rgba(168 , 85 , 247 , 0.95));
    box-shadow: 0 30px 90px rgba(0 , 0 , 0 , 0.45);
}

.hero::after {
    content: "";
    position: absolute;
    width: 420px;
    height: 420px;
    right: -120px;
    top: -170px;
    border-radius: 999px;
    background: rgba(255 , 255 , 255 , 0.18);
}

.hero-content {
    position: relative;
    z-index: 2;
    display: flex;
    justify-content: space-between;
    gap: 24px;
    align-items: center;
}

.hero h1 {
    font-size: 58px;
    letter-spacing: -2px;
    margin-bottom: 8px;
}

.hero p {
    font-size: 18px;
    color: #e0f2fe;
}

.hero-badge {
    background: rgba(2 , 6 , 23 , 0.25);
    border: 1px solid rgba(255 , 255 , 255 , 0.22);
    border-radius: 24px;
    padding: 20px 24px;
    min-width: 230px;
}

.hero-badge span {
    display: block;
    color: #dbeafe;
    font-size: 14px;
    margin-bottom: 6px;
}

.hero-badge strong {
    font-size: 42px;
}

.top-grid {
    display: grid;
    grid-template-columns: 1.2fr 0.8fr 1fr;
    gap: 22px;
    margin-bottom: 22px;
}

.grid {
    display: grid;
    grid-template-columns: repeat(2 , 1fr);
    gap: 22px;
}

.card {
    background: var(--panel);
    border: 1px solid var(--border);
    border-radius: 28px;
    padding: 26px;
    box-shadow: 0 18px 50px rgba(0 , 0 , 0 , 0.32);
    backdrop-filter: blur(18px);
}

.card h2 {
    color: #67e8f9;
    font-size: 24px;
    margin-bottom: 22px;
    letter-spacing: -0.4px;
}

.big-number {
    font-size: 54px;
    font-weight: 900;
    letter-spacing: -2px;
    margin-bottom: 20px;
}

.subtle {
    color: var(--muted);
    font-size: 14px;
    margin-top: 16px;
    line-height: 1.5;
}

.stat-row {
    margin-bottom: 18px;
}

.stat-label {
    display: flex;
    justify-content: space-between;
    font-size: 14px;
    color: #cbd5e1;
    margin-bottom: 8px;
}

.bar {
    height: 12px;
    background: rgba(51 , 65 , 85 , 0.78);
    border-radius: 999px;
    overflow: hidden;
}

.fill {
    height: 100%;
    border-radius: 999px;
}

.fill-cyan {
    background: linear-gradient(90deg , var(--cyan) , #67e8f9);
}

.fill-green {
    background: linear-gradient(90deg , var(--green) , #86efac);
}

.fill-purple {
    background: linear-gradient(90deg , var(--purple) , #c084fc);
}

.mini-grid {
    display: grid;
    grid-template-columns: repeat(2 , 1fr);
    gap: 16px;
}

.mini-card {
    background: rgba(255 , 255 , 255 , 0.045);
    border: 1px solid rgba(148 , 163 , 184 , 0.13);
    border-radius: 20px;
    padding: 18px;
    min-height: 112px;
}

.mini-card span {
    color: #c084fc;
    font-size: 14px;
    font-weight: 700;
}

.mini-card strong {
    display: block;
    font-size: 34px;
    margin-top: 12px;
}

.chart-card {
    min-height: 430px;
}

.chart-wrap {
    height: 330px;
    position: relative;
}

.image-card {
    min-height: 430px;
}

.elo-image-wrap {
    height: 330px;
    border-radius: 24px;
    overflow: hidden;
    background:
        radial-gradient(circle at top , rgba(34 , 211 , 238 , 0.12) , transparent 40%),
        rgba(255 , 255 , 255 , 0.04);
    border: 1px solid rgba(148 , 163 , 184 , 0.14);
    display: flex;
    align-items: center;
    justify-content: center;
}

.elo-image-wrap img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.mode-bars {
    display: grid;
    gap: 15px;
}

.mode-line {
    display: grid;
    grid-template-columns: 120px 1fr 42px;
    align-items: center;
    gap: 12px;
}

.mode-name {
    color: #cbd5e1;
    font-weight: 700;
}

.mode-count {
    text-align: right;
    color: #e5e7eb;
    font-weight: 800;
}

.mode-bar-bg {
    height: 14px;
    background: rgba(51 , 65 , 85 , 0.75);
    border-radius: 999px;
    overflow: hidden;
}

.mode-bar-fill {
    height: 100%;
    border-radius: 999px;
    min-width: 6px;
}

.footer {
    margin-top: 28px;
    text-align: center;
    color: var(--muted);
    font-size: 14px;
}

@media(max-width: 1100px) {
    .top-grid , .grid {
        grid-template-columns: 1fr;
    }

    .hero-content {
        flex-direction: column;
        align-items: flex-start;
    }
}
</style>
</head>

<body>
<div class="container">

<section class="hero">
    <div class="hero-content">
        <div>
            <h1>{{name}}</h1>
            <p>Professional analytics dashboard for your local Quiz Project profile</p>
        </div>

        <div class="hero-badge">
            <span>Current Ranked Rating</span>
            <strong>{{elo}}</strong>
        </div>
    </div>
</section>

<section class="top-grid">

<div class="card">
    <h2>Ranked Performance</h2>
    <div class="big-number">{{elo}} ELO</div>

    <div class="stat-row">
        <div class="stat-label">
            <span>Ranked Win Rate</span>
            <strong>{{rankedWinRate}}%</strong>
        </div>
        <div class="bar">
            <div class="fill fill-cyan" style="width: {{rankedWinRateRaw}}%;"></div>
        </div>
    </div>

    <div class="stat-row">
        <div class="stat-label">
            <span>Overall Accuracy</span>
            <strong>{{overallAccuracy}}%</strong>
        </div>
        <div class="bar">
            <div class="fill fill-green" style="width: {{overallAccuracyRaw}}%;"></div>
        </div>
    </div>

    <div class="stat-row">
        <div class="stat-label">
            <span>Average Ranked Score</span>
            <strong>{{rankedAvg}} / 5</strong>
        </div>
        <div class="bar">
            <div class="fill fill-purple" style="width: {{rankedAvgRaw}}%;"></div>
        </div>
    </div>
</div>

<div class="card">
    <h2>Ranked Record</h2>

    <div class="mini-grid">
        <div class="mini-card">
            <span>Wins</span>
            <strong>{{rankedWins}}</strong>
        </div>

        <div class="mini-card">
            <span>Losses</span>
            <strong>{{rankedLosses}}</strong>
        </div>

        <div class="mini-card">
            <span>Best Score</span>
            <strong>{{rankedBestScore}}</strong>
        </div>

        <div class="mini-card">
            <span>Ranked Games</span>
            <strong>{{rankedGames}}</strong>
        </div>
    </div>
</div>

<div class="card">
    <h2>All Modes Summary</h2>

    <div class="mini-grid">
        <div class="mini-card">
            <span>Total Games</span>
            <strong>{{totalGames}}</strong>
        </div>

        <div class="mini-card">
            <span>Questions</span>
            <strong>{{totalQuestions}}</strong>
        </div>

        <div class="mini-card">
            <span>Correct</span>
            <strong>{{totalCorrect}}</strong>
        </div>

        <div class="mini-card">
            <span>Favorite Mode</span>
            <strong style="font-size:26px;">{{favoriteMode}}</strong>
        </div>
    </div>
</div>

</section>

<section class="grid">

<div class="card chart-card">
    <h2>Mode Distribution</h2>
    <div class="chart-wrap">
        <canvas id="modeChart"></canvas>
    </div>
</div>

<div class="card image-card">
    <h2>ELO Mood</h2>
    <div class="elo-image-wrap">
        <img src="{{eloImage}}" alt="ELO Mood">
    </div>
    <p class="subtle">{{eloMessage}}</p>
</div>

</section>

<section class="card" style="margin-top:22px;">
    <h2>Mode Activity Breakdown</h2>

    <div class="mode-bars">

        <div class="mode-line">
            <div class="mode-name">Timed</div>
            <div class="mode-bar-bg">
                <div class="mode-bar-fill" style="width: {{timedPercent}}%; background: linear-gradient(90deg , #06b6d4 , #67e8f9);"></div>
            </div>
            <div class="mode-count">{{timedGames}}</div>
        </div>

        <div class="mode-line">
            <div class="mode-name">Survival</div>
            <div class="mode-bar-bg">
                <div class="mode-bar-fill" style="width: {{survivalPercent}}%; background: linear-gradient(90deg , #10b981 , #86efac);"></div>
            </div>
            <div class="mode-count">{{survivalGames}}</div>
        </div>

        <div class="mode-line">
            <div class="mode-name">Sudden Death</div>
            <div class="mode-bar-bg">
                <div class="mode-bar-fill" style="width: {{suddenPercent}}%; background: linear-gradient(90deg , #7c3aed , #c084fc);"></div>
            </div>
            <div class="mode-count">{{suddenDeathGames}}</div>
        </div>

        <div class="mode-line">
            <div class="mode-name">PvP</div>
            <div class="mode-bar-bg">
                <div class="mode-bar-fill" style="width: {{pvpPercent}}%; background: linear-gradient(90deg , #f97316 , #fdba74);"></div>
            </div>
            <div class="mode-count">{{pvpGames}}</div>
        </div>

        <div class="mode-line">
            <div class="mode-name">LLM</div>
            <div class="mode-bar-bg">
                <div class="mode-bar-fill" style="width: {{llmPercent}}%; background: linear-gradient(90deg , #e879f9 , #f0abfc);"></div>
            </div>
            <div class="mode-count">{{llmGames}}</div>
        </div>

        <div class="mode-line">
            <div class="mode-name">ELO</div>
            <div class="mode-bar-bg">
                <div class="mode-bar-fill" style="width: {{eloPercent}}%; background: linear-gradient(90deg , #38bdf8 , #818cf8);"></div>
            </div>
            <div class="mode-count">{{eloGames}}</div>
        </div>

    </div>
</section>

<div class="footer">
    Generated locally from generated_data/quiz.db
</div>

</div>

<script>
const chartData = [
    Number("{{timedGames}}"),
    Number("{{survivalGames}}"),
    Number("{{suddenDeathGames}}"),
    Number("{{pvpGames}}"),
    Number("{{llmGames}}"),
    Number("{{eloGames}}")
];

const safeChartData = chartData.every(value => value === 0)
    ? [1 , 1 , 1 , 1 , 1 , 1]
    : chartData;

const labels = ["Timed" , "Survival" , "Sudden Death" , "PvP" , "LLM" , "ELO"];
const colors = ["#06b6d4" , "#10b981" , "#7c3aed" , "#f97316" , "#e879f9" , "#38bdf8"];

new Chart(document.getElementById("modeChart") , {
    type: "doughnut" ,
    data: {
        labels: labels ,
        datasets: [{
            data: safeChartData ,
            backgroundColor: colors ,
            borderColor: "#020617" ,
            borderWidth: 6 ,
            hoverOffset: 10
        }]
    } ,
    options: {
        maintainAspectRatio: false ,
        cutout: "64%" ,
        plugins: {
            legend: {
                position: "bottom" ,
                labels: {
                    color: "#e5e7eb" ,
                    padding: 18 ,
                    font: {
                        size: 13 ,
                        weight: "bold"
                    }
                }
            }
        }
    }
});
</script>

</body>
</html>
""";

            html = html
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

                    .replace("{{timedGames}}" , String.valueOf(timedGames))
                    .replace("{{survivalGames}}" , String.valueOf(survivalGames))
                    .replace("{{suddenDeathGames}}" , String.valueOf(suddenDeathGames))
                    .replace("{{pvpGames}}" , String.valueOf(pvpGames))
                    .replace("{{llmGames}}" , String.valueOf(llmGames))
                    .replace("{{eloGames}}" , String.valueOf(eloGames))

                    .replace("{{timedPercent}}" , String.valueOf(percent(timedGames , chartTotal)))
                    .replace("{{survivalPercent}}" , String.valueOf(percent(survivalGames , chartTotal)))
                    .replace("{{suddenPercent}}" , String.valueOf(percent(suddenDeathGames , chartTotal)))
                    .replace("{{pvpPercent}}" , String.valueOf(percent(pvpGames , chartTotal)))
                    .replace("{{llmPercent}}" , String.valueOf(percent(llmGames , chartTotal)))
                    .replace("{{eloPercent}}" , String.valueOf(percent(eloGames , chartTotal)))

                    .replace("{{eloImage}}" , eloImage)
                    .replace("{{eloMessage}}" , escapeHtml(eloMessage));

            PrintWriter out = new PrintWriter(dashboard);
            out.println(html);
            out.close();

            Desktop.getDesktop().browse(dashboard.toURI());

        } catch (Exception e) {
            System.out.println("Could not open dashboard: " + e.getMessage());
            e.printStackTrace();
        }
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