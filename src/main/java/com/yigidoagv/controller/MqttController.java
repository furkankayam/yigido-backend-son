package com.yigidoagv.controller;

import com.yigidoagv.dto.request.RouteDto;
import com.yigidoagv.dto.response.QRResponseDto;
import com.yigidoagv.dto.response.RouteSubscribeDto;
import com.yigidoagv.dto.response.StateResponseDto;
import com.yigidoagv.service.PublishMessageService;
import com.yigidoagv.service.QRService;
import com.yigidoagv.service.RouteService;
import com.yigidoagv.service.StateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MqttController {

    private final PublishMessageService publishMessageService;
    private final StateService stateService;
    private final QRService qrService;
    private final RouteService routeService;

    @PostMapping("/publish")
    public ResponseEntity<RouteDto> createMessage(HttpServletRequest httpServletRequest,
                                                  @RequestBody RouteDto routeDto) {
        return ResponseEntity.ok(publishMessageService.createMessage(httpServletRequest, routeDto));
    }

    @GetMapping("/state")
    public ResponseEntity<StateResponseDto> getState(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(stateService.getMessage(httpServletRequest));
    }

    @GetMapping("/qr")
    public ResponseEntity<QRResponseDto> getQR(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(qrService.getMessage(httpServletRequest));
    }

    @GetMapping("/route")
    public ResponseEntity<RouteSubscribeDto> getRouteResponse(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(routeService.getMessage(httpServletRequest));
    }

}