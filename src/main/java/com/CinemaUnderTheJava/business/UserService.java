package com.cinemaUnderTheJava.business;

import com.cinemaUnderTheJava.api.controller.exceptions.custom.AlreadyExistsException;
import com.cinemaUnderTheJava.api.controller.exceptions.custom.NotFoundException;
import com.cinemaUnderTheJava.api.dto.user.NewUserDto;
import com.cinemaUnderTheJava.api.dto.user.UserRequestDto;
import com.cinemaUnderTheJava.api.dto.user.UserResponseDto;
import com.cinemaUnderTheJava.business.util.UserVerificationUtil;
import com.cinemaUnderTheJava.database.entity.UserEntity;
import com.cinemaUnderTheJava.database.enums.ActivationStatus;
import com.cinemaUnderTheJava.database.mapper.UserMapper;
import com.cinemaUnderTheJava.database.repository.jpa.UserJpaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.cinemaUnderTheJava.api.controller.exceptions.ExceptionMessages.*;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;
    private final UserVerificationUtil userVerification;

    @Transactional
    public NewUserDto registerUser(UserRequestDto userRequestDto) {
        log.info("Registering new user: [%s]".formatted(userRequestDto.email()));
        checkIfEmailExists(userRequestDto);
        validatePasswordMatching(userRequestDto);

        UserEntity user = createUser(userRequestDto);
        userJpaRepository.save(user);
        log.info("User registered successfully: [%s]".formatted(user.getEmail()));
        userVerification.sendVerificationEmail(user);
        log.info("Verification email was send successfully!");

        return userMapper.newUserEntityToDto(user);
    }

    private UserEntity createUser(UserRequestDto userRequestDto) {
        log.info("Creating new User data: [%s]".formatted(userRequestDto));
        return UserEntity.builder()
                .firstName(userRequestDto.firstName())
                .lastName(userRequestDto.lastName())
                .email(userRequestDto.email())
                .password(userRequestDto.password())
                .activationStatus(ActivationStatus.INACTIVE)
                .activationToken(userVerification.createVerificationToken())
                .role(userRequestDto.role())
                .build();
    }

    private void validatePasswordMatching(UserRequestDto userRequestDto) {
        if (!userRequestDto.password().equals(userRequestDto.repeatedPassword())) {
            throw new ValidationException(PASSWORDS_DO_NOT_MATCH.getMessage());
        }
    }

    private void checkIfEmailExists(UserRequestDto userRequestDto) {
        String userEmail = userRequestDto.email();
        if (userJpaRepository.existsByEmail(userEmail)) {
            throw new AlreadyExistsException(EMAIL_IS_TAKEN.getMessage(), userEmail);
        }
    }

    public UserResponseDto findUserDtoById(Long userId) {
        UserEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage(), userId));
        return userMapper.entityToDto(user);
    }

    public UserEntity findUserById(Long userId) {
        return userJpaRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage(), userId));
    }
}
