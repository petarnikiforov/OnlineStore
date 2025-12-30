package org.online.store.service;

import org.online.store.dto.LoginRequest;
import org.online.store.dto.LoginResponse;
import org.online.store.dto.UserResponse;
import org.online.store.error.NotFoundObjectException;
import org.online.store.mapper.UserMapper;
import org.online.store.models.Product;
import org.online.store.models.Userr;
import org.online.store.repository.UserRepository;
import org.online.store.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepo;
    @Autowired
    UserMapper userMapper;
    @Autowired
    JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request){
        Userr user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new NotFoundObjectException("Username doesn't exist!",
                        Product.class.getName(), request.getUsername()));
        if(!user.getPassword().equals(request.getPassword())){
            throw new RuntimeException("Invalid password or username");
        }
        String token = jwtUtil.generateToken(user.getUsername());
        UserResponse userResponse = userMapper.modelToResponse(user);
        return new LoginResponse(token, userResponse);
    }
}
