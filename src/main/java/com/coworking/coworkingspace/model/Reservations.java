package com.coworking.coworkingspace.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int reservationID;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time", nullable = false)
    private String endTime;

    @ManyToOne
    @JoinColumn(name = "coworking_space_id", nullable = false)
    private CoworkingSpace space;

    @Override
    public String toString() {
        return "Reservation{" +
                "ID=" + reservationID +
                ", Customer='" + customerName + '\'' +
                ", Date='" + date + '\'' +
                ", Time=" + startTime + "-" + endTime +
                ", Space=" + space.getSpaceID() +
                '}';
    }
}
