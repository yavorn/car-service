package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.service.ProcedureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
