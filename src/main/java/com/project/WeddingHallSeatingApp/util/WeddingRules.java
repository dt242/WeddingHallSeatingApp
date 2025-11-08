package com.project.WeddingHallSeatingApp.util;

import com.project.WeddingHallSeatingApp.exception.SeatingException;

import java.util.HashSet;
import java.util.Set;

public final class WeddingRules {

    private static volatile WeddingRules instance;
    private final Set<Set<String>> bannedPairs = new HashSet<>();

    private WeddingRules() {}

    public static WeddingRules getInstance() {
        if (instance == null) {
            synchronized (WeddingRules.class) {
                if (instance == null) {
                    instance = new WeddingRules();
                }
            }
        }
        return instance;
    }

    public void banTogether(String family1, String family2) throws SeatingException {
        if (!bannedPairs.add(Set.of(family1, family2))) {
            throw new SeatingException("❌ These families are already banned from sitting together! Can they not stand each other that much?");
        }
    }

    public void unbanTogether(String family1, String family2) throws SeatingException {
        if (!bannedPairs.remove(Set.of(family1, family2))) {
            throw new SeatingException("❌ Cannot find ban! Somehow...");
        }
    }

    public boolean areBanned(String family1, String family2) {
        return bannedPairs.contains(Set.of(family1, family2));
    }

    public Set<Set<String>> getBannedPairs() {
        return new HashSet<>(bannedPairs);
    }

}



