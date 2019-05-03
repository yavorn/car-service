package com.telerikacademy.carservice.models;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "car_event")
public class CarEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_event_id")
    private Long carEventID;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_car_id")
    private CustomerCars customerCar;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="date")
    private LocalDateTime date;

    @DecimalMin(value = "0.0", message = "Price should be a positive number")
    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "finalized" , nullable = false, columnDefinition = "tinyint(4) default 0")
    private boolean finalized;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(4) default 0")
    private boolean carEventDeleted;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "procedure_visit",
            joinColumns = { @JoinColumn(name = "car_event_id") },
            inverseJoinColumns = { @JoinColumn(name = "procedure_id") })
    private Set<Procedure> procedures ;

    public CarEvent(){
        // this.date = LocalDate.now();
        this.procedures = new HashSet<>();

    }


    public CarEvent(LocalDateTime date, CustomerCars customerCar, @DecimalMin(value = "0.0", message = "Price should be a positive number") double totalPrice) {
        this.date = date;
        this.customerCar = customerCar;
        this.totalPrice = totalPrice;
        this.procedures = new HashSet<>();

    }

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean getFinalized() {
        return finalized;
    }

    public void setFinalized(boolean finalized) {
        this.finalized = finalized;
    }

    public boolean isCarEventDeleted() {
        return carEventDeleted;
    }

    public void setCarEventDeleted() {
        this.carEventDeleted = true;
    }

    public Set<Procedure> getProcedures() {
        return this.procedures;
    }

    public void setProcedures(Set<Procedure> procedures) {
        this.procedures = procedures;
    }
}
