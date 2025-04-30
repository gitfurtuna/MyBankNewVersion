package com.mybank.merch.web.controller;

import com.mybank.merch.domain.model.Merch;
import com.mybank.merch.domain.service.MerchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/merch")
@RequiredArgsConstructor
public class MerchController {

    private final MerchService merchService;

    @GetMapping
    private ResponseEntity<List<Merch>> getAllMerch() {
        List<Merch> result = merchService.showAllMerch();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

