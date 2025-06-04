package com.example.loudlygmz.domain.service;

import com.example.loudlygmz.application.dto.user.RegisterRequestDTO;
import com.example.loudlygmz.domain.model.MsqlUser;

public interface IMsqlUserService {
    MsqlUser createUser(String uid, RegisterRequestDTO request);
    MsqlUser getMsqlUserByUsername(String username);
    MsqlUser getMsqlUserByUid(String uid);
}
