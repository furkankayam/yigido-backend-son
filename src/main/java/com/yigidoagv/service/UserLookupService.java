package com.yigidoagv.service;

import com.yigidoagv.exception.UserNotFoundException;
import com.yigidoagv.model.User;
import com.yigidoagv.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLookupService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public User findUserByRequest(HttpServletRequest httpServletRequest) {
        String username = jwtService.resolveUsername(httpServletRequest);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException("User Not Found!");
        }

        return user;
    }

}
