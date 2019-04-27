package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Make;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MakeRepository extends JpaRepository<Make, Long> {

    List<Make> findAllByOrderByMakeNameAsc();
    List<Make> getAllByMakeDeletedFalseOrderByMakeNameAsc();
    Make findMakeByMakeID(Long id);
    Make findMakeByMakeName(String name);

}
