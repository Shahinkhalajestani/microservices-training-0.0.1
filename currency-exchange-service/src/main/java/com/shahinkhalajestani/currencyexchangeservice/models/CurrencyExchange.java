package com.shahinkhalajestani.currencyexchangeservice.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "currency_exchange")
public class CurrencyExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name = "currency_from")
    private String from;
    @Column(name = "currency_to")
    private String to ;
    private BigDecimal conversionMultiple;
    private String environment;
}
