package com.quizapp.profiles;

public class ProfileSession {
    private static Profile currentProfile;

    public static void setCurrentProfile(Profile profile) {
        currentProfile = profile;
    }

    public static Profile getCurrentProfile() {
        return currentProfile;
    }
}