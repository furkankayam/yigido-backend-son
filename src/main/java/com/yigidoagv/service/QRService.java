package com.yigidoagv.service;

import com.yigidoagv.config.MqttConfig;
import com.yigidoagv.dto.response.QRResponseDto;
import com.yigidoagv.exception.MqttClientException;
import com.yigidoagv.model.QR;
import com.yigidoagv.repository.QRRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class QRService {

    private final MqttConfig mqttConfig;
    private final UserLookupService userLookupService;
    private final QRRepository qRRepository;
    private String qr;

    private final MqttCallback mqttCallback = new MqttCallback() {

        @Override
        public void connectionLost(Throwable cause) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) {
            String payload = new String(message.getPayload());
            JSONObject jsonMessage = new JSONObject(payload);
            qr = jsonMessage.getString("qr");
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }

    };

    public QRResponseDto getMessage(HttpServletRequest httpServletRequest) {
        userLookupService.findUserByRequest(httpServletRequest);

        try {
            MqttClient mqttClient = new MqttClient(mqttConfig.getMQTT_SERVER_URL(),
                    UUID.randomUUID().toString());
            mqttClient.setCallback(mqttCallback);
            mqttClient.connect();
            mqttClient.subscribe("qr");
        } catch (MqttException e) {
            throw new MqttClientException("Mqtt Broker Could Not Subscribe To The QR Topic!");
        }

        saveQR(qr);

        return new QRResponseDto(
                qr
        );
    }

    @Async
    public void saveQR(String qr) {
        Optional<QR> existingQR = qRRepository.findByQr(qr);

        if (existingQR.isEmpty()) {
            QR saveQR = new QR();
            saveQR.setQr(qr);
            qRRepository.save(saveQR);
            log.info("QR saved: {}", qr);
        }
    }

}
