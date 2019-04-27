package com.telerikacademy.carservice.exceptions;

public class DatabaseItemAlreadyDeletedException extends RuntimeException {
    public DatabaseItemAlreadyDeletedException(String item) {
        super(String.format("%s is already deleted", item));
    }

    public DatabaseItemAlreadyDeletedException(String item, Long id) {
        super(String.format("%s with ID: %d already deleted.", item, id));
    }


}
