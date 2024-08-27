package com.yigidoagv.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ExceptionMessage {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

}
