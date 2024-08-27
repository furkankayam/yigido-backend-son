package com.yigidoagv.controller;

import com.yigidoagv.dto.AuthRequestDto;
import com.yigidoagv.dto.JwtResponseDto;
import com.yigidoagv.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/generateToken")
    public JwtResponseDto AuthenticateAndGetToken(@RequestBody AuthRequestDto request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        if(authentication.isAuthenticated()){
            return JwtResponseDto.builder()
                    .accessToken(jwtService.GenerateToken(request.getUsername()))
                    .build();
        }
        throw new UsernameNotFoundException("invalid username {} " + request.getUsername());
    }

}
