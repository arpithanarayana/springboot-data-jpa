package com.te.ems.exception;

public class EmployeeNotFoundException extends RuntimeException{
    String message;
    public EmployeeNotFoundException(String message){
        super(message);
    }
}
