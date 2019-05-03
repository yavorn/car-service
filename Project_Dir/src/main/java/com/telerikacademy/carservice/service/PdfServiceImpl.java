package com.telerikacademy.carservice.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.telerikacademy.carservice.models.CarEvent;
import com.telerikacademy.carservice.models.Procedure;
import com.telerikacademy.carservice.service.contracts.PdfService;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Set;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public boolean createPdf(CarEvent carEvent,
                             ServletContext context,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            Document document = new Document(PageSize.A4, 15, 15, 45, 30);

            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean existPath = new File(filePath).exists();

            if (!existPath) {
                new File(filePath).mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/" + "invoice" + ".pdf"));
            document.open();

            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(BaseColor.ORANGE);

            Font mainFont = FontFactory.getFont("Arial", 15, BaseColor.BLACK);
            Font titleFont = FontFactory.getFont("Arial", 25, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("Car Visit Details", titleFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);

            document.add(paragraph);
            document.add(lineSeparator);

            Paragraph visitIdParagraph = new Paragraph("Visit No: " + carEvent.getCarEventID(), mainFont);
            visitIdParagraph.setAlignment(Element.ALIGN_LEFT);
            visitIdParagraph.setIndentationLeft(50);
            visitIdParagraph.setIndentationRight(50);
            visitIdParagraph.setSpacingAfter(10);

            Paragraph dateParagraph = new Paragraph("Date: " + carEvent.getDate(), mainFont);
            dateParagraph.setAlignment(Element.ALIGN_LEFT);
            dateParagraph.setIndentationLeft(50);
            dateParagraph.setIndentationRight(50);
            dateParagraph.setSpacingAfter(10);

            document.add(visitIdParagraph);
            document.add(dateParagraph);
            document.add(new Chunk(lineSeparator));

            Paragraph customerParagraph = new Paragraph("Customer: " + carEvent.getCustomerCar().getCustomer().getName() + ", "
                    + carEvent.getCustomerCar().getCustomer().getEmail() + ", " + carEvent.getCustomerCar().getCustomer().getPhone());
            customerParagraph.setAlignment(Element.ALIGN_LEFT);
            customerParagraph.setIndentationLeft(50);
            customerParagraph.setIndentationRight(50);
            customerParagraph.setSpacingAfter(10);

            Paragraph carParagraph = new Paragraph("Vehicle: " + carEvent.getCustomerCar().getModel().getMake().getMakeName() +
                    ", " + carEvent.getCustomerCar().getModel().getModelName() + ", " + carEvent.getCustomerCar().getYearOfProduction());
            carParagraph.setAlignment(Element.ALIGN_LEFT);
            carParagraph.setIndentationLeft(50);
            carParagraph.setIndentationRight(50);
            carParagraph.setSpacingAfter(10);

            Paragraph licensePlateParagraph = new Paragraph("Plate Number: " + carEvent.getCustomerCar().getLicensePlate());
            licensePlateParagraph.setAlignment(Element.ALIGN_LEFT);
            licensePlateParagraph.setIndentationLeft(50);
            licensePlateParagraph.setIndentationRight(50);
            licensePlateParagraph.setSpacingAfter(10);

            document.add(customerParagraph);
            document.add(carParagraph);
            document.add(licensePlateParagraph);

            Font tableBody = FontFactory.getFont("Arial", 15, BaseColor.WHITE);

            PdfPTable eventTable = new PdfPTable(3);
            eventTable.setWidthPercentage(100);
            eventTable.setSpacingBefore(10);
            eventTable.setSpacingAfter(10);

            Set<Procedure> visitProcedures = carEvent.getProcedures();

            for (Procedure p : visitProcedures) {
                PdfPCell procedure = new PdfPCell( new Paragraph( p.getProcedureName(), tableBody));

                procedure.setBorderColor(BaseColor.BLACK);
                procedure.setPaddingLeft(10);
                procedure.setHorizontalAlignment(Element.ALIGN_LEFT);
                procedure.setVerticalAlignment(Element.ALIGN_LEFT);
                procedure.setBackgroundColor(BaseColor.GRAY);
                procedure.setExtraParagraphSpace(5);
                eventTable.addCell(procedure);

                PdfPCell price = new PdfPCell(new Paragraph(String.valueOf(p.getProcedurePrice()), tableBody));
                price.setBorderColor(BaseColor.BLACK);
                price.setPaddingLeft(10);
                price.setHorizontalAlignment(Element.ALIGN_RIGHT);
                price.setVerticalAlignment(Element.ALIGN_RIGHT);
                price.setBackgroundColor(BaseColor.GRAY);
                price.setExtraParagraphSpace(5);
                eventTable.addCell(price);
            }

            Paragraph totalPriceParagraph = new Paragraph("Total price: " + carEvent.getTotalPrice(), mainFont);
            totalPriceParagraph.setAlignment(Element.ALIGN_RIGHT);
            totalPriceParagraph.setIndentationLeft(50);
            totalPriceParagraph.setIndentationRight(50);
            totalPriceParagraph.setSpacingAfter(10);

            document.add(eventTable);
            document.add(totalPriceParagraph);
            document.close();
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public void fileDownload(String fullPath, HttpServletResponse response) {
        File file = new File(fullPath);
        final int BUFFER_SIZE = 4096;
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                response.setContentType("application/pdf");
                response.setHeader("content-disposition", "attachment; filename=" + "invoice.pdf");
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outputStream.close();
                file.delete();
            } catch (Exception e) {
                e.getMessage();
                throw new RuntimeException();
            }
        }
    }
}
