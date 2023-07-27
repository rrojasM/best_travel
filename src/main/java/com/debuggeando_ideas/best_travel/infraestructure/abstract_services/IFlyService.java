package com.debuggeando_ideas.best_travel.infraestructure.abstract_services;

import com.debuggeando_ideas.best_travel.api.models.response.FlyResponse;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

public interface IFlyService extends CatalogService<FlyResponse> {
    Set<FlyResponse> readByOriginDestiny(String origen, String destiny);

    BigDecimal findPrice(Long flyId, Currency currency);
}
