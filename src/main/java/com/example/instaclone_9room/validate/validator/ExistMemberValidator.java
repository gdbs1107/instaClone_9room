package com.example.instaclone_9room.validate.validator;

import com.example.instaclone_9room.apiPayload.code.status.ErrorStatus;
import com.example.instaclone_9room.repository.userEntityRepository.UserRepository;
import com.example.instaclone_9room.validate.annotation.ExistMember;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExistMemberValidator implements ConstraintValidator<ExistMember, Long> {

    private final UserRepository userRepository;

    @Override
    public void initialize(ExistMember constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext context) {
        boolean isValid;
        ErrorStatus errorStatus;
        if (userId == null) {
            errorStatus = ErrorStatus.MEMBER_NOT_FOUND;
            isValid = false;
        } else {
            errorStatus = ErrorStatus.MEMBER_NOT_FOUND;
            isValid = userRepository.findById(userId).isPresent();
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorStatus.toString())
                    .addConstraintViolation();
        }

        return isValid;
    }
}
