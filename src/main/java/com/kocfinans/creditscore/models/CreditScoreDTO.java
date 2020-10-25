package com.kocfinans.creditscore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditScoreDTO {

    private boolean success;
    private Map<String, Integer> scores;

}
