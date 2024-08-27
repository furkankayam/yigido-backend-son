package com.yigidoagv.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "states")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean charge;

    @Column(name = "battery_level")
    private String batteryLevel;

    private Boolean barrier;

    private Boolean load;

    @Column(name = "load_level")
    private String loadLevel;

    private LocalTime timeZone = LocalTime.now();

}
