package com.mibar.springrestmvc.repositories;

import com.mibar.springrestmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
