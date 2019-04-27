package com.telerikacademy.carservice.models;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMin;

@Entity
@Table(name = "`procedure`")
public class Procedure {

    public Procedure(){

    }

    public Procedure(@Valid String procedureName, double procedurePrice) {
        this.procedureName = procedureName;
        this.procedurePrice = procedurePrice;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "procedure_id")
    private Long procedureID;

    @Size(min = 3, max = 40, message = "Procedure name must be between 3 and 40 symbols")
    @Column(name = "procedure_name")
    private String procedureName;

    @DecimalMin(value = "0.0", message = "Price should be a positive number")
    @Column(name = "procedure_price")
    private double procedurePrice;


    @Column(name = "is_deleted", nullable = false, columnDefinition = "tinyint(4) default 0")
    private boolean procedureDeleted;

    public Long getProcedureID() {
        return procedureID;
    }

    public void setProcedureID(Long procedureID) {
        this.procedureID = procedureID;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public double getProcedurePrice() {
        return procedurePrice;
    }

    public void setProcedurePrice(double procedurePrice) {
        this.procedurePrice = procedurePrice;
    }


    public boolean isProcedureDeleted() {
        return procedureDeleted;
    }

    public void setProcedureDeleted() {
        this.procedureDeleted = true;
    }

    public void setProcedureNotDeleted() {
        this.procedureDeleted = false;
    }
}
