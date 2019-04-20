package com.telerikacademy.carservice.models;

import org.springframework.ui.Model;

import javax.persistence.*;
import javax.validation.constraints.Size;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customerID;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "model_id")
    private Models modelID;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "car_event_id")
    private CarEvent carEventID;

    @Column(name= "year")
    private Integer yearOfPruduction;

    @Column(name= "license_plate")
    @Size(min = 6, max = 8, message = "Incorrect size for Licese Plate Number.")
    private String licensePlate;

    @Column(name= "VIN")
    @Size(min = 16, max = 18, message = "Incorrect size for VIN Number.")
    private String vinNumber;

    public CustomerCars() {
    }


    public CustomerCars(Customer customerID
            , Models modelID
            , CarEvent carEventID
            , Integer yearOfPruduction
            , String licensePlate
            , String vinNumber) {
        this.customerID = customerID;
        this.modelID = modelID;
        this.carEventID = carEventID;
        this.yearOfPruduction = yearOfPruduction;
        this.licensePlate = licensePlate;
        this.vinNumber = vinNumber;
    }
}
