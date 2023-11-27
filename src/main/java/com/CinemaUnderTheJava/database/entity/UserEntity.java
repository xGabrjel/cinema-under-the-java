package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.enums.ActivationStatus;
import com.cinemaUnderTheJava.database.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    private ActivationStatus activationStatus;
    private String activationToken;
}


