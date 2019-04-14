package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Make;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MakeRepository extends JpaRepository<Make, Long> {

    Make findMakeByMakeID(Long id);
    Make findMakeByMakeName(String name);

}
