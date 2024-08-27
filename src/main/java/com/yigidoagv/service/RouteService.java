package com.yigidoagv.service;
import com.yigidoagv.config.MqttConfig;
import com.yigidoagv.dto.response.RouteSubscribeDto;
import com.yigidoagv.exception.MqttClientException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final MqttConfig mqttConfig;
    private final UserLookupService userLookupService;
    private String rota;

    private final MqttCallback mqttCallback = new MqttCallback() {

        @Override
        public void connectionLost(Throwable cause) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) {
            String payload = new String(message.getPayload());
            JSONObject jsonMessage = new JSONObject(payload);
            rota = String.valueOf(jsonMessage);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }

    };

    public RouteSubscribeDto getMessage(HttpServletRequest httpServletRequest) {
        userLookupService.findUserByRequest(httpServletRequest);

        try {
            MqttClient mqttClient = new MqttClient(mqttConfig.getMQTT_SERVER_URL(), UUID.randomUUID().toString());
            mqttClient.setCallback(mqttCallback);
            mqttClient.connect();
            mqttClient.subscribe("rota");
        } catch (MqttException e) {
            throw new MqttClientException("Mqtt Broker Could Not Subscribe To The Route Topic!");
        }

        return new RouteSubscribeDto(
                rota
        );
    }

}
