package com.kocfinans.creditscore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("CreditScore")
public class CreditScore {
    @Id
    private String tc_id_number;
    private Integer credit_score;

}
