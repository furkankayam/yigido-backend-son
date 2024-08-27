package com.yigidoagv.service;

import com.yigidoagv.dto.converter.MqttConverter;
import com.yigidoagv.dto.converter.RouteConverter;
import com.yigidoagv.dto.request.RouteDto;
import com.yigidoagv.model.MqttPublishModel;
import com.yigidoagv.model.Route;
import com.yigidoagv.repository.RouteRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublishMessageService {

    private final RouteRepository routeRepository;
    private final RouteConverter routeConverter;
    private final MqttConverter mqttConverter;
    private final MqttPublishService mqttPublishService;
    private final UserLookupService userLookupService;

    @Transactional
    public RouteDto createMessage(HttpServletRequest httpServletRequest,
                                  RouteDto routeDto) {
        userLookupService.findUserByRequest(httpServletRequest);
        Route route = routeConverter.toRoute(routeDto);
        routeRepository.save(route);
        MqttPublishModel mqttPublishModel = mqttConverter.toMqttModel(routeDto);
        mqttPublishService.publishMessage(mqttPublishModel);
        return routeDto;
    }

}
