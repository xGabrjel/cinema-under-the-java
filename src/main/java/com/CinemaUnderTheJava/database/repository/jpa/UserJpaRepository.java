package com.cinemaUnderTheJava.database.repository.jpa;

import com.cinemaUnderTheJava.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByActivationToken(String activationToken);
}
