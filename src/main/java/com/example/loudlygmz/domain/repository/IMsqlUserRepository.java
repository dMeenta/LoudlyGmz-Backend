package com.example.loudlygmz.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.loudlygmz.domain.model.MsqlUser;

public interface IMsqlUserRepository extends JpaRepository<MsqlUser, String> {
  Optional<MsqlUser> findByUsername(String username);

  @Query("SELECT u FROM MsqlUser u WHERE u.uid NOT IN :excludedIds")
  Page<MsqlUser> findAllExcludingIds(@Param("excludedIds") List<String> excludedIds, Pageable pageable);

  Page<MsqlUser> findByUidIn(List<String> uids, Pageable pageable);

  boolean existsByEmail(String email);
  boolean existsByUsername(String username);
  Page<MsqlUser> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
  Page<MsqlUser> findByUidInAndUsernameContainingIgnoreCase(List<String> ids, String username, Pageable pageable);
}
