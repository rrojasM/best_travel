package com.debuggeando_ideas.best_travel.domain.repositories;

import com.debuggeando_ideas.best_travel.domain.entities.FlyEntity;
import com.debuggeando_ideas.best_travel.domain.entities.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends CrudRepository<TourEntity,Long> {
}
