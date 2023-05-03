package com.mibar.springrestmvc.mappers;

import com.mibar.springrestmvc.entities.Beer;
import com.mibar.springrestmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);


}
