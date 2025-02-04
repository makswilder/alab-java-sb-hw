package com.coworking.coworkingspace.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "coworking_spaces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoworkingSpace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "space_id", nullable = false, updatable = false)
    private int spaceID;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "available", nullable = false)
    private boolean available;

    @Override
    public String toString() {
        return "CoworkingSpace{" +
                "ID=" + spaceID +
                ", Type='" + type + '\'' +
                ", Price=" + price +
                ", Available=" + available +
                '}';
    }
}
