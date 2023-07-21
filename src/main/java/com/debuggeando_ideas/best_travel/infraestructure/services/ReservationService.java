package com.debuggeando_ideas.best_travel.infraestructure.services;

import com.debuggeando_ideas.best_travel.api.models.request.ReservationRequest;
import com.debuggeando_ideas.best_travel.api.models.response.FlyResponse;
import com.debuggeando_ideas.best_travel.api.models.response.HotelResponse;
import com.debuggeando_ideas.best_travel.api.models.response.ReservationResponse;
import com.debuggeando_ideas.best_travel.domain.entities.ReservationEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.CustomerRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.HotelRepository;
import com.debuggeando_ideas.best_travel.domain.repositories.ReservationRepository;
import com.debuggeando_ideas.best_travel.infraestructure.abstract_services.IReservationService;
import com.debuggeando_ideas.best_travel.util.BestTravelUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final CustomerRepository customerRepository;
    private final HotelRepository hotelRepository;
    private final ReservationRepository reservationRepository;
    @Override
    public ReservationResponse create(ReservationRequest request) {
        var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow();
        var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

        var reservationToPersist = ReservationEntity.builder()
                .id(UUID.randomUUID())
                .hotel(hotel)
                .customer(customer)
                .dateTimeReservation(LocalDateTime.now())
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
                .totalDays(request.getTotalDays())
                .price(hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage)))
                .build();
        var reservationPersisted = reservationRepository.save(reservationToPersist);

        log.info("Ticket saved with id : {}", reservationPersisted.getId());
        return this.entityToResponse(reservationPersisted);
    }

    @Override
    public ReservationResponse read(UUID id) {
        var reservationFromDb = reservationRepository.findById(id).orElseThrow();
        return this.entityToResponse(reservationFromDb);
    }

    @Override
    public ReservationResponse update(ReservationRequest request, UUID id) {

        var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow();

        var reservationToUpdate = this.reservationRepository.findById(id).orElseThrow();

        reservationToUpdate.setHotel(hotel);
        reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
        reservationToUpdate.setDateStart(LocalDate.now());
        reservationToUpdate.setDateEnd(LocalDate.now());
        reservationToUpdate.setTotalDays(request.getTotalDays());
        reservationToUpdate.setPrice(hotel.getPrice().multiply(charges_price_percentage));

        var reservationUpdated = reservationRepository.save(reservationToUpdate);
        log.info("Reservation Updated with id{}", reservationUpdated.getId());
        return entityToResponse(reservationUpdated);
    }

    @Override
    public void delete(UUID id) {
        var reservationToDelete = reservationRepository.findById(id).orElseThrow();
        this.reservationRepository.delete(reservationToDelete);
    }

    @Override
    public BigDecimal findPrice(Long hotelId) {
        var hotel = hotelRepository.findById(hotelId).orElseThrow();
        return hotel.getPrice().add(hotel.getPrice().multiply(charges_price_percentage));
    }

    private ReservationResponse entityToResponse( ReservationEntity entity){
        var response = new ReservationResponse();
        BeanUtils.copyProperties(entity, response);

        var hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(entity.getHotel(), hotelResponse);

        response.setHotel(hotelResponse);
        return response;
    }

    private static final BigDecimal charges_price_percentage = BigDecimal.valueOf(0.20);
}
