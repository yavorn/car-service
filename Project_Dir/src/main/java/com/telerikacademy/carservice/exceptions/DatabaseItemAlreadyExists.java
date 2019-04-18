package com.telerikacademy.carservice.exceptions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class DatabaseItemAlreadyExists extends RuntimeException {

    public DatabaseItemAlreadyExists(String item ) {
        super (String.format("%s already exists.",item));
    }

}
