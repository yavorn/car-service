package com.telerikacademy.carservice.models;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name = "procedure_visit")
public class ProcedureVisit {

    public ProcedureVisit(){

    }

    public ProcedureVisit(@Valid  CarEvent carEvent, Procedure procedure) {
        this.carEvent = carEvent;
        this.procedure = procedure;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "procedure_visit_id")
    private int procedureVisitID;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "car_event_id")
    private CarEvent carEvent;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "procedure_id")
    private Procedure procedure;

    public int getProcedureVisitID() {
        return procedureVisitID;
    }

    public void setProcedureVisitID(int procedureVisitID) {
        this.procedureVisitID = procedureVisitID;
    }

    public CarEvent getCarEvent() {
        return carEvent;
    }

    public void setCarEvent(CarEvent carEvent) {
        this.carEvent = carEvent;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }
}