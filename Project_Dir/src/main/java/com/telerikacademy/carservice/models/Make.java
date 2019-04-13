package com.telerikacademy.carservice.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

@Entity
@Table(name = "make")
public class Make {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "make_id")
    private Long makeID;

    @Column(name = "make_name")
    @Size(min = 1, max = 30, message = "Incorrect size for make name.")
    private String makeName;


    public Make() {
    }

    public Make( String makeName) {
        this.makeName = makeName;
    }

    public Long getMakeID() {
        return makeID;
    }

    public void setMakeID(Long makeID) {
        this.makeID = makeID;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }
}
