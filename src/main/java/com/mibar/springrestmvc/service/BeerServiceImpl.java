package com.mibar.springrestmvc.service;

import com.mibar.springrestmvc.model.Beer;
import com.mibar.springrestmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
//I am not implementing a DB at the moment, we are going to mock out a DB and manually add this

//Used for organizing and separating business logic and data manipulation tasks in a Spring Boot.
//Denotes which classes in your application are responsible for performing specific services and functions.
@Service
@Slf4j   //Used for Logging purposes
public class BeerServiceImpl implements BeerService {

    //Create a Map to store the Beer Id and the Beer Object
    private Map<UUID, Beer> beerMap;

    //Create a constructor and initialize the beerMap
    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        //Create three beers and store them into beerMap
        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("The Can Can")
                .beerStyle(BeerStyle.IPA)
                .upc("987654")
                .price(new BigDecimal("18.99"))
                .quantityOnHand(12)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Riverside Rachets")
                .beerStyle(BeerStyle.SOUR)
                .upc("739182")
                .price(new BigDecimal("16.99"))
                .quantityOnHand(58)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    //Create a method that will return a list of all beers
    @Override
    public List<Beer> listBeers() {
        //We want to return a new array with the beerMap values (The Beer Objects)
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Get Beer by Id -  In service. Id: " + id.toString());
        //Call the beerMap and ".get()" the beer by id
        return beerMap.get(id);
    }

    @Override
    public Beer saveNewBeer(Beer beer) {
        //We are creating and returning a Beer object bc we are mimicking the persistence store
        Beer savedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .upc(beer.getUpc())
                .version(beer.getVersion())
                .price(beer.getPrice())
                .build();

        beerMap.put(savedBeer.getId(), savedBeer);

        return savedBeer;
    }

    @Override
    public void updateBeerById(UUID beerId, Beer beer) {
        Beer existing = beerMap.get(beerId);
        existing.setBeerName(beer.getBeerName());
        existing.setBeerStyle(beer.getBeerStyle());
        existing.setPrice(beer.getPrice());
        existing.setUpc(beer.getUpc());
        existing.setQuantityOnHand(beer.getQuantityOnHand());
        existing.setVersion(beer.getVersion());

        //Add the beer back into the beerMap
        beerMap.put(existing.getId(), existing);
    }




    @Override
    public void deleteById(UUID beerId) {
        //call the Maps remove method and pass in the beerId
        beerMap.remove(beerId);
    }

    @Override
    public void patchBeerById(UUID beerId, Beer beer) {
        Beer existing = beerMap.get(beerId);

        if (StringUtils.hasText(beer.getBeerName())) {
            existing.setBeerName(beer.getBeerName());
        }

        if (beer.getBeerStyle() != null) {
            existing.setBeerStyle(beer.getBeerStyle());
        }

        if (beer.getPrice() != null) {
            existing.setPrice(beer.getPrice());
        }

        if (beer.getVersion() != null) {
            existing.setVersion(beer.getVersion());
        }

        if (beer.getQuantityOnHand() != null) {
            existing.setQuantityOnHand(beer.getQuantityOnHand());
        }

        if (StringUtils.hasText(beer.getUpc())) {
            existing.setUpc(beer.getUpc());
        }
    }
}
