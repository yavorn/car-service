package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.models.CarEvent;
import com.telerikacademy.carservice.models.ProcedureVisit;
import com.telerikacademy.carservice.service.contracts.CarEventService;
import com.telerikacademy.carservice.service.contracts.PdfService;
import com.telerikacademy.carservice.service.contracts.ProcedureVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/")
public class PdfMvcController {
    private CarEventService eventService;
    private ProcedureVisitService procedureVisitService;
    private ServletContext context;
    private PdfService pdfService;

    @Autowired
    public PdfMvcController(CarEventService eventService, ProcedureVisitService procedureVisitService,
                            ServletContext context, PdfService pdfService) {
        this.eventService = eventService;
        this.procedureVisitService = procedureVisitService;
        this.context = context;
        this.pdfService = pdfService;
    }

    @RequestMapping(value = {"/car-visit/pdf/{id}", "/admin/car-visit/pdf/{id}"}, method = RequestMethod.GET)
    public void createPdf(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) {
        CarEvent carEvent = eventService.getCarEventByID(id);
        List<ProcedureVisit> procedureVisits = procedureVisitService.getAllProcedureVisitsByCarEventCustomerCarID(id);
        boolean isPdfGen = pdfService.createPdf(carEvent, procedureVisits, context, request, response);

        if (isPdfGen) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/"
                    + carEvent.getDate() + ", " + carEvent.getCustomerCar().getLicensePlate() +  ".pdf");
            pdfService.fileDownload(fullPath, response);
            procedureVisitService.editPdfGenerated(procedureVisitService.findProcedureVisitByCarEventId(carEvent.getCarEventID()));
        }
    }
}
