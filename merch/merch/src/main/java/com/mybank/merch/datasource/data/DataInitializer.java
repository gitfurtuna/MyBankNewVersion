package com.mybank.merch.datasource.data;

import com.mybank.merch.datasource.mapper.DataDomainMerchMapper;
import com.mybank.merch.datasource.repository.MerchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MerchRepository merchRepository;

    private final DataDomainMerchMapper dataDomainMerchMapper;

    @Override
    public void run(String... args) throws Exception {

    }
}
