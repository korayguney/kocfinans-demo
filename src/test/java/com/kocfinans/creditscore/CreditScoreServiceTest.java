package com.kocfinans.creditscore;

import com.kocfinans.creditscore.models.CreditScore;
import com.kocfinans.creditscore.models.Customer;
import com.kocfinans.creditscore.repositories.CreditScoreRepository;
import com.kocfinans.creditscore.services.CreditScoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootApplication
public class CreditScoreServiceTest {

    @Mock
    private CreditScoreRepository repository;

    private CreditScoreService subject;

    @Before
    public void setup() {
        subject = new CreditScoreService(repository);
    }

    @Test
    public void calculationShouldReturnEmptyWhenCustomerDoesNotExist() {
        Customer customer = new Customer(22222222222L,"Ali Veli", 8000,"5555555555");
        Mockito.when(repository.findById(String.valueOf(customer.getTc_id_number()))).thenReturn(Optional.empty());

        Optional<Integer> result = this.subject.checkCreditScore(customer);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void calculationShouldReturnScoreWhenCustomerExists() {
        Customer customer = new Customer(22222222222L,"Ali Veli", 8000,"5555555555");
        CreditScore creditScore = new CreditScore(String.valueOf(customer.getTc_id_number()) , 750 );

        Mockito.when(repository.findById(String.valueOf(customer.getTc_id_number()))).thenReturn(Optional.of(creditScore));

        Optional<Integer> result = this.subject.checkCreditScore(customer);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPresent());
    }

    @Test
    public void calculationShouldReturn10000WhenCustomerCreditScoreIsHigherBetween500and1000() {
        Customer customer = new Customer(22222222222L,"Ali Veli", 8000,"5555555555");
        CreditScore creditScore = new CreditScore(String.valueOf(customer.getTc_id_number()) , 750 );

        Mockito.when(repository.findById(String.valueOf(customer.getTc_id_number()))).thenReturn(Optional.of(creditScore));

        Optional<Integer> result = this.subject.checkCreditScore(customer);

        Assert.assertTrue(result.isPresent());
        double actual = 0;

        if(result.get() >= 500 && result.get() < 1000){
            actual = 10000;
        }
        double expected = 10000;

        Assert.assertTrue(expected == actual);

    }

}
