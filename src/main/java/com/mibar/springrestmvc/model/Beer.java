package com.mibar.springrestmvc.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

//model folder --> Where our POJOs will reside
@Data     // Adds Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode
@Builder  // Helpful mechanism for using the Builder pattern without writing boilerplate code
public class Beer {
    private UUID id;
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;



}
