package com.yigidoagv.service;

import com.yigidoagv.config.MqttConfig;
import com.yigidoagv.dto.response.StateResponseDto;
import com.yigidoagv.exception.MqttClientException;
import com.yigidoagv.model.State;
import com.yigidoagv.repository.StateRepository;
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
public class StateService {

    private final MqttConfig mqttConfig;
    private final UserLookupService userLookupService;
    private final StateRepository stateRepository;
    private Boolean charge;
    private String batteryLevel;
    private Boolean barrier;
    private Boolean load;
    private String loadLevel;

    private final MqttCallback mqttCallback = new MqttCallback() {

        @Override
        public void connectionLost(Throwable cause) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            String payload = new String(message.getPayload());
            JSONObject jsonMessage = new JSONObject(payload);
            charge = jsonMessage.getBoolean("charge");
            batteryLevel = jsonMessage.getString("batteryLevel");
            barrier = jsonMessage.getBoolean("barrier");
            load = jsonMessage.getBoolean("load");
            loadLevel = jsonMessage.getString("loadLevel");
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }

    };

    public StateResponseDto getMessage(HttpServletRequest httpServletRequest) {
        userLookupService.findUserByRequest(httpServletRequest);

        try {
            MqttClient mqttClient = new MqttClient(mqttConfig.getMQTT_SERVER_URL(), UUID.randomUUID().toString());
            mqttClient.setCallback(mqttCallback);
            mqttClient.connect();
            mqttClient.subscribe("durum");
        } catch (MqttException e) {
            throw new MqttClientException("Mqtt Broker Could Not Subscribe To The State Topic!");
        }

        saveState(
                charge,
                batteryLevel,
                barrier,
                load,
                loadLevel
        );

        return new StateResponseDto(
                charge,
                batteryLevel,
                barrier,
                load,
                loadLevel);
    }

    @Async
    public void saveState(Boolean charge,
                          String batteryLevel,
                          Boolean barrier,
                          Boolean load,
                          String loadLevel) {
        Optional<State> existingState = stateRepository.findByChargeAndBatteryLevelAndBarrierAndLoadAndLoadLevel(
                charge,
                batteryLevel,
                barrier,
                load,
                loadLevel
        );

        if (existingState.isEmpty()) {
            State state = new State();
            state.setCharge(charge);
            state.setBatteryLevel(batteryLevel);
            state.setBarrier(barrier);
            state.setLoad(load);
            state.setLoadLevel(loadLevel);
            stateRepository.save(state);

            log.info("State: {} {} {} {} {}",
                    charge,
                    batteryLevel,
                    barrier,
                    load,
                    loadLevel);
        }
    }

}
