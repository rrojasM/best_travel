package com.debuggeando_ideas.best_travel.infraestructure.helpers;

import com.debuggeando_ideas.best_travel.domain.repositories.ReservationRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AllArgsConstructor
@Component
public class TourHelper {
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;
}
