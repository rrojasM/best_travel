package com.debuggeando_ideas.best_travel.infraestructure.services;

import com.debuggeando_ideas.best_travel.api.models.response.FlyResponse;
import com.debuggeando_ideas.best_travel.domain.entities.jpa.FlyEntity;
import com.debuggeando_ideas.best_travel.domain.repositories.jpa.FlyRepository;
import com.debuggeando_ideas.best_travel.infraestructure.abstract_services.IFlyService;
import com.debuggeando_ideas.best_travel.infraestructure.helpers.ApiCurrencyConnectorHelper;
import com.debuggeando_ideas.best_travel.util.constants.CacheConstants;
import com.debuggeando_ideas.best_travel.util.enums.SortType;
import com.debuggeando_ideas.best_travel.util.enums.Tables;
import com.debuggeando_ideas.best_travel.util.exceptions.IdNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@AllArgsConstructor
public class FlyService implements IFlyService {

    private final FlyRepository flyRepository;
    private final ApiCurrencyConnectorHelper currencyConnectorHelper;
    //private final WebClient webClient;
    @Override
    public Page<FlyResponse> readAll(Integer page, Integer size, SortType sortType) {
        PageRequest pageRequest = null;

        switch (sortType){
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case UPPER -> pageRequest = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return this.flyRepository.findAll(pageRequest).map(this::entityToResponse);
    }
    @Override
    @Cacheable(value = CacheConstants.FLY_CACHE_NAME)
    public Set<FlyResponse> readLessPrice(BigDecimal price) {
        return this.flyRepository.selectLessPrice(price).stream().map(this::entityToResponse).collect(Collectors.toSet());
    }
    @Override
    @Cacheable(value = CacheConstants.FLY_CACHE_NAME)
    public Set<FlyResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
        return this.flyRepository.selectBetweenPrice(min, max).stream().map(this::entityToResponse).collect(Collectors.toSet());
    }
    @Override
    @Cacheable(value = CacheConstants.FLY_CACHE_NAME)
    public Set<FlyResponse> readByOriginDestiny(String origen, String destiny) {
        return this.flyRepository.selectOriginDestiny(origen, destiny).stream().map(this::entityToResponse).collect(Collectors.toSet());
    }

    @Override
    public BigDecimal findPrice(Long flyId, Currency currency) {
        var fly = this.flyRepository.findById(flyId).orElseThrow(() -> new IdNotFoundException(Tables.fly.name()));
        if (currency.equals(Currency.getInstance("USD"))) return fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage));
        var currencyDTO = this.currencyConnectorHelper.getCurrencyDTO(currency);
        return fly.getPrice().add(fly.getPrice().multiply(charger_price_percentage)).multiply(currencyDTO.getResult());
    }

    private FlyResponse entityToResponse(FlyEntity entity){
        FlyResponse response = new FlyResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
    public static final BigDecimal charger_price_percentage = BigDecimal.valueOf(0.20);
}
