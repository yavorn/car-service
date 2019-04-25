package com.telerikacademy.carservice.controllers.exception_handler;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return violations;
    }
    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

}
