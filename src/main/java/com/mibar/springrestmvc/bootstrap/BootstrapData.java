package com.mibar.springrestmvc.bootstrap;

import com.mibar.springrestmvc.entities.Beer;
import com.mibar.springrestmvc.model.BeerStyle;
import com.mibar.springrestmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    //Bring in the two repositories
    private final BeerRepository beerRepository;

    //TODO: bring in the Customer Repository
    @Override
    public void run(String... args) throws Exception {
        //Create method to initialize the data
        loadBeerData();
        //TODO: Implement a method to load the customer data
    }

    private void loadBeerData() {
        //whenever you work with an in-memory database you are always going to start at 0
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("123456")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("The Can Can")
                    .beerStyle(BeerStyle.IPA)
                    .upc("987654")
                    .price(new BigDecimal("18.99"))
                    .quantityOnHand(12)
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Riverside Rachets")
                    .beerStyle(BeerStyle.SOUR)
                    .upc("739182")
                    .price(new BigDecimal("16.99"))
                    .quantityOnHand(58)
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }

    //TODO: Implement the loadCustomerData and save the customers to the H2 DB

}
