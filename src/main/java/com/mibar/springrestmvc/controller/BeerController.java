package com.mibar.springrestmvc.controller;

import com.mibar.springrestmvc.model.BeerDTO;
import com.mibar.springrestmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@Controller --> Helps create MVC controllers and handling HTTP requests It allows developers to easily map
// specific methods in a controller class to specific HTTP requests

//combines @Controller and @ResponseBody – which eliminates the need to annotate every request
//handling method of the controller class with the @ResponseBody annotation.
@RestController
@RequiredArgsConstructor
@Slf4j  // Lombok logger
@RequestMapping("/api/v1/beer")
public class BeerController {
    //Bring in the BeerService
    private final BeerService beerService;


    //Create a method that returns a list of all beers
//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<BeerDTO> listBeers() {
        return beerService.listBeers();
    }


    //Create a method to get a Beer by Id
    //@PathVariable --> Used to handle template variables in the request URI mapping, and set them as method parameters
    //Need to specify the path variable in curly braces, and inside the @PathVariable annotation
//    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    @GetMapping("{beerId}")
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("Get beer by Id - in controller");
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }


    //Create a POST method to accept a new beer object
    //This method should return a ResponseEntity and takes in a Beer object
    //@RequestBody --> annotation maps the HttpRequest body to a transfer or domain object,
    // enabling automatic deserialization of the inbound HttpRequest body onto a Java object.
    //@RequestBody --> annotation tells a controller that the object returned is automatically serialized into
    // JSON and passed back into the HttpResponse object
    @PostMapping
    public ResponseEntity handlePost(@RequestBody BeerDTO beer) {
        //Call the saveNewBeer method in beerService and pass in the beer object
        BeerDTO savedBeer = beerService.saveNewBeer(beer);

        //Create an HTTP header with the Location and the header value
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        //Return an HTTP header and status of (201)
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    //Create a method that updates a resource and returns a Response Entity
    //You will use @PathVariable(to find the beerId) and @RequestBody(to update the Beer Object)
    @PutMapping("{beerId}")
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {

        //Call the updateBeerById method on beerService
        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    //Create a method to PATCH a beer object
    @PatchMapping("{beerId}")
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {

        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    //Create a method to delete a beer. This method should also return a response entity.
    //This method also needs to use the @PathVariable annotation
    @DeleteMapping("{beerId}")
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {

        //call the deleteById method on beerService
        beerService.deleteById(beerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }




}
