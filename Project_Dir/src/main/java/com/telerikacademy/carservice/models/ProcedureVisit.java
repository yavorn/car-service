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
    private Long procedureVisitID;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "car_event_id")
    private CarEvent carEvent;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "procedure_id")
    private Procedure procedure;


    private boolean isPdfGenerated;

    public Long getProcedureVisitID() {
        return procedureVisitID;
    }

    public void setProcedureVisitID(Long procedureVisitID) {
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


    public boolean isProcedureVisitDeleted() {
        return procedureVisitDeleted;
    }

    public void setProcedureVisitDeleted() {
        this.procedureVisitDeleted = true;
    }

    public void setProcedureVisitNotDeleted() {
        this.procedureVisitDeleted = false;
    }

    public boolean isPdfGenerated() {
        return isPdfGenerated;
    }

    public void setPdfGenerated(boolean pdfGenerated) {
        isPdfGenerated = pdfGenerated;
    }
}
