package com.coworking.coworkingspace.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "coworking_space")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoworkingSpace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "space_id", nullable = false, updatable = false)
    private Integer spaceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "available", nullable = false)
    private boolean available = true;

    @Column(name = "reservation_details")
    private String reservationDetails;

    @Override
    public String toString() {
        return String.format("CoworkingSpace{id=%d, name='%s', type='%s', price=%.2f, available=%b, booking='%s'}",
                spaceId, name, type, price, available, reservationDetails != null ? reservationDetails : "None");
    }
}
