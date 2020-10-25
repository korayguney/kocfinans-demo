package com.kocfinans.creditscore.services;

import com.kocfinans.creditscore.models.CreditScore;
import com.kocfinans.creditscore.models.Customer;
import com.kocfinans.creditscore.repositories.CreditScoreRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditScoreService {

    private CreditScoreRepository creditScoreRepository;

    public CreditScoreService(CreditScoreRepository creditScoreRepository) {
        this.creditScoreRepository = creditScoreRepository;
    }

    /*
        get credit score from Redis
     */
    public Optional<Integer> checkCreditScore(Customer customer) {
        Optional<CreditScore> creditScore = this.creditScoreRepository.findById(String.valueOf(customer.getTc_id_number()));
        return creditScore.isPresent() ? Optional.ofNullable(creditScore.get().getCredit_score()) : Optional.empty();
    }
}
