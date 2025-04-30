package com.mybank.merch.datasource.mapper;

import com.mybank.merch.datasource.model.MerchEntity;
import com.mybank.merch.datasource.repository.MerchRepository;
import com.mybank.merch.domain.model.Merch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DataDomainMerchMapper {

    Merch toMerch (MerchEntity merchEntity);

    MerchEntity fromMerch (Merch merch);

}
