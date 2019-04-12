package com.telerikacademy.carservice.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

@Entity
@Table(name = "models")
public class Models {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "model_id")
    private int modelID;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "make_id")
    private Make make;

    @Column(name = "model_name")
    @Size(min = 2, max = 30, message = "Incorrect size for model name.")
    private String modelName;

    public Models() {
    }

    public Models(Make make, @Size(min = 1, max = 30, message = "Incorrect size for model name.") String modelName) {
        this.make = make;
        this.modelName = modelName;
    }

    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public Make getMake() {
        return make;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
