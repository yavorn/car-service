package com.telerikacademy.carservice.models;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Table(name = "customer_cars")
public class CustomerCars  {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "customer_car_id")
    private Long customerCarID;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customerID;

    @OneToOne
    @JoinColumn(name = "model_id")
    private Models modelID;



    @Column(name= "year")
    private Integer yearOfProduction;

    @Column(name= "license_plate")
    @Size(min = 6, max = 8, message = "Incorrect size for Licese Plate Number.")
    private String licensePlate;

    @Column(name= "VIN")
    @Size(min = 16, max = 18, message = "Incorrect size for VIN Number.")
    private String vinNumber;

    public CustomerCars() {
    }


    public CustomerCars(Customer customerID,
                        Models modelID,
                        Integer yearOfProduction,
                        String licensePlate,
                        String vinNumber) {
        this.customerID = customerID;
        this.modelID = modelID;
        this.yearOfProduction = yearOfProduction;
        this.licensePlate = licensePlate;
        this.vinNumber = vinNumber;
    }

    public Long getCustomerCarID() {
        return customerCarID;
    }

    public void setCustomerCarID(Long customerCarID) {
        this.customerCarID = customerCarID;
    }

    public Customer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Customer customerID) {
        this.customerID = customerID;
    }

    public Models getModelID() {
        return modelID;
    }

    public void setModelID(Models modelID) {
        this.modelID = modelID;
    }

    public Integer getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(Integer yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }
}
