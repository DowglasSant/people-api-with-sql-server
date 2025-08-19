package com.santana.dowglas.sql_server_application.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Address", schema = "dbo", catalog = "people")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    @JsonBackReference
    private Person person;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "state", nullable = false, length = 50)
    private String state;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Column(name = "neighborhood", length = 100)
    private String neighborhood;

    @Column(name = "zipcode", length = 20)
    private String zipcode;

    @Column(name = "house_number", length = 20)
    private String houseNumber;
}
