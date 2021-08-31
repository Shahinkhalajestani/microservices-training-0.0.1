package com.shahinkhalajestani.currencyconversionservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyConversion {
    private Long id ;
    private String from;
    private String to ;
    private BigDecimal conversionMultiple;
    private BigDecimal totalCalculateAmount;
    private String environment;
}
