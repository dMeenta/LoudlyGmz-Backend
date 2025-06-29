package com.example.loudlygmz.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.loudlygmz.application.dto.user.RegisterRequestDTO;
import com.example.loudlygmz.domain.model.MsqlUser;

public interface IMsqlUserService {
    MsqlUser createUser(String uid, RegisterRequestDTO request);
    MsqlUser getMsqlUserByUsername(String username);
    MsqlUser getMsqlUserByUid(String uid);
    List<MsqlUser> getAllMsqlUserByUid(List<String> listOfUids);
    Page<MsqlUser> findUsersByIdIn(List<String> listOfUids, Pageable pageable);
    Page<MsqlUser> findAllExcludingIds(List<String> excludedIds, Pageable pageable);
}
