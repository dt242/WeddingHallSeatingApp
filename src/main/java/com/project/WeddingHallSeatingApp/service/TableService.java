package com.project.WeddingHallSeatingApp.service;

import com.project.WeddingHallSeatingApp.exception.SeatingException;
import com.project.WeddingHallSeatingApp.model.Table;
import com.project.WeddingHallSeatingApp.util.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {
    private final List<Table> tables = new ArrayList<>();
    private final SeatingManagerService seatingManagerService;

    public TableService(SeatingManagerService seatingManagerService) {
        this.seatingManagerService = seatingManagerService;
    }

    public List<Table> getTables() {
        return tables;
    }

    public boolean isTableFull(Table table) {
        return table.getGuestCount() >= Constants.MAX_GUESTS_PER_TABLE;
    }

    public Table findTableByName(String name) throws SeatingException {
        return tables.stream()
                .filter(t -> t.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new SeatingException("❌ Table '" + name + "' not found! I'm just as surprised as you!"));
    }

    public void addTableWithName(String name) throws SeatingException {
        String validTableName = seatingManagerService.validateName(name);
        if (tables.stream().anyMatch(t -> t.getName().equals(validTableName))) {
            throw new SeatingException("❌ Table with name '" + validTableName + "' already exists! You like the name that much?");
        }
        tables.add(new Table(validTableName));
    }

    public void removeTableWithName(String name) throws SeatingException {
        tables.remove(findTableByName(seatingManagerService.validateName(name)));
    }
}
