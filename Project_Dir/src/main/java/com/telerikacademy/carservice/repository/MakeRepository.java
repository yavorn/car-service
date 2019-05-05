package com.telerikacademy.carservice.repository;

import com.telerikacademy.carservice.models.Make;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MakeRepository extends JpaRepository<Make, Long> {

    List<Make> getAllByMakeDeletedFalseOrderByMakeNameAsc();
    Make findMakeByMakeIDAndMakeDeletedTrue(Long id);
    Make findMakeByMakeIDAndMakeDeletedFalse(Long id);
    Make findMakeByMakeNameAndMakeDeletedFalse(String name);

}
