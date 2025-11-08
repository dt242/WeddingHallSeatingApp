package com.project.WeddingHallSeatingApp.model;

import com.project.WeddingHallSeatingApp.util.Constants;
import com.project.WeddingHallSeatingApp.exception.SeatingException;

public class Family extends Group {
    public Family(String name) {
        super(name);
    }

    @Override
    public void add(Seatable seatable) throws SeatingException {
        if (seatable instanceof Group) {
            throw new SeatingException("❌ A family cannot contain another family or table! Why did you even think that would work?");
        }
        if (getGuestCount() >= Constants.MAX_GUESTS_PER_FAMILY) {
            throw new SeatingException("❌ A family cannot have more than 10 members! Disown a member first to add another!");
        }
        super.add(seatable);
    }
}

