package com.example.loudlygmz.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import com.example.loudlygmz.domain.enums.Role;

@Entity
@Table(name = "users")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class MsqlUser {
    
    @Id
    private String uid;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(name = "profile_picture")
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    
    private String biography;

    @Column(name = "creation_date", insertable = false, updatable = false)
    private LocalDateTime creationDate;

}
