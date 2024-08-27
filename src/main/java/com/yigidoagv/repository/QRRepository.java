package com.yigidoagv.repository;

import com.yigidoagv.model.QR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QRRepository extends JpaRepository<QR, Long> {
    Optional<QR> findByQr(String qr);
}
