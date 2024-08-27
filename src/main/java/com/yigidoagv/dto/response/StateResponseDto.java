package com.yigidoagv.dto.response;

public record StateResponseDto(
        Boolean charge,
        String batteryLevel,
        Boolean barrier,
        Boolean load,
        String loadLevel
) {
}
