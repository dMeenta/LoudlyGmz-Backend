package com.example.loudlygmz.domain.service;

import com.example.loudlygmz.application.dto.auth.LoginResponse;
import com.example.loudlygmz.application.dto.user.LoginRequestDTO;

public interface IAuthService {
    LoginResponse login(LoginRequestDTO request);
}
