package com.telerikacademy.carservice.service.contracts;

import com.telerikacademy.carservice.models.CarEvent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PdfService {
    boolean createPdf(CarEvent carEvent,

                      ServletContext context,
                      HttpServletRequest request,
                      HttpServletResponse response);

    void fileDownload(String fullPath, HttpServletResponse response);

}
