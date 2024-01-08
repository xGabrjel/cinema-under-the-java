package com.cinemaUnderTheJava.business.util;

import com.cinemaUnderTheJava.database.entity.UserEntity;
import com.cinemaUnderTheJava.database.enums.ActivationStatus;
import com.cinemaUnderTheJava.database.repository.jpa.UserJpaRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserVerificationUtil {

    private final UserJpaRepository userJpaRepository;
    private final EmailVerificationUtil emailVerification;
    @Value("${verification.link}")
    public String verificationLink;

    public String createVerificationToken() {
        return UUID.randomUUID().toString();
    }

    public String createVerificationLink(UserEntity user, String verificationLink) {
        return verificationLink.concat(user.getActivationToken());
    }

    public void sendVerificationEmail(UserEntity user) {
        try {
            emailVerification.sendVerificationEmail(user.getEmail(), createVerificationLink(user, verificationLink));
        } catch (MessagingException | UnsupportedEncodingException ex) {
            log.error("Exception occurred during sending verification email", ex);
        }
    }

    public void activateUserAccount(String token) {
        userJpaRepository.findByActivationToken(token)
                .ifPresentOrElse(user -> {
                            user.setActivationStatus(ActivationStatus.ACTIVE);
                            user.setActivationToken(null);
                            userJpaRepository.save(user);
                            log.info("User Account Verified!");
                        },
                        () -> log.error("User not found for activation token: [%s]".formatted(token)));
    }
}
