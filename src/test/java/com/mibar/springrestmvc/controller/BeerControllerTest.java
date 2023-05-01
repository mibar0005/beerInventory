package com.mibar.springrestmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mibar.springrestmvc.model.Beer;
import com.mibar.springrestmvc.service.BeerService;
import com.mibar.springrestmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    //Object Mapper helps us serialize and deserialize data from JSON. POJOs to JSON and vice versa
    @Autowired
    ObjectMapper objectMapper;

    BeerServiceImpl beerServiceImpl;

    //Use Captor's to Id and Beer
    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    //Initialize the BeerServiceImpl inside the before each so we have a fresh list after each test
    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }



    @Test
    void getBeerById() throws Exception {
        //Create a test beer, use the beerServiceImpl's listBeers() to grab any beer by index
        Beer testBeer = beerServiceImpl.listBeers().get(0);
        //Given any UUID id, and pass it to the getBeerById method, will return the testBeer
        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));
        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }


    //We are going to throw an Exception using Mockito for when get beer by Id is not found
    @Test
    void getBeerByIdNotFound() throws Exception {

        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());

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

    @Test
    void testCreateNewBeer() throws Exception {
        //Create a beer Object and store a beer in that object
        Beer beer = beerServiceImpl.listBeers().get(0);
        //Set up MockMvc to pass in that beer object as JSON into the controller and allow the controller
        //to create the action.
        //Set Id and Version to null for now
        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        //place the body of the JSON file here
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }


    //We are updating the service
    //We are going to verify the interaction --> there is not much mocking for this
    //but we can verify that this was called
    @Test
    void testUpdateBeer() throws Exception {
        //Grab a beer from the beerList and store it in a beer object
        Beer beer = beerServiceImpl.listBeers().get(0);
        //We dont have a given(), so we can get straight to the Mocking
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/beer/" + beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        //use verify() method to make sure the interaction occurred
        verify(beerService).updateBeerById(any(UUID.class), any(Beer.class));
    }

    //Create a test method to delete a beer
    @Test
    void testDeleteBeer() throws Exception {
        //Store a beer into a beer object
        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete("/api/v1/beer/" + beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());


    }


    @Test
    void testPatchBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        //Create a new beerMap and add a K,V pair
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch("/api/v1/beer/" + beer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }


}