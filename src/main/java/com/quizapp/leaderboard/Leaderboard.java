package com.quizapp.leaderboard;

import java.util.Comparator;
import java.util.List;
import com.quizapp.ui.Terminal;
import com.quizapp.profiles.Profile;
import com.quizapp.profiles.ProfileManager;
import com.quizapp.ui.Screen;
import com.quizapp.ui.Theme;

public class Leaderboard {
    public static void showLeaderboard() {
        Screen.clear();

        List<Profile> profiles = ProfileManager.getAllProfiles();

        profiles.sort(Comparator.comparingInt(Profile::getElo).reversed());

        System.out.println(Theme.BORDER_COLOR + "╔════════════════════════════════════════════════════╗" + Theme.RESET);
        System.out.println(Theme.BORDER_COLOR + "║" + Theme.TITLE_TEXT + "                    LEADERBOARD                    " + Theme.BORDER_COLOR + "║" + Theme.RESET);
        System.out.println(Theme.BORDER_COLOR + "╠════════════════════════════════════════════════════╣" + Theme.RESET);

        if (profiles.isEmpty()) {
            System.out.println(Theme.BORDER_COLOR + "║" + Theme.FEEDBACK_WRONG + "              No profiles found yet                 " + Theme.BORDER_COLOR + "║" + Theme.RESET);
        }

        for (int i = 0; i < profiles.size(); i++) {
            Profile p = profiles.get(i);

            String line = String.format("  %d. %s | ELO: %d | W: %d | L: %d",
                    i + 1 ,
                    p.getName() ,
                    p.getElo() ,
                    p.getRankedWins() ,
                    p.getRankedLosses());

            System.out.println(Theme.BORDER_COLOR + "║" + Theme.OPTION_TEXT + Terminal.padRight(line , 52) + Theme.BORDER_COLOR + "║" + Theme.RESET);
        }

        System.out.println(Theme.BORDER_COLOR + "╚════════════════════════════════════════════════════╝" + Theme.RESET);
    }
}