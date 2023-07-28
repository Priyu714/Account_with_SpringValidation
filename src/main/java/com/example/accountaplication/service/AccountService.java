package com.example.accountaplication.service;

import com.example.accountaplication.entity.Account;
import com.example.accountaplication.exception.AccountNumberNotFoundException;
import com.example.accountaplication.exception.InsufficientBalanceException;
import com.example.accountaplication.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepo accountRepo;
    public List<Account> getAllAccountDetails() {
        return accountRepo.findAll();
    }

    public Account getAccountDetailsById(Long id){
        if(!accountRepo.existsById(id))
            throw new AccountNumberNotFoundException("Account Number is not Valid.");
        return accountRepo.findById(id).get();
    }
    public Account createAccount(Account account){
        return accountRepo.save(account);
    }

    public Account updateAccountdetail(Long id, Account account){

        Optional<Account> accNumber= accountRepo.findById(id);
        if(!accNumber.isPresent())
            throw new AccountNumberNotFoundException("Account Number id not found. Please enter correct account Number");

        Account updatedDetails=accNumber.get();
        updatedDetails.setAccountHolderName(account.getAccountHolderName());
        updatedDetails.setAccountType(account.getAccountType());
        updatedDetails.setAccountBalance(account.getAccountBalance());

        return accountRepo.save(account);
    }

    public String deleteAccountById(Long id){
        if(!accountRepo.existsById(id))
            throw new AccountNumberNotFoundException("Account Number is not Valid.");

        accountRepo.deleteById(id);
        return "Account deleted successFully.";
    }

    public String withdrawMoney(Long id, BigDecimal amount){
        Optional<Account> account =accountRepo.findById(id);
        if(!account.isPresent())
            throw new AccountNumberNotFoundException("Account Number is not Valid");
        Account value=account.get();

        if(value.getAccountBalance().compareTo(amount) > 0){
            value.setAccountBalance(value.getAccountBalance().subtract(amount)) ;
            accountRepo.save(value);
            return "Amount withdraw successfully";
        }
        else
            throw new InsufficientBalanceException("Your AccountBalance is Insufficient to do transaction");
    }

    public String transferMoney(Long senderId, Long receiverId, BigDecimal amount){
        Optional<Account> senderAccount = accountRepo.findById(senderId);
        Optional<Account> receiveAccount = accountRepo.findById(receiverId);
        if(!(senderAccount.isPresent() && receiveAccount.isPresent())){
            throw new AccountNumberNotFoundException("One of the account number is not valid");
        }

        if(senderAccount.get().getAccountBalance().compareTo(amount) >= 0 ){
            senderAccount.get().setAccountBalance(senderAccount.get().getAccountBalance().subtract(amount));
            receiveAccount.get().setAccountBalance(receiveAccount.get().getAccountBalance().add(amount));
            accountRepo.save(senderAccount.get());
            accountRepo.save(receiveAccount.get());
            return "Money Transferred Successfully";
        }
        else {
            throw new InsufficientBalanceException("Your AccountBalance is Insufficient to do transaction");
        }
    }
}