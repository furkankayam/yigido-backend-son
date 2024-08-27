package com.yigidoagv.exception;

public class MqttClientException extends RuntimeException{
    public MqttClientException(String message) {
        super(message);
    }
}
