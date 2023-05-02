package com.mibar.springrestmvc.bootstrap;

import com.mibar.springrestmvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest        // Annotation for JPA tests that focuses only on JPA components. This annotation will disable
    //fill auto-configuration and instead apply only configuration relevant to JPA tests
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    //TODO: Implement the tests to the Customer Repository

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository);
    }

    @Test
    void Testrun() throws Exception {
        bootstrapData.run(null);

        //assert that three beer objects have been added into the database
        assertThat(beerRepository.count()).isEqualTo(3);
    }
}