package com.project.WeddingHallSeatingApp.service;

import com.project.WeddingHallSeatingApp.exception.SeatingException;
import com.project.WeddingHallSeatingApp.model.Family;
import com.project.WeddingHallSeatingApp.model.Seatable;
import com.project.WeddingHallSeatingApp.model.Table;
import org.springframework.stereotype.Service;

@Service
public class FamilyService {
    private final TableService tableService;
    private final SeatingManagerService seatingManagerService;

    public FamilyService(TableService tableService, SeatingManagerService seatingManagerService) {
        this.tableService = tableService;
        this.seatingManagerService = seatingManagerService;
    }

    public int findTotalFamilies() {
        return tableService.getTables().stream()
                .mapToInt(table -> (int) table.getMembers().stream()
                        .filter(member -> member instanceof Family)
                        .count())
                .sum();
    }

    public Family findFamilyInTableByName(String familyName, Table table) throws SeatingException {
        for (Seatable s : table.getMembers()) {
            if (s instanceof Family family && family.getName().equals(familyName)) {
                return family;
            }
        }
        throw new SeatingException("❌ Family '" + familyName + "' not found! Either there's a bug or you're hacking!");
    }

    public void addFamilyToTable(String familyName, String tableName) throws SeatingException{
        String validFamilyName = seatingManagerService.validateName(familyName);
        String validTableName = seatingManagerService.validateName(tableName);
        Table table = tableService.findTableByName(validTableName);

        if (table.getMembers().stream()
                .filter(m -> m instanceof Family)
                .map(m -> ((Family) m).getName())
                .anyMatch(f -> f.equals(validFamilyName))) {
            throw new SeatingException("❌ Family '" + validFamilyName + "' already exists at table '" + validTableName + "'! Why are you trying to separate the family?!");
        }
        table.add(new Family(validFamilyName));
    }

    public void removeFamilyFromTable(String familyName, String tableName) throws SeatingException{
        Table table = tableService.findTableByName(seatingManagerService.validateName(tableName));
        table.remove(findFamilyInTableByName(seatingManagerService.validateName(familyName), table));
    }
}
