package com.project.WeddingHallSeatingApp.iterator;

import com.project.WeddingHallSeatingApp.model.Family;
import com.project.WeddingHallSeatingApp.model.Seatable;
import com.project.WeddingHallSeatingApp.model.Table;

import java.util.List;
import java.util.NoSuchElementException;

public class CompositeIterator implements SeatingIterator {

    private final List<Table> tables;
    private final SeatingType type;
    private int tableIndex = 0;
    private int familyIndex = 0;
    private int guestIndex = 0;

    public CompositeIterator(List<Table> tables, SeatingType type) {
        this.tables = tables;
        this.type = type;
    }

    @Override
    public boolean hasNext() {
        return switch (type) {
            case TABLE -> tableIndex < tables.size();
            case FAMILY -> findNextFamily();
            case GUEST -> findNextGuest();
        };
    }

    @Override
    public Seatable next() {
        if (!hasNext()) throw new NoSuchElementException("No more elements!");

        return switch (type) {
            case TABLE -> tables.get(tableIndex++);
            case FAMILY -> tables.get(tableIndex).getMembers().get(familyIndex++);
            case GUEST -> {
                Family family = (Family) tables.get(tableIndex).getMembers().get(familyIndex);
                yield family.getMembers().get(guestIndex++);
            }
        };
    }

    private boolean findNextFamily() {
        while (tableIndex < tables.size()) {
            Table table = tables.get(tableIndex);
            if (familyIndex < table.getMembers().size()) return true;
            tableIndex++;
            familyIndex = 0;
        }
        return false;
    }

    private boolean findNextGuest() {
        while (tableIndex < tables.size()) {
            Table table = tables.get(tableIndex);
            List<Seatable> families = table.getMembers();
            if (familyIndex >= families.size()) {
                tableIndex++;
                familyIndex = 0;
                guestIndex = 0;
                continue;
            }

            Family family = (Family) families.get(familyIndex);
            if (guestIndex < family.getMembers().size()) return true;

            familyIndex++;
            guestIndex = 0;
        }
        return false;
    }
}