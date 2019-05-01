package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.service.contracts.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String listProcedures(Model model){
        model.addAttribute("newProcedure", new Procedure());
        model.addAttribute("allProcedures", procedureService.getAllProcedures());
        return "procedures";
    }

    @PostMapping
    public String addProcedure(@Valid @ModelAttribute Procedure procedure , BindingResult bindingErrors) {

        if(bindingErrors.hasErrors()) {
            return "procedures";
        }
        procedureService.addProcedure(procedure);
        return "redirect:/procedures";
    }

    @GetMapping("/{procedureID}")
    @ResponseBody
    public Procedure getProcedureById(@PathVariable Long procedureID){
        return procedureService.getProcedureByID(procedureID);
    }

    @DeleteMapping("/{procedureID}")
    @ResponseBody
    public String deleteProcedureById(@PathVariable Long procedureID){
        procedureService.deleteProcedure(procedureID);
        return "redirect:/procedures";
    }

    @PutMapping("/{procedureID}")
    public String editProcedureById(@Valid @ModelAttribute Procedure procedure, @PathVariable Long procedureID) {
        procedureService.editProcedure(procedure, procedureID);
        return "redirect:/procedures";
    }


}
