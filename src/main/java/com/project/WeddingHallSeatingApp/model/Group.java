package com.project.WeddingHallSeatingApp.model;

import com.project.WeddingHallSeatingApp.exception.SeatingException;

import java.util.ArrayList;
import java.util.List;

public abstract class Group implements Seatable {
    private String name;
    private List<Seatable> members = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Seatable> getMembers() {
        return members;
    }

    public void add(Seatable seatable) throws SeatingException {
        members.add(seatable);
    }

    public void remove(Seatable seatable) {
        if (members.contains(seatable)) {
            if (seatable instanceof Group g) {
                g.clear();
            }
            members.remove(seatable);
        }
    }

    @Override
    public int getGuestCount() {
        return members.stream().mapToInt(Seatable::getGuestCount).sum();
    }

    @Override
    public void printInfo() {
        System.out.println(getClass().getSimpleName() + " \"" + name + "\" (" + getGuestCount() + " guests)");
        for (Seatable s : members) s.printInfo();
    }

    public void clear() {
        for (Seatable s : new ArrayList<>(members)) {
            if (s instanceof Group g) {
                g.clear();
            }
            members.remove(s);
        }
    }
}
