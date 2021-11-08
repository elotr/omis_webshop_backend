package com.elo.webshopbackend.model.output;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data  // v]tab k]ik getterid konstruktorid jne
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
public class EveryPayData {

    private String api_username;
    private String account_name;
    private BigDecimal amount;
    private String order_reference;
    private String token_agreement;
    private String nonce;
    private ZonedDateTime timestamp;
    private String customer_url;

}
