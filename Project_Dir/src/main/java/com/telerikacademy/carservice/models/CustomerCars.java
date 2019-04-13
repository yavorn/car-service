package com.telerikacademy.carservice.models;

import org.springframework.ui.Model;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


@Entity
@Table(name = "customer_cars")
public class CustomerCars {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "customer_car_id")
    private Long customerCarID;




}
