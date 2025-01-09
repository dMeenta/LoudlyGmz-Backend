package com.example.loudlygmz.services.Offer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.DAO.IOfferDAO;
import com.example.loudlygmz.entity.Offer;

@Service
public class OfferService implements IOfferService {

    @Autowired
    private IOfferDAO offerDAO;

    @Override
    public List<Offer> getActiveOffers(LocalDate currentDate) {
        return offerDAO.findActiveOffers(currentDate);
    }

}
