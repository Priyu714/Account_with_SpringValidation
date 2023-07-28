package com.example.accountaplication.controller;


import com.example.accountaplication.entity.Account;
import com.example.accountaplication.exception.AccountNumberNotFoundException;
import com.example.accountaplication.exception.InsufficientBalanceException;
import com.example.accountaplication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("account")
public class AccontController {

    @Autowired
     AccountService accountService;
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccountDetails(){
        return new ResponseEntity<>(accountService.getAllAccountDetails(), HttpStatus.OK) ;
    } // localhost:9090/account/1

    @GetMapping("/{id}")
    public  ResponseEntity<?> getAccountDetailsById(@PathVariable Long id){
        return new ResponseEntity<>(accountService.getAccountDetailsById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody @Valid Account account){
        return new ResponseEntity<>(accountService.createAccount(account),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccountdetail(@PathVariable Long id, @RequestBody Account account){
        return new ResponseEntity<>(accountService.updateAccountdetail(id,account),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccountById(@PathVariable Long id ){
        return new ResponseEntity<>(accountService.deleteAccountById(id),HttpStatus.OK);
    }

    @PutMapping("{id}/{amount}")
    public ResponseEntity<String> withDrawMoney(@PathVariable Long id, @PathVariable BigDecimal amount){
        return new ResponseEntity<>(accountService.withdrawMoney(id,amount),HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id1}/{id2}/{amount}")
    public ResponseEntity<String> transferMoney(@PathVariable Long id1, @PathVariable Long id2, @PathVariable BigDecimal amount){
        return new ResponseEntity<>(accountService.transferMoney(id1, id2, amount), HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(AccountNumberNotFoundException.class)
    public ResponseEntity<String> handlerAccountNumberNotFound(AccountNumberNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalance(InsufficientBalanceException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }

}
