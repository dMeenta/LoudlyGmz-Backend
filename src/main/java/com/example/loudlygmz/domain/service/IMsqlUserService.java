package com.example.loudlygmz.domain.service;

import com.example.loudlygmz.application.dto.user.MsqlUserRequest;
import com.example.loudlygmz.application.dto.user.MsqlUserResponse;

public interface IMsqlUserService {
    MsqlUserResponse createUser(MsqlUserRequest request);
    MsqlUserResponse getUserByUid(String uid);
}
