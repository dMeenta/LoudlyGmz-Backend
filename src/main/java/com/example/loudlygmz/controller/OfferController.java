package com.example.loudlygmz.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.entity.Offer;
import com.example.loudlygmz.services.Offer.IOfferService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/offers")
public class OfferController {
    @Autowired
    private IOfferService offerService;

    @GetMapping()
    public List<Offer> getActiveOffers() {
        return offerService.getActiveOffers(LocalDate.now());
    }

}
