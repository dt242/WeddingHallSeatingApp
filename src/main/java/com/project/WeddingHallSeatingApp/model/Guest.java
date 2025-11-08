package com.project.WeddingHallSeatingApp.model;

public class Guest implements Seatable {
    private String name;

    public Guest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getGuestCount() {
        return 1;
    }

    @Override
    public void printInfo() {
        System.out.println("Guest: " + name);
    }
}

