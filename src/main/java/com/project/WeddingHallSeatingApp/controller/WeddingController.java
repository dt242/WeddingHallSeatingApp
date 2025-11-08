package com.project.WeddingHallSeatingApp.controller;

import com.project.WeddingHallSeatingApp.exception.SeatingException;
import com.project.WeddingHallSeatingApp.iterator.SeatingType;
import com.project.WeddingHallSeatingApp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WeddingController {
    private final TableService tableService;
    private final FamilyService familyService;
    private final GuestService guestService;
    private final SeatingManagerService seatingManagerService;
    private final IteratorService iteratorService;

    public WeddingController(TableService tableService, FamilyService familyService, GuestService guestService, SeatingManagerService seatingManagerService, IteratorService iteratorService) {
        this.tableService = tableService;
        this.familyService = familyService;
        this.guestService = guestService;
        this.seatingManagerService = seatingManagerService;
        this.iteratorService = iteratorService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tables", tableService.getTables());
        model.addAttribute("totalGuests", guestService.findTotalGuests());
        model.addAttribute("bans", seatingManagerService.getBannedPairs());

        return "index";
    }

    @GetMapping("/guests")
    public String showAllGuests(Model model) {
        model.addAttribute("guests", iteratorService.listOf(SeatingType.GUEST));
        model.addAttribute("totalGuests", guestService.findTotalGuests());
        return "guests";
    }

    @GetMapping("/families")
    public String showAllFamilies(Model model) {
        model.addAttribute("families", iteratorService.listOf(SeatingType.FAMILY));
        model.addAttribute("totalFamilies", familyService.findTotalFamilies());
        return "families";
    }

    @GetMapping("/tables")
    public String showAllTables(Model model) {
        model.addAttribute("tables", iteratorService.listOf(SeatingType.TABLE));
        model.addAttribute("totalTables", tableService.getTables().size());
        return "tables";
    }

    @PostMapping("/addTable")
    public String addTable(@RequestParam String name, RedirectAttributes redirectAttributes) throws SeatingException {
        try {
            tableService.addTableWithName(name);
            redirectAttributes.addFlashAttribute("message", "✅ Table '" + name + "' added! Finally, somewhere to sit!");
        } catch (SeatingException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/removeTable")
    public String removeTable(@RequestParam String name, RedirectAttributes redirectAttributes) {
        try {
            tableService.removeTableWithName(name);
            redirectAttributes.addFlashAttribute("message", "✅ Table '" + name + "' removed! Remove the chairs as well?");
        } catch (SeatingException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/addFamily")
    public String addFamily(@RequestParam String tableName, @RequestParam String familyName, RedirectAttributes redirectAttributes) {
        try {
            familyService.addFamilyToTable(familyName, tableName);
            redirectAttributes.addFlashAttribute("message", "✅ Family '" + familyName + "' added to '" + tableName + "'! Now add some members if you don't want to party alone!");
        } catch (SeatingException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/removeFamily")
    public String removeFamily(@RequestParam String tableName, @RequestParam String familyName, RedirectAttributes redirectAttributes) {
        try {
            familyService.removeFamilyFromTable(familyName, tableName);
            redirectAttributes.addFlashAttribute("message", "✅ Family '" + familyName + "' removed from table '" + tableName + "'! Did you have a fight?!");
        } catch (SeatingException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/addGuest")
    public String addGuest(@RequestParam String tableName, @RequestParam String familyName, @RequestParam String guestName, RedirectAttributes redirectAttributes) {
        try {
            guestService.addGuestToFamily(guestName, familyName, tableName);
            redirectAttributes.addFlashAttribute("message", "✅ Guest '" + guestName + "' added to family '" + familyName + "'! Go on, fill the room!");
        } catch (SeatingException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/removeGuest")
    public String removeGuest(@RequestParam String tableName, @RequestParam String familyName, @RequestParam String guestName, RedirectAttributes redirectAttributes) {
        try {
            guestService.removeGuestFromFamily(guestName, familyName, tableName);
            redirectAttributes.addFlashAttribute("message", "✅ Guest '" + guestName + "' removed from family '" + familyName + "'! One present down!");
        } catch (SeatingException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/ban")
    public String banFamilies(@RequestParam String f1, @RequestParam String f2, RedirectAttributes redirectAttributes) {
        try {
            seatingManagerService.createBan(f1, f2);
            redirectAttributes.addFlashAttribute("message", "✅ Added a ban! '" + f1 + "' can't sit with '" + f2  + "'! Family feud!");
        } catch (SeatingException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/removeBan")
    public String removeBan(@RequestParam String f1, @RequestParam String f2, RedirectAttributes redirectAttributes) {
        try {
            seatingManagerService.removeBan(f1, f2);
            redirectAttributes.addFlashAttribute("message", "✅ Removed a ban! '" + f1 + "' now CAN sit with '" + f2  + "'! Family feud is settled!");
        } catch (SeatingException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/";
    }
}

