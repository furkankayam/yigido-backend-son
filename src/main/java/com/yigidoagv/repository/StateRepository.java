package com.yigidoagv.repository;

import com.yigidoagv.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByChargeAndBatteryLevelAndBarrierAndLoadAndLoadLevel(
            Boolean charge,
            String batteryLevel,
            Boolean barrier,
            Boolean load,
            String loadLevel
    );
}
