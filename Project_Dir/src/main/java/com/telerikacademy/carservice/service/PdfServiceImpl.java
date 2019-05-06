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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public boolean createPdf(CarEvent carEvent,  ServletContext context, HttpServletRequest request, HttpServletResponse response) {

        int pageMarginLeft = 15;
        int pageMarginRight = 15;
        int pageMarginTop = 45;
        int pageMarginBottom= 30;
        String visitIdParagraphText = "Visit No: " + carEvent.getCarEventID();
        String customerParagraphText = String.format("Customer: %s, %s, %s", carEvent.getCustomerCar().getCustomer().getName(), carEvent.getCustomerCar().getCustomer().getEmail(), carEvent.getCustomerCar().getCustomer().getPhone());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String dateParagraphText = "Date: " + carEvent.getDate().format(dateTimeFormatter);
        String carParagraphText = String.format("Vehicle: %s %s, %d", carEvent.getCustomerCar().getModel().getMake().getMakeName(), carEvent.getCustomerCar().getModel().getModelName(), carEvent.getCustomerCar().getYearOfProduction());
        String licensePlateParagraphText = String.format("Car plate number: %s", carEvent.getCustomerCar().getLicensePlate());
        String totalPriceParagraphText = "Total price: " + carEvent.getTotalPrice() + " lv.";

        try {
            Document document = new Document(PageSize.A4, pageMarginLeft, pageMarginRight, pageMarginTop, pageMarginBottom);

            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean existingPath = new File(filePath).exists();

            if (!existingPath) {
                new File(filePath).mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file + "/"+ "reference" + ".pdf"));
            document.open();

            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(BaseColor.ORANGE);

            Font mainFont = FontFactory.getFont("Arial", 15, BaseColor.BLACK);
            Font titleFont = FontFactory.getFont("Arial", 25, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("Car Visit Details", titleFont);
            setParagraphTextAlignment(paragraph, Element.ALIGN_CENTER);

            document.add(paragraph);
            document.add(lineSeparator);

            Paragraph visitIdParagraph = new Paragraph(visitIdParagraphText, mainFont);
            setParagraphTextAlignment(visitIdParagraph, Element.ALIGN_LEFT);

            Paragraph serviceEventDateParagraph = new Paragraph(dateParagraphText, mainFont);
            setParagraphTextAlignment(serviceEventDateParagraph, Element.ALIGN_LEFT);

            document.add(visitIdParagraph);
            document.add(serviceEventDateParagraph);
            document.add(new Chunk(lineSeparator));

            Paragraph customerParagraph = new Paragraph(customerParagraphText);
            setParagraphTextAlignment(customerParagraph, Element.ALIGN_LEFT);

            Paragraph carParagraph = new Paragraph(carParagraphText);
            setParagraphTextAlignment(carParagraph, Element.ALIGN_LEFT);

            Paragraph licensePlateParagraph = new Paragraph(licensePlateParagraphText);
            setParagraphTextAlignment(licensePlateParagraph, Element.ALIGN_LEFT);

            document.add(customerParagraph);
            document.add(carParagraph);
            document.add(licensePlateParagraph);

            Font tableBody = FontFactory.getFont("Arial", 15, BaseColor.WHITE);

            PdfPTable eventTable = new PdfPTable(2);
            eventTable.setWidthPercentage(100);
            eventTable.setSpacingBefore(10);
            eventTable.setSpacingAfter(10);

            Set<Procedure> procedures = carEvent.getProcedures();

            for (Procedure p : procedures) {
                PdfPCell procedure = new PdfPCell( new Paragraph( p.getProcedureName(), tableBody));

                setPdfPCellTextAlignment(procedure, Element.ALIGN_LEFT);
                eventTable.addCell(procedure);

                PdfPCell price = new PdfPCell(new Paragraph(p.getProcedurePrice() + " lv.", tableBody));
                setPdfPCellTextAlignment(price, Element.ALIGN_RIGHT);
                eventTable.addCell(price);
            }

            Paragraph totalPriceParagraph = new Paragraph(totalPriceParagraphText, mainFont);
            setParagraphTextAlignment(totalPriceParagraph, Element.ALIGN_RIGHT);

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
                response.setHeader("content-disposition", "attachment; filename=" + "MyCar'sProcedureList.pdf");
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

    private void setPdfPCellTextAlignment(PdfPCell procedure, int textAlign) {
        procedure.setBorderColor(BaseColor.BLACK);
        procedure.setPaddingLeft(10);
        procedure.setHorizontalAlignment(textAlign);
        procedure.setVerticalAlignment(textAlign);
        procedure.setBackgroundColor(BaseColor.GRAY);
        procedure.setExtraParagraphSpace(5);
    }

    private void setParagraphTextAlignment(Paragraph paragraph, int textAlign) {
        paragraph.setAlignment(textAlign);
        paragraph.setIndentationLeft(50);
        paragraph.setIndentationRight(50);
        paragraph.setSpacingAfter(10);
    }
}
