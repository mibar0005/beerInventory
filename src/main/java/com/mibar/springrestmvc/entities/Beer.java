package com.mibar.springrestmvc.entities;

import com.mibar.springrestmvc.model.BeerStyle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

//This is going to have the same properties at the BeerDTO (1 to 1 copy)
//Only difference is one is going to be the DTO and the other one will be a JPA entity
//We can use the @Data and @Builder annotation as well
@Builder
// @Data  --> It is not recommended to use @Data when working with the Databases. Should use your getter and
// setter annotations instead
@Getter
@Setter
@Entity  // Entities Represent persistent object stores as a record in the database. It represents a table
//in a database each entity instance corresponds to a value on the table

//Don't forget to add your constructors...
@AllArgsConstructor
@NoArgsConstructor
public class Beer {

    @Id  // --> the object id should correspond to the primary key of the object's table.
    //We need to set up our Id so that Hibernate can manage this. Use GeneratedValue bean
    @GeneratedValue(generator = "UUID")
    //This is where we tell which generator to use
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    //When we create a column, we can pass properties to that column
    @Column(length = 36, columnDefinition ="varchar", updatable = false, nullable = false)
    private UUID id;

    @Version  // --> allows your entities to detect concurrent modifications to the same datastore record.
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
