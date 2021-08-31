package com.shahinkhalajestani.currencyexchangeservice.repository;

import com.shahinkhalajestani.currencyexchangeservice.models.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange,Long> {
    List<CurrencyExchange> findAllByFromAndTo(String from, String to);
}
