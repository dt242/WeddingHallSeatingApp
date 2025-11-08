package com.project.WeddingHallSeatingApp.service;

import com.project.WeddingHallSeatingApp.exception.SeatingException;
import com.project.WeddingHallSeatingApp.util.WeddingRules;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SeatingManagerService {
    public String validateName(String name) throws SeatingException {
        name = name.trim();
        if (name.isEmpty()) {
            throw new SeatingException("❌ Name cannot be empty! Looking for bugs?");
        }
        return name;
    }

    public void createBan(String f1, String f2) throws SeatingException {
        WeddingRules.getInstance().banTogether(validateName(f1), validateName(f2));
    }

    public void removeBan(String f1, String f2) throws SeatingException {
        WeddingRules.getInstance().unbanTogether(validateName(f1), validateName(f2));
    }

    public Set<Set<String>> getBannedPairs() {
        return WeddingRules.getInstance().getBannedPairs();
    }


}
