package com.debuggeando_ideas.best_travel.domain.repositories;

import com.debuggeando_ideas.best_travel.domain.entities.FlyEntity;
import com.debuggeando_ideas.best_travel.domain.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {
}
