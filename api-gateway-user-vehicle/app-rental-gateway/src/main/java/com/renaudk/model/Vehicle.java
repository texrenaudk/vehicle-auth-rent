package com.renaudk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class Vehicle {



    private Long id;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Status status;


    private String owner;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date associationDate;


    private String brand;


    private String model;

}
