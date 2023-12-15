package com.cinemaUnderTheJava.database.entity;

import com.cinemaUnderTheJava.database.enums.ActivationStatus;
import com.cinemaUnderTheJava.database.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UserEntity extends AbstractUUIDEntity {

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


