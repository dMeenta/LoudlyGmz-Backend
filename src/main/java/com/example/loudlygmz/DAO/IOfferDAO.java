package com.example.loudlygmz.DAO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.loudlygmz.entity.Offer;

public interface IOfferDAO extends JpaRepository<Offer, Integer> {

    @Query("SELECT o FROM Offer o WHERE o.startDate <= :currentDate AND o.endDate >= :currentDate")
    List<Offer> findActiveOffers(@Param("currentDate") LocalDate currentDate);
}
