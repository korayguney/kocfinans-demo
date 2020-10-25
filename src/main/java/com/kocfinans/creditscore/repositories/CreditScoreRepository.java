package com.kocfinans.creditscore.repositories;

import com.kocfinans.creditscore.models.CreditScore;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CreditScoreRepository extends CrudRepository<CreditScore, String> {
    @Override
    List<CreditScore> findAll();
}

