package com.project.WeddingHallSeatingApp.service;

import com.project.WeddingHallSeatingApp.iterator.CompositeIterator;
import com.project.WeddingHallSeatingApp.iterator.SeatingIterator;
import com.project.WeddingHallSeatingApp.iterator.SeatingType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IteratorService {
    private final TableService tableService;

    public IteratorService(TableService tableService) {
        this.tableService = tableService;
    }

    public SeatingIterator createCompositeIterator(SeatingType type) {
        return new CompositeIterator(tableService.getTables(), type);
    }

    public List<String> listOf(SeatingType type) {
        SeatingIterator iterator = createCompositeIterator(type);
        List<String> result = new ArrayList<>();

        while (iterator.hasNext()) {
            result.add(iterator.next().getName());
        }

        return result;
    }
}
