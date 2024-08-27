package com.yigidoagv.dto.request;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteDto {

    private List<Map<String, String>> rota;

}
