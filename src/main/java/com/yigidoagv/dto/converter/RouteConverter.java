package com.yigidoagv.dto.converter;

import com.yigidoagv.dto.request.RouteDto;
import com.yigidoagv.model.Route;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RouteConverter {

    public Route toRoute(RouteDto routeDto) {
        String array = toString(routeDto);
        Route route = new Route();
        route.setRota(array);
        return route;
    }

    private String toString(RouteDto routeDto) {
        return routeDto.getRota().stream()
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }
}
