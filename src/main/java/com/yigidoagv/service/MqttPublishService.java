package com.yigidoagv.service;

import com.yigidoagv.config.MqttConfig;
import com.yigidoagv.exception.MqttClientException;
import com.yigidoagv.model.MqttPublishModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MqttPublishService {

    private final MqttConfig mqttConfig;

    public void publishMessage(MqttPublishModel mqttPublishModel) {
        try {
            MqttMessage mqttMessage = new MqttMessage(mqttPublishModel.getMessage().getBytes());
            mqttMessage.setQos(mqttPublishModel.getQos());
            mqttMessage.setRetained(mqttPublishModel.getRetained());
            mqttConfig.getInstance().publish(mqttPublishModel.getTopic(), mqttMessage);
            log.info(mqttPublishModel.getMessage());
        } catch (MqttException e) {
            throw new MqttClientException("Mqtt Broker Failed To Deliver The Message!");
        }
    }
}
