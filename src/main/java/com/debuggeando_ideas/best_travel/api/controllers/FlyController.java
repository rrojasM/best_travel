package com.debuggeando_ideas.best_travel.api.controllers;

import com.debuggeando_ideas.best_travel.api.models.response.FlyResponse;
import com.debuggeando_ideas.best_travel.infraestructure.abstract_services.IFlyService;
import com.debuggeando_ideas.best_travel.util.annotations.Notify;
import com.debuggeando_ideas.best_travel.util.enums.SortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping(path = "/fly")
@AllArgsConstructor
@Tag(name = "Fly")
public class FlyController {
    private final IFlyService flyService;
    private IFlyService ticketService;

    @Operation(summary = "Return a page with flights can be sorted or not")
    @GetMapping
    @Notify
    public ResponseEntity<Page<FlyResponse>> getAll(@RequestParam Integer page, @RequestParam Integer size, @RequestHeader(required = false) SortType sortType){
        if (Objects.isNull(sortType)) sortType = SortType.NONE;
        var response = this.flyService.readAll(page, size, sortType);
        return  response.isEmpty()? ResponseEntity.noContent().build(): ResponseEntity.ok(response);
    }

    @Operation(summary = "Return a list with flights with price less to prices in")
    @GetMapping(path = "/less_price")
    public ResponseEntity<Set<FlyResponse>> getLessPrice(@RequestParam BigDecimal price){
        var response = this.flyService.readLessPrice(price);
        return  response.isEmpty()? ResponseEntity.noContent().build(): ResponseEntity.ok(response);
    }

    @GetMapping(path = "/between_price")
    public ResponseEntity<Set<FlyResponse>> getBetweenPrice(@RequestParam BigDecimal min, @RequestParam BigDecimal max){
        var response = this.flyService.readBetweenPrices(min, max);
        return  response.isEmpty()? ResponseEntity.noContent().build(): ResponseEntity.ok(response);
    }

    @GetMapping(path = "/origin_destiny")
    public ResponseEntity<Set<FlyResponse>> getOriginDestiny(@RequestParam String origin, @RequestParam String destiny){
        var response = this.flyService.readByOriginDestiny(origin, destiny);
        return  response.isEmpty()? ResponseEntity.noContent().build(): ResponseEntity.ok(response);
    }

    @GetMapping(path = "/get_price")
    public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(
            @RequestParam Long flyId,
            @RequestHeader(required = false) Currency currency) {
        if (Objects.isNull(currency)) currency = Currency.getInstance("USD");
        return ResponseEntity.ok(Collections.singletonMap("flyPrice", this.ticketService.findPrice(flyId, currency)));
    }
}
