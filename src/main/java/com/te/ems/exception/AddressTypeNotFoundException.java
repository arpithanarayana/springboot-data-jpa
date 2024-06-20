package com.te.ems.exception;

public class AddressTypeNotFoundException extends RuntimeException{
    String message;
    public AddressTypeNotFoundException(String message){

        super(message);
    }
}
