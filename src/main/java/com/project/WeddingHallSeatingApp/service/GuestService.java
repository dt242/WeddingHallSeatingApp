package com.project.WeddingHallSeatingApp.service;

import com.project.WeddingHallSeatingApp.exception.SeatingException;
import com.project.WeddingHallSeatingApp.model.Family;
import com.project.WeddingHallSeatingApp.model.Guest;
import com.project.WeddingHallSeatingApp.model.Seatable;
import com.project.WeddingHallSeatingApp.model.Table;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    private final TableService tableService;
    private final FamilyService familyService;
    private final SeatingManagerService seatingManagerService;

    public GuestService(TableService tableService, FamilyService familyService, SeatingManagerService seatingManagerService) {
        this.tableService = tableService;
        this.familyService = familyService;
        this.seatingManagerService = seatingManagerService;
    }

    public int findTotalGuests() {
        return tableService.getTables().stream().mapToInt(Table::getGuestCount).sum();
    }

    public Guest findGuestInFamilyByName(String guestName, Family family) throws SeatingException {
        for (Seatable s : family.getMembers()) {
            if (s instanceof Guest guest && guest.getName().equals(guestName)) {
                return guest;
            }
        }
        throw new SeatingException("❌ Guest '" + guestName + "' not found! Forgot to invite them and already removing them...");
    }

    public void addGuestToFamily(String guestName, String familyName, String tableName) throws SeatingException {
        String validGuestName = seatingManagerService.validateName(guestName);
        String validFamilyName = seatingManagerService.validateName(familyName);
        String validTableName = seatingManagerService.validateName(tableName);
        Table table = tableService.findTableByName(validTableName);
        Family family = familyService.findFamilyInTableByName(validFamilyName, table);
        if (family.getMembers().stream()
                .filter(m -> m instanceof Guest)
                .map(m -> ((Guest) m).getName())
                .anyMatch(g -> g.equals(validGuestName))) {
            throw new SeatingException("❌ Guest '" + validGuestName + "' already exists in family '" + validFamilyName + "'! No doppelgangers!");
        }
        if (tableService.isTableFull(table)) {
            throw new SeatingException("❌ Cannot add more than 10 guests to a table! Check your math!");
        }
        family.add(new Guest(validGuestName));
    }

    public void removeGuestFromFamily(String guestName, String familyName, String tableName) throws SeatingException {
        Table table = tableService.findTableByName(seatingManagerService.validateName(tableName));
        Family family = familyService.findFamilyInTableByName(seatingManagerService.validateName(familyName), table);
        family.remove(findGuestInFamilyByName(seatingManagerService.validateName(guestName), family));
    }
}
