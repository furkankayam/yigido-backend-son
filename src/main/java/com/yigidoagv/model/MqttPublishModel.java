package com.yigidoagv.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MqttPublishModel {

    @NotNull
    @Size(min = 1,max = 255)
    private String topic;

    @NotNull
    @Size(min = 1,max = 255)
    private String message;

    @NotNull
    private Boolean retained;

    @NotNull
    private Integer qos;

}
