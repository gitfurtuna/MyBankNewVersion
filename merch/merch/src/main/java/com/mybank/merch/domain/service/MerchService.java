package com.mybank.merch.domain.service;

import com.mybank.merch.datasource.mapper.DataDomainMerchMapper;
import com.mybank.merch.datasource.model.MerchEntity;
import com.mybank.merch.datasource.repository.MerchRepository;
import com.mybank.merch.domain.model.Merch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchService {

 private final MerchRepository merchRepository;

 private final DataDomainMerchMapper dataDomainMerchMapper;


    public List<Merch> showAllMerch() {
        List<MerchEntity> merchEntity = merchRepository.findAll();
        return dataDomainMerchMapper.toMerchList(merchEntity);
    }


}
