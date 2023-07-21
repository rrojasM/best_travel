package com.debuggeando_ideas.best_travel.domain.repositories;

import com.debuggeando_ideas.best_travel.domain.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
@Repository
public interface HotelRepository extends JpaRepository<HotelEntity,Long> {
    Set<HotelEntity> findByPriceLessThan(BigDecimal price);
    Set<HotelEntity> findByPriceIsBetween(BigDecimal min, BigDecimal max);
    Set<HotelEntity> findByRatingGreaterThan(Integer rating);
    Optional<HotelEntity> findByReservationId(UUID id);
}
