package com.example.accountaplication.exception;

public class AccountNumberNotFoundException extends RuntimeException{

    public AccountNumberNotFoundException(String message){
        super(message);
    }
}
