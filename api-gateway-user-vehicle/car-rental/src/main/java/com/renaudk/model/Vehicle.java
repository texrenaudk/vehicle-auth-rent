package com.renaudk.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Data
//@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 15)
    @Enumerated(EnumType.STRING) // vu que c 'est un enum, on precise que son type utilisé pour etre conservé dans la BD est une string
    private Status status;

    @Column
    private Long owner;

    @Temporal(TemporalType.TIMESTAMP) // nous permet de conserver la date en timestamp, on aurait pu mettre en date,time juste
    @Column(length = 30)
    private Date associationDate;

    @Column(length = 30)
    private String brand;

    @Column(length = 30)
    private String model;


}
