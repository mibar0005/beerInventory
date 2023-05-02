package com.mibar.springrestmvc.repositories;

import com.mibar.springrestmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest  // --> Annotation for JPA test that focuses only on JPA components. This will disable full
// auto-configuration and instead apply only configurations relevant to JPA tests.
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    //Create a new test to make sure a beer can be saved
    @Test
    void testSaveBeer() {
        //Use a builder to create a new beer (just the beer name for now)
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("My Beer")
                        .build());
        //Create assertions to make sure the ID and the Beer objects are not null
        //Remember that Id creation will be handled by hibernate so it should not be null
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();

    }

}