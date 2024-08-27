package com.yigidoagv.dto.converter;

import com.google.gson.Gson;
import com.yigidoagv.dto.request.RouteDto;
import com.yigidoagv.model.MqttPublishModel;
import org.springframework.stereotype.Component;

@Component
public class MqttConverter {

    public MqttPublishModel toMqttModel(RouteDto routeDto) {
        MqttPublishModel mqttPublishModel = new MqttPublishModel();

        String rotaJson = new Gson().toJson(routeDto);

        mqttPublishModel.setMessage(rotaJson);
        mqttPublishModel.setQos(1);
        mqttPublishModel.setTopic("rota");
        mqttPublishModel.setRetained(true);

        return mqttPublishModel;
    }

}
