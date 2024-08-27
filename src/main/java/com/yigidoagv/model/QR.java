package com.yigidoagv.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "qr")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String qr;

    private LocalTime timeZone = LocalTime.now();

}
