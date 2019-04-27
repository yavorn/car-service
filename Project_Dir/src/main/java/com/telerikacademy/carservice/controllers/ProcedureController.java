package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.service.contracts.ProcedureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/procedures")
public class ProcedureController {
    private ProcedureService procedureService;

    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @GetMapping
    public String listProcedures(Model model){
        model.addAttribute("procedures", procedureService.getAllProcedures());
        return "list-procedures";
    }

    @PutMapping("/delete/{procedureID}")
    public String deleteProcedureById(@PathVariable Long procedureID){
        procedureService.deleteProcedure(procedureID);
        return "list-procedures";
    }

    @GetMapping("/add-procedure")
    public String addProcedureForm(Model model) {

        model.addAttribute("procedure", new Procedure());
        return "add-procedure";
    }

    @PostMapping("/add-procedure")
    public String addProcedure(@Valid @ModelAttribute Procedure procedure , BindingResult bindingErrors) {

        if(bindingErrors.hasErrors()) {
            return "add-procedure";
        }
        procedureService.addProcedure(procedure);
        return "redirect:/procedures";
    }

}
