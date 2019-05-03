package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.service.contracts.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/procedures")
public class ProcedureController {
    private ProcedureService procedureService;

    @Autowired
    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @GetMapping
    public String listProcedures(Model model) {
        model.addAttribute("procedures", procedureService.getAllProcedures());
        model.addAttribute("procedure", new Procedure());
        return "procedures";
    }

    @PostMapping()
    public String addProcedure(@Valid @ModelAttribute Procedure procedure) {
        procedureService.addProcedure(procedure);
        return "redirect:/procedures";
    }

    @GetMapping("/{procedureID}")
    public Procedure getProcedureById(@PathVariable Long procedureID) {
        return procedureService.getProcedureByID(procedureID);
    }

    @DeleteMapping("/{procedureID}")
    public String deleteProcedureById(@PathVariable Long procedureID) {
        procedureService.deleteProcedure(procedureID);
        return "redirect:/procedures";
    }

    @PutMapping("/{procedureID}")
    public String editProcedureById(@Valid @ModelAttribute Procedure procedure, @PathVariable Long procedureID) {
        procedureService.editProcedure(procedure, procedureID);
        return "redirect:/procedures";
    }


}
