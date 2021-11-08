package com.elo.webshopbackend.service;

import com.elo.webshopbackend.model.Item;
import com.elo.webshopbackend.model.input.EverypayResponse;
import com.elo.webshopbackend.model.output.EveryPayData;
import com.elo.webshopbackend.model.output.EveryPayLink;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Log4j2 //logifail
public class PaymentService {

    @Value("${everypay.url}")
    // need v''rtused teises failis - application.properties
    private String url;
    @Value("${everypay.userName}")
    private String userName;
    @Value("${everypay.accountName}")
    private String accountName;
    @Value("${everypay.tokenAgreement}")
    private String tokenAgreement;
    @Value("${everypay.customerUrl}")
    private String customerUrl;
    @Value("${everypay.authKey}")
    private String authKey;
    @Value("${everypay.nonceKey}")
    private String nonceKey;

    @Autowired
    RestTemplate restTemplate;

    public EveryPayLink makePayment(List<Item> orderItems, Long id) {
        log.info("Started Everypay payment");

        BigDecimal totalSum = orderItems.stream()
                .map(item -> item.getPrice()) // v6tab igalt tootelt hinna
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String orderId = id.toString();
        ZonedDateTime timestamp = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());

        EveryPayData data = new EveryPayData();
        data.setApi_username(userName);
        data.setAccount_name(accountName);
        data.setAmount(totalSum);
        data.setOrder_reference(orderId);
        data.setToken_agreement(tokenAgreement);
        try {
            data.setNonce(encode(userName + orderId + timestamp));
        } catch (Exception e) {
            log.error("Error while generating nonce {} ", e.getMessage());
        }
        data.setTimestamp(timestamp);
        data.setCustomer_url(customerUrl);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", authKey);
        HttpEntity<EveryPayData> requestData = new HttpEntity<>(data, httpHeaders);
        ResponseEntity<EverypayResponse> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, requestData, EverypayResponse.class);
            return new EveryPayLink(response.getBody().getPayment_link());
        } catch (RestClientException e) {
            log.error("Error while connecting Everypay - {}", e.getMessage());
            return null;
        }
    }

    public static String encode(String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        sha256_HMAC.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
        byte[] result = sha256_HMAC.doFinal("example".getBytes());
        return DatatypeConverter.printHexBinary(result);
    }

}
