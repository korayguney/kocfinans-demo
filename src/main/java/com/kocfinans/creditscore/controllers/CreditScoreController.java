package com.kocfinans.creditscore.controllers;

import com.kocfinans.creditscore.models.Customer;
import com.kocfinans.creditscore.services.CreditScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CreditScoreController {


    private CreditScoreService creditScoreService;

    public CreditScoreController(CreditScoreService creditScoreService) {
        this.creditScoreService = creditScoreService;
    }

    /*
        calculates credit score of the customer
     */
    @PostMapping(value = "/creditscore", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> calculateCreditResult(@RequestBody Customer customer) {
        Optional<Integer> scoreResult = this.creditScoreService.checkCreditScore(customer);
        String result = null;
        if (scoreResult.isPresent()) {
            if(scoreResult.get() < 500){
                result = "RED! Kredi puanı yetersiz. \nMevcut kredi puanı : " + scoreResult.get();
            } else if(scoreResult.get() >= 500 && scoreResult.get() < 1000){
                result = "Kredi Onaylanmıştır! \nMevcut kredi puanı : " + scoreResult.get();
                if(customer.getMonthly_salary() < 5000){
                    result = result + "\nKullanılabilir Kredi Miktarı : 10000.0 TL.";
                } else if(customer.getMonthly_salary() >= 5000){
                    result = result + "\nKullanılabilir Kredi Miktarı : 15000.0 TL.";
                }
            } else if(scoreResult.get() >= 1000){
                result = "Kredi Onaylanmıştır! \nMevcut kredi puanı : " + scoreResult.get();
                result = result + "\nKullanılabilir Kredi Miktarı : "+(customer.getMonthly_salary() * 4) +" TL.";
            }

            return ResponseEntity.ok(result);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

}
