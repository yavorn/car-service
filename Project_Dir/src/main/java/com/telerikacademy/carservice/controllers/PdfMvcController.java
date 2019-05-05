package com.telerikacademy.carservice.controllers;

import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import com.telerikacademy.carservice.models.CarEvent;
import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.service.contracts.CarEventService;
import com.telerikacademy.carservice.service.contracts.PdfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Controller
@RequestMapping
@Api(value = "Pdf creation management", description = "Contains methods for creating a pdf file.")
public class PdfMvcController {
    private static final String NO_EVENT_FOUND_ERROR_MESSAGE = "Car event with id %d not found";
    private static final String NO_PROCEDURES_FOUND_FOR_CUSTOMER_CAR = "There are no car events for car with id %d";
    private CarEventService carEventService;
    private ServletContext context;
    private PdfService pdfService;

    @Autowired
    public PdfMvcController(CarEventService carEventService,
                            ServletContext context,
                            PdfService pdfService) {
        this.carEventService = carEventService;
        this.context = context;
        this.pdfService = pdfService;
    }

    @ApiOperation(value = "Method that sends information about customer and car event which info the PDF file will contain.")
    @RequestMapping(value = "/createPdf/{carEventId}", method = RequestMethod.GET)
    public void createPdf(@PathVariable long carEventId, HttpServletRequest request, HttpServletResponse response) {

        CarEvent carEvent = carEventService.getCarEventByID(carEventId);
        if (carEvent == null) throw new DatabaseItemNotFoundException(String.format(NO_EVENT_FOUND_ERROR_MESSAGE, carEventId));
        Set<Procedure> procedures = carEvent.getProcedures();
        if (procedures.size() == 0) throw new DatabaseItemNotFoundException(String.format(NO_PROCEDURES_FOUND_FOR_CUSTOMER_CAR,
                carEvent.getCustomerCar().getCustomerCarID()));

        boolean isPdfCreated = pdfService.createPdf(carEvent, context, request, response);

        if (isPdfCreated) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "reference" +  ".pdf");
            pdfService.fileDownload(fullPath, response);
            carEventService.editPdfGenerated(carEvent);
        }
    }
}
