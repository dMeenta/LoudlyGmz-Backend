package com.example.loudlygmz.services.Offer;

import java.time.LocalDate;
import java.util.List;

import com.example.loudlygmz.entity.Offer;

public interface IOfferService {
    public List<Offer> getActiveOffers(LocalDate currentDate);
}
