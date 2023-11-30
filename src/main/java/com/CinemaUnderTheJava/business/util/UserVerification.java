package com.cinemaUnderTheJava.business.util;

import com.cinemaUnderTheJava.database.entity.UserEntity;
import com.cinemaUnderTheJava.database.enums.ActivationStatus;
import com.cinemaUnderTheJava.database.repository.jpa.UserJpaRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class UserVerification {

    private final UserJpaRepository userJpaRepository;
    private final EmailVerification emailVerification;
    @Value("$verification.link")
    private String verificationLink;

    public String createVerificationToken() {
        return UUID.randomUUID().toString();
    }

    public String createVerificationLink(UserEntity user) {
        return verificationLink.concat(user.getActivationToken());
    }

    public void sendVerificationEmail(UserEntity user) {
        try {
            emailVerification.sendVerificationEmail(user.getEmail(), createVerificationLink(user));
        } catch (MessagingException | UnsupportedEncodingException ex) {
            log.error("Exception occurred during sending verification email", ex);
        }
    }

    public void activateUserAccount(String token) {
        userJpaRepository.findByActivationToken(token).ifPresentOrElse(user -> {
            user.setActivationStatus(ActivationStatus.ACTIVE);
            user.setActivationToken(null);
            userJpaRepository.save(user);
            log.info("User Account Verified!");
        },
                () -> log.error("User not found for activation token: [%s]".formatted(token)));
    }
}