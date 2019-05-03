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
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "model_id")
    private Models model;

    @Column(name= "year")
    private Integer yearOfProduction;

    @Column(name= "license_plate")
    @Size(min = 6, max = 8, message = "Incorrect size for Licese Plate Number.")
    private String licensePlate;

    @Column(name= "VIN")
    @Size(min = 16, max = 18, message = "Incorrect size for VIN Number.")
    private String vinNumber;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(4) default 0")
    private boolean customerCarDeleted;

    public CustomerCars() {

    }


    public CustomerCars(Customer customer,
                        Models model,
                        Integer yearOfProduction,
                        String licensePlate,
                        String vinNumber) {
        this.customer = customer;
        this.model = model;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Models getModel() {
        return model;
    }

    public void setModel(Models model) {
        this.model = model;
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

    public boolean isCustomerCarDeleted() {
        return customerCarDeleted;
    }

    public void setCustomerCarDeleted(boolean customerCarDeleted) {
        this.customerCarDeleted = true;
    }

    public void setCustomerCarUndeleted(boolean customerCarDeleted) {
        this.customerCarDeleted = false;
    }
}
