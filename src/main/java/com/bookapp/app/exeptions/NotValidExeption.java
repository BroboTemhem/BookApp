package com.bookapp.app.exeptions;

public class NotValidExeption extends RuntimeException{
    public NotValidExeption(String message) {
        super(message);
    }
}
