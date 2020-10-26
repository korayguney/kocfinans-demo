package com.kocfinans.creditscore.utils;

import com.kocfinans.creditscore.models.CreditScore;
import com.kocfinans.creditscore.models.CreditScoreDTO;
import com.kocfinans.creditscore.repositories.CreditScoreRepository;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CreditScoreLoaderUtil {

    @Autowired
    private CreditScoreRepository scoreRepository;

    @Value("${kocfinans.mockdata}")
    private String testDataUrl;

    /*
        * this method saves mock credit notes to Redis
        * Runs every 6 hour
     */
    @Scheduled(fixedRate = 6 * 1000 * 60 * 60)
    private void saveMockCreditScores() {
        try {
            CreditScoreDTO scores = restTemplate().getForObject(testDataUrl, CreditScoreDTO.class);
            scores.getScores().forEach((key, value) -> {
                CreditScore score = new CreditScore(key, value);
                this.scoreRepository.save(score);
            });
        } catch (RestClientException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
        Generates a RestTemplate object which will not attempt to validate certificate via https url
     */
    @Bean
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate = contentTypeHelper(restTemplate);
        return restTemplate;
    }

    /*
        Add text/json support to 'testDataUrl' for RestTemplate
     */
    public RestTemplate contentTypeHelper(RestTemplate restTemplatex) {
        restTemplatex = new RestTemplateBuilder().build();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplatex.setMessageConverters(messageConverters);
        return restTemplatex;
    }

}
