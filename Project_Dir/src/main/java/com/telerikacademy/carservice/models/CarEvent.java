package com.telerikacademy.carservice.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.time.LocalDate;

@Entity
@Table(name = "car_event")
public class CarEvent {

    public CarEvent(){

    }

    public CarEvent(LocalDate date, CustomerCars customerCar, @DecimalMin(value = "0.0", message = "Price should be a positive number") double totalPrice, int finalized) {
        this.date = date;
        this.customerCar = customerCar;
        this.totalPrice = totalPrice;
        this.finalized = finalized;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_event_id")
    private Long carEventID;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_car_id")
    private CustomerCars customerCar;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name ="date")
    private LocalDate date;

    @DecimalMin(value = "0.0", message = "Price should be a positive number")
    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "finalized")
    private int finalized;

    public CustomerCars getCustomerCar() {
        return customerCar;
    }

    public void setCustomerCar(CustomerCars customerCar) {
        this.customerCar = customerCar;
    }


    public Long getCarEventID() {
        return carEventID;
    }

    public void setCarEventID(Long carEventID) {
        this.carEventID = carEventID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getFinalized() {
        return finalized;
    }

    public void setFinalized(int finalized) {
        this.finalized = finalized;
    }
}
