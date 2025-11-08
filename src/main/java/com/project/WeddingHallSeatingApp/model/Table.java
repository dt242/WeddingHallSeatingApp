package com.project.WeddingHallSeatingApp.model;

import com.project.WeddingHallSeatingApp.util.Constants;
import com.project.WeddingHallSeatingApp.exception.SeatingException;
import com.project.WeddingHallSeatingApp.util.WeddingRules;

public class Table extends Group {
    public Table(String name) {
        super(name);
    }

    @Override
    public void add(Seatable seatable) throws SeatingException {
        int total = getGuestCount() + seatable.getGuestCount();
        if (total >= Constants.MAX_GUESTS_PER_TABLE && seatable instanceof Family) {
            throw new SeatingException("❌ Cannot add more that 10 guests to a table and there is already a big family on this one! Capacity reached!");
        }

        long familyCount = getMembers().stream()
                .filter(s -> s instanceof Family)
                .count();
        if (seatable instanceof Family && familyCount >= Constants.MAX_FAMILIES_PER_TABLE) {
            throw new SeatingException("❌ Table '" + getName() + "' already has 2 families! The whole wedding cannot be seated on a single table! duh");
        }

        if (seatable instanceof Family f) {
            for (Seatable s : getMembers()) {
                if (s instanceof Family existing) {
                    if (WeddingRules.getInstance().areBanned(existing.getName(), f.getName())) {
                        throw new SeatingException("❌ '" + f.getName() + "' cannot sit with '" + existing.getName() + "'! The drama would be too wild!");
                    }
                }
            }
        }
        if (seatable instanceof Family) {
            super.add(seatable);
        } else {
            throw new SeatingException("❌ A guest must be a part of a family to be added! No orphans allowed i guess!");
        }
    }
}
