package com.example.accountaplication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

    @Id
    private  Long accountNumber;


    @Enumerated(EnumType.STRING)
    private AccountType accountType;

   @NotBlank(message = "Account Holder name should not be blank")
    private String accountHolderName;

   @Min(value = 10000,message = "Account balance should be 10,000")
    private BigDecimal accountBalance;

}
