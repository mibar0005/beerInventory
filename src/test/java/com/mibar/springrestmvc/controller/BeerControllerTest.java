package com.mibar.springrestmvc.controller;

import com.mibar.springrestmvc.model.Beer;
import com.mibar.springrestmvc.service.BeerService;
import com.mibar.springrestmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest     --> Loads the complete Spring application context

//We are indicating this is a Test Splice and we want to limit this to the BeerController class
@WebMvcTest(BeerController.class)
class BeerControllerTest {
    //Bring in the MockMvc Dependency
    //This Sets up MockMvc that will get autowired by the Spring framework
    @Autowired
    MockMvc mockMvc;

    //@MockBean --> Tells mockito that this is the service that we want to mock into the Spring Context (BeerService)
    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void getBeerById() throws Exception {
        //Create a test beer, use the beerServiceImpl's listBeers() to grab any beer by index
        Beer testBeer = beerServiceImpl.listBeers().get(0);
        //Given any UUID id, and pass it to the getBeerById method, will return the testBeer
        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);
        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }

    //Create a test to test for the list of beers
    @Test
    public void testListBeers() throws Exception {
        //given a list of beers from beer service will return the same as a beerServiceImpl list of beers
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());
        //Use mockMvn to perform a get on the endpoint to list beers. This should accept a MediaType of JSON
        //and make assertions regarding status code, the media content type, and the size of the list
        mockMvc.perform(get("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));


    }
}