package com.cinemaUnderTheJava.business.util;

import com.cinemaUnderTheJava.database.entity.UserEntity;
import com.cinemaUnderTheJava.database.enums.ActivationStatus;
import com.cinemaUnderTheJava.database.repository.jpa.UserJpaRepository;
import com.cinemaUnderTheJava.fixtures.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserVerificationUtilTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private EmailVerificationUtil emailVerification;

    @InjectMocks
    private UserVerificationUtil userVerificationUtil;

    private final String verificationLink = "http://localhost:8080/CinemaUnderTheJava/users/activation?token=";

    @Test
    void createVerificationTokenShouldReturnValidToken() {
        //given, when
        String token = userVerificationUtil.createVerificationToken();

        //then
        assertDoesNotThrow(() -> UUID.fromString(token));
    }

    @Test
    void createVerificationLinkShouldReturnValidLink() {
        //given
        UserEntity user = EntityFixtures.someUserEntity()
                .withActivationToken(verificationLink);

        //when
        String link = userVerificationUtil.createVerificationLink(user, verificationLink);

        //then
        assertDoesNotThrow(() -> new java.net.URI(link));
        assertThat(link).contains(user.getActivationToken());
    }

    @Test
    void activateUserAccountShouldActivateUserWhenTokenExists() {
        //given
        UserEntity user = EntityFixtures.someUserEntity()
                .withActivationStatus(ActivationStatus.INACTIVE)
                .withActivationToken(verificationLink);

        //when
        when(userJpaRepository.findByActivationToken(user.getActivationToken())).thenReturn(Optional.of(user));

        //then
        assertDoesNotThrow(() -> userVerificationUtil.activateUserAccount(user.getActivationToken()));
        verify(userJpaRepository, times(1)).save(user);
        assertEquals(ActivationStatus.ACTIVE, user.getActivationStatus());
        assertNull(user.getActivationToken());
    }
}